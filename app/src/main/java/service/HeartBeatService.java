package service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.example.livestreamingtestreceiver.app.IHeartBeatService;
import constant.Config;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/7/20.
 */
public class HeartBeatService extends Service {

	private static final long HEART_BEAT_RATE = 60 * 1000;
	public static final String MESSAGE_ACTION = "com.yogee.message_ACTION";
	public static final String HEART_BEAT_ACTION = "com.yogee" +
			".heart_beat_broadcast_ACTION";
	private long sendTime = 0L;
	private WeakReference<Socket> mSocket;
	private AckThread mReadThread;
	private static String heartBeatMsg;
	private IHeartBeatService.Stub iHeartBeatService = new IHeartBeatService.Stub() {

		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

		}

		@Override
		public boolean sendMessage(String message) throws RemoteException {
			return sendMsg(message);
		}

	};

	static {
		heartBeatMsg = createHeartBeatMsg();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		return super.onStartCommand(intent, START_STICKY, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		new createSocketThread().start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try{
			mHandler.removeCallbacks(heartBeatRunnable);
			mReadThread.release();
			releaseLastSocket(mSocket);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return (IBinder) iHeartBeatService;
	}

	private static String createHeartBeatMsg(){
		JSONObject job = new JSONObject();
		try{
			job.put("type", "0");
			job.put("userid", "androidid");
			job.put("msg", "");
			job.put("username", "androidtest");
			job.put("liveid", "0000");
		}catch(JSONException e){
			e.printStackTrace();
		}
		return job.toString();
	}

	private Handler mHandler = new Handler();
	private Runnable heartBeatRunnable = new Runnable() {
		@Override
		public void run() {
			if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
				boolean isSuccess = sendMsg(heartBeatMsg);
				if (!isSuccess) {
					mHandler.removeCallbacks(heartBeatRunnable);
					mReadThread.release();
					releaseLastSocket(mSocket);
					new createSocketThread().start();
				}
			}
			mHandler.postDelayed(this, HEART_BEAT_RATE);
		}
	};

	public boolean sendMsg(String msg) {
		if (null == mSocket || null == mSocket.get()) {
			return false;
		}
		Socket soc = mSocket.get();
		try {
			if (!soc.isClosed() && !soc.isOutputShutdown()) {
				OutputStream os = soc.getOutputStream();
				String message = msg + "\r\n";
				os.write(message.getBytes());
				os.flush();
				sendTime = System.currentTimeMillis();
				Log.e("发送成功的时间：" , ""+sendTime);
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void createSocket() throws UnknownHostException, IOException {
		Socket socket = new Socket(Config.HOST, Config.PORT);
		mSocket = new WeakReference<Socket>(socket);
		mReadThread = new AckThread(socket);
		mReadThread.start();
		mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);
	}

	class createSocketThread extends Thread{
		@Override
		public void run() {
			super.run();
			try {
				createSocket();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void releaseLastSocket(WeakReference<Socket> mSocket) {
		try {
			if (null != mSocket) {
				Socket sk = mSocket.get();
				if (!sk.isClosed()) {
					sk.close();
				}
				sk = null;
				mSocket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleAckMsg(String msg){
		try{
			JSONObject job = new JSONObject(msg);
			if(job.get("type").equals("0")){
				Intent intent = new Intent(HEART_BEAT_ACTION);
				sendBroadcast(intent);
			}else{
				Intent intent = new Intent(MESSAGE_ACTION);
				intent.putExtra("message", job.getString("username") + ": " +
						job.getString("msg"));
				sendBroadcast(intent);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
	}

	private class AckThread extends Thread{
		private WeakReference<Socket> mWeakSocket;
		private boolean isStart = true;

		public AckThread(Socket mWeakSocket) {
			this.mWeakSocket = new WeakReference<Socket>(mWeakSocket);
		}
		public void release() {
			isStart = false;
			releaseLastSocket(mWeakSocket);
		}

		@Override
		public void run(){
			super.run();

			Socket socket = mWeakSocket.get();
			if(socket != null){
				try {
					InputStream is = socket.getInputStream();
					byte[] buffer = new byte[1024 * 4];
					int length = 0;
					while (!socket.isClosed() && !socket.isInputShutdown()
							&& isStart && ((length = is.read(buffer)) != -1)) {
						if (length > 0) {
							String message = new String(Arrays.copyOf(buffer,
									length)).trim();
							Log.e("收到服务器发送来的消息： ", message);
							handleAckMsg(message);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
