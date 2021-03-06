# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

#bugly-start
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#bugly-end

-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

##友盟
#-keepclassmembers class * {
#   public <init> (org.json.JSONObject);
#}
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-dontoptimize
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose
-ignorewarnings


-keep public class * extends android.app.Activity

-keep public class * extends android.app.Application

-keep public class * extends android.app.Service

-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends android.app.backup.BackupAgentHelper

-keep public class * extends android.preference.Preference

-keep public class com.android.vending.licensing.ILicensingService

-keep public class android.content.pm.*
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#####################记录生成的日志数据,gradle build时在本项目根目录输出################
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#####################记录生成的日志数据，gradle build时 在本项目根目录输出-end################


###############混淆保护自己项目的部分代码以及引用的第三方jar包library#########################
#for support v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

# for zxing.jar
#-libraryjars   libs/zxing.jar
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }
-keep interface com.google.zxing.** { *; }
-keep public class * extends com.google.zxing.**
# for xiaomi
#-libraryjars   libs/oauth-xiaomiopenauth.jar
-dontwarn com.xiaomi.account**
-dontwarn miui.net.**
-keep class com.xiaomi.account.** { *; }
-keep interface com.xiaomi.account.** { *; }
-keep class com.xiaomi.auth.** { *; }
-keep interface com.xiaomi.auth.** { *; }
-keep class com.xiaomi.openauth.** { *; }
-keep interface com.xiaomi.openauth.** { *; }
-keep interface miui.net.** { *; }
-keep public class * extends com.xiaomi.account.**

#for com.ming.app.xing
-keep class com.mining.app.zxing.** { *; }
-keep interface com.mining.app.zxing.** { *; }
-keep public class * extends com.mining.app.zxing.**

#for websocket client
#-keep class dx.client.** { *; }
#-keep interface dx.client.** { *; }
#-keep public class * extends dx.client.**

-keep class net.minidev.json.** { *; }
-keep interface net.minidev.json.** { *; }
-keep public class * extends net.minidev.json.**

#高德地图
#-keep class com.amap.api.** { *; }
#-keep interface com.amap.api.** { *; }
#-keep public class * extends com.amap.api.**

#-keep class autonavi.amap.mapcore.** { *; }
#-keep interface autonavi.amap.mapcore.** { *; }
#-keep public class * extends autonavi.amap.mapcore.**

#-keep class com.aps.** { *; }
#-keep interface com.aps.** { *; }
#-keep public class * extends com.aps.**

#-keep class com.amap.api.location.** { *; }
#-keep interface com.amap.api.location.** { *; }
#-keep public class * extends com.amap.api.location.**

#-keep class com.amap.api.services.** { *; }
#-keep interface com.amap.api.services.** { *; }
#-keep public class * extends com.amap.api.services.**
#如果有其它包有warning，在报出warning的包加入下面类似的-dontwarn 报名
#高德相关混淆文件
#3D 地图
-keep   class com.amap.api.mapcore.**{*;}
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
#Location
-keep   class com.amap.api.location.**{*;}
-keep   class com.aps.**{*;}
#Service
-keep   class com.amap.api.services.**{*;}

#commons
-keep class org.apache.commons.codec.** { *; }
-keep interface org.apache.commons.codec.** { *; }
-keep public class * extends org.apache.commons.codec.**

-keep class org.apache.commons.httpclient.** { *; }
-keep interface org.apache.commons.httpclient.** { *; }
-keep public class * extends org.apache.commons.httpclient.**

-keep class org.apache.commons.logging.** { *; }
-keep interface org.apache.commons.logging.** { *; }
-keep public class * extends org.apache.commons.logging.**
#dom4j
-keep class org.dom4j.** { *; }
-keep interface org.dom4j.** { *; }
-keep public class * extends org.dom4j.**
#fota
-keep class com.gmobi.fota.** { *; }
-keep class com.gmobi.handler.** { *; }

-keep interface com.gmobi.fota.** { *; }
-keep interface com.gmobi.handler.** { *; }
-keep public class * extends com.gmobi.fota.**
-keep public class * extends com.gmobi.handler.**

-keep class com.redbend.** { *; }
-keep interface com.redbend.** { *; }
-keep public class * extends com.redbend.**
#gson
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }
-keep public class * extends com.google.gson.**

#jaxen
-keep class org.jaxen.** { *; }
#joda
-keep class org.joda.time.** { *; }
#libammsdk
-keep class com.tencent.mm.** { *; }
#tencent多级目录
# qq sdk
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

#mistats
-keep class com.xiaomi.mistat.** { *; }
-keep class com.xiaomi.mistatistic.sdk.** { *; }
-keep interface com.xiaomi.mistat.** { *; }
-keep public class * extends com.xiaomi.mistat.**
-keep interface com.xiaomi.mistatistic.sdk.** { *; }
-keep public class * extends com.xiaomi.mistatistic.sdk.**
#wearable
-keep class com.mediatek.camera.addition.remotecamera.service.** { *; }
-keep class com.mediatek.camera.service.** { *; }
-keep class com.mediatek.ctrl.epo.** { *; }
-keep class com.mediatek.ctrl.fota.** { *; }
-keep class com.mediatek.ctrl.map.** { *; }
-keep class com.mediatek.ctrl.music.** { *; }
-keep class com.mediatek.ctrl.notification.** { *; }
-keep class com.mediatek.ctrl.sos.** { *; }
-keep class com.mediatek.ctrl.sync.** { *; }
-keep class com.mediatek.ctrl.yahooweather.** { *; }
-keep class com.mediatek.leprofiles.** { *; }
-keep class com.mediatek.wearable.** { *; }
-keep class com.mediatek.wearableProfiles.** { *; }

-dontwarn okio.**
-keep class okio.** { *;}

-dontwarn okhttp3.**
-keep class okhttp3.** { *;}

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}

-dontwarn com.ximalaya.ting.android.player.**
-keep class com.ximalaya.ting.android.player.** { *;}

-dontwarn com.google.gson.**
-keep class com.google.gson.** { *;}

-dontwarn org.litepal.**
-keep class org.litepal.** { *;}

-dontwarn android.support.**
-keep class android.support.** { *;}

-keep interface com.ximalaya.ting.android.opensdk.** {*;}
-dontwarn com.ximalaya.ting.android.opensdk.**
-keep class com.ximalaya.ting.android.opensdk.** { *; }



#telecom
-keep   class com.telecom.websdk.**{*;}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    static transient <fields>;
    private <fields>;
    private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclasseswithmembers,allowshrinking class android.content.pm.* {
    public <methods>;
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

-keep class com.imibaby.client.receivers.MiPushMessageReceiver {*;}

#极光推送
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

# 定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}