package beans;

public class StreamCallBackBean{
	private String createdAt;
	private String disabled;
	private String disabledTill;
	private Hosts hosts;
	private String hub;
	private String id;
	private String publishKey;
	private String publishSecurity;
	private String title;
	private String updatedAt;

	public String getCreatedAt(){
		return this.createdAt;
	}

	public String getDisabled(){
		return this.disabled;
	}

	public String getDisabledTill(){
		return this.disabledTill;
	}

	public Hosts getHosts(){
		return this.hosts;
	}

	public String getHub(){
		return this.hub;
	}

	public String getId(){
		return this.id;
	}

	public String getPublishKey(){
		return this.publishKey;
	}

	public String getPublishSecurity(){
		return publishSecurity;
	}

	public String getTitle(){
		return this.title;
	}

	public String getUpdatedAt(){
		return this.updatedAt;
	}

	public void setCreatedAt(String paramString){
		this.createdAt = paramString;
	}

	public void setDisabled(String paramString){
		this.disabled = paramString;
	}

	public void setDisabledTill(String paramString){
		this.disabledTill = paramString;
	}

	public void setHosts(Hosts paramHosts){
		this.hosts = paramHosts;
	}

	public void setHub(String paramString){
		this.hub = paramString;
	}

	public void setId(String paramString){
		this.id = paramString;
	}

	public void setPublishKey(String paramString){
		this.publishKey = paramString;
	}

	public void setPublishSecurity(String paramString){
		this.publishSecurity = paramString;
	}

	public void setTitle(String paramString){
		this.title = paramString;
	}

	public void setUpdatedAt(String paramString){
		this.updatedAt = paramString;
	}

	public String toString(){
		return "{\"id\":\"" + getId() + "\"" + "," + "\"createdAt\"" + ":" + "\"" + getCreatedAt() + "\"" + "," + "\"updatedAt\"" + ":" + "\"" + getUpdatedAt() + "\"" + "," + "\"title\"" + ":" + "\"" + getTitle() + "\"" + "," + "\"hub\"" + ":" + "\"" + getHub() + "\"" + "," + "\"disabledTill\"" + ":" + "\"" + getDisabledTill() + "\"" + "," + "\"disabled\"" + ":" + "\"" + getDisabled() + "\"" + "," + "\"publishKey\"" + ":" + "\"" + getPublishKey() + "\"" + "," + "\"publishSecurity\"" + ":" + "\"" + getPublishSecurity() + "\"" + "," + "\"hosts\"" + ":" + "{" + "\"publish\"" + ":" + "{" + "\"rtmp\"" + ":" + "\"" + getHosts()
				.getPublish()
				.getRtmp() + "\"" + "}" + "," + "\"live\"" + ":" + "{" + "\"hdl\"" + ":" + "\"" + getHosts()
				.getLive()
				.getHdl() + "\"" + "," + "\"hls\"" + ":" + "\"" + getHosts()
				.getLive()
				.getHls() + "\"" + "," + "\"http\"" + ":" + "\"" + getHosts()
				.getLive()
				.getHttp() + "\"" + "," + "\"rtmp\"" + ":" + "\"" + getHosts()
				.getLive()
				.getRtmp() + "\"" + "," + "\"snapshot\"" + ":" + "\"" + getHosts()
				.getLive()
				.getSnapshot() + "\"" + "}" + "," + "\"playback\"" + ":" + "{" + "\"hls\"" + ":" + "\"" + getHosts()
				.getPlayback()
				.getHls() + "\"" + "," + "\"http\"" + ":" + "\"" + getHosts()
				.getPlayback()
				.getHttp() + "\"" + "}" + "," + "\"play\"" + ":" + "{" + "\"http\"" + ":" + "\"" + getHosts()
				.getPlay()
				.getHttp() + "\"" + "," + "\"rtmp\"" + ":" + "\"" + getHosts()
				.getPlay().getRtmp() + "\"" + "}" + "}" + "}";
	}

	public class Hosts{
		private StreamCallBackBean.Live live;
		private StreamCallBackBean.Play play;
		private StreamCallBackBean.Playback playback;
		private StreamCallBackBean.Publish publish;

		public Hosts(){
		}

		public StreamCallBackBean.Live getLive(){
			return this.live;
		}

		public StreamCallBackBean.Play getPlay(){
			return this.play;
		}

		public StreamCallBackBean.Playback getPlayback(){
			return this.playback;
		}

		public StreamCallBackBean.Publish getPublish(){
			return this.publish;
		}

		public void setLive(StreamCallBackBean.Live paramLive){
			this.live = paramLive;
		}

		public void setPlay(StreamCallBackBean.Play paramPlay){
			this.play = paramPlay;
		}

		public void setPlayback(StreamCallBackBean.Playback paramPlayback){
			this.playback = paramPlayback;
		}

		public void setPublish(StreamCallBackBean.Publish paramPublish){
			this.publish = paramPublish;
		}
	}

	public class Live{
		private String hdl;
		private String hls;
		private String http;
		private String rtmp;
		private String snapshot;

		public Live(){
		}

		public String getHdl(){
			return this.hdl;
		}

		public String getHls(){
			return this.hls;
		}

		public String getHttp(){
			return this.http;
		}

		public String getRtmp(){
			return this.rtmp;
		}

		public String getSnapshot(){
			return this.snapshot;
		}

		public void setHdl(String paramString){
			this.hdl = paramString;
		}

		public void setHls(String paramString){
			this.hls = paramString;
		}

		public void setHttp(String paramString){
			this.http = paramString;
		}

		public void setRtmp(String paramString){
			this.rtmp = paramString;
		}

		public void setSnapshot(String paramString){
			this.snapshot = paramString;
		}
	}

	public class Play{
		private String http;
		private String rtmp;

		public Play(){
		}

		public String getHttp(){
			return this.http;
		}

		public String getRtmp(){
			return this.rtmp;
		}

		public void setHttp(String paramString){
			this.http = paramString;
		}

		public void setRtmp(String paramString){
			this.rtmp = paramString;
		}
	}

	public class Playback{
		private String hls;
		private String http;

		public Playback(){
		}

		public String getHls(){
			return this.hls;
		}

		public String getHttp(){
			return this.http;
		}

		public void setHls(String paramString){
			this.hls = paramString;
		}

		public void setHttp(String paramString){
			this.http = paramString;
		}
	}

	public class Publish{
		private String rtmp;

		public Publish(){
		}

		public String getRtmp(){
			return this.rtmp;
		}

		public void setRtmp(String paramString){
			this.rtmp = paramString;
		}
	}
}