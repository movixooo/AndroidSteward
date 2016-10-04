package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.notification;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity.HomeActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity.TelTypeActivity;
/**
 * Created by Administrator on 2016.8.9.
 */
public class MainNotificaton {
    private static NotificationManager notificationManager=null;
    private static final int NOTIFICATIONID=1;
    public static void openNotification(Context context){
        if (notificationManager==null){
            notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        }
        Notification notification=null;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_notification);
            //需要创建出通讯大全后在做PendingInTENT
        Intent intenthome=new Intent(context, HomeActivity.class);
        Intent intenttel=new Intent(context, TelTypeActivity.class);
        PendingIntent pendingIntenthome = PendingIntent.getActivity(context,0,intenthome,PendingIntent.FLAG_UPDATE_CURRENT);//做更新
        PendingIntent pendingIntenttel=PendingIntent.getActivity(context,0,intenttel,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_top,pendingIntenthome);
        remoteViews.setOnClickPendingIntent(R.id.notification_right,pendingIntenttel);
        notification=new Notification.Builder(context)
                .setContent(remoteViews)//设置Notification的自定义布局
                .setTicker("有新的消息")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContent(remoteViews)
                .build();
        notification.flags=notification.FLAG_NO_CLEAR;//点击通知栏消除不了
        notificationManager.notify(NOTIFICATIONID,notification);
    }
    public static void closeNotification(Context context){
        if (notificationManager==null){
            notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        }
        notificationManager.cancel(NOTIFICATIONID);
    }
}
