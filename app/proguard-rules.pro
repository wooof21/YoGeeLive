# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontskipnonpubliclibraryclasses #不去忽略非公共的库类
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-dontoptimize #优化 不优化输入的类文件

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
-keepattributes Annotation, Exceptions, Signature, InnerClasses, EnclosingMethod #保护注解
-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keep public class * extends android.support.v4.app.Fragment #如果有引用v4包
-dontwarn android.support.** #如果引用了v4或者v7包

-ignorewarning #忽略警告
-dump class_files.txt #记录生成的日志数据,gradle build时在本项目根目录输出 apk 包内所有 class 的内部结构
-printseeds seeds.txt #未混淆的类和成员
-printusage unused.txt #列出从 apk 中删除的代码
-printmapping mapping.txt #混淆前后的映射 mapping.txt
                                  #列出了原始的类，方法和字段名与混淆后代码间的映射。
                                  #这个文件很重要，当你从release版本中收到一个bug报告时，可以用它来翻译被混淆的代码。
                                  # mapping目录在 \app\build\outputs\mapping\release


-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable #保持 Serializable 不被混淆

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

-keepclassmembers class **.R$* { #不混淆资源类
    public static <fields>;
}



-keep class com.umeng.**{*;} #友盟

-keep class dagger.**{*;}
-dontwarn dagger.*
-keep class javax.inject.**{*;}
-dontwarn javax.inject.*
-keep class rx.**{*;}
-dontwarn rx.*

-keep class library.tool.clm.**{*;}
-dontwarn library.tool.clm.*

-keep class com.qiniu.pili.droid.streaming.** { *; }
-keep class tv.danmaku.ijk.media.player.** {*;}

