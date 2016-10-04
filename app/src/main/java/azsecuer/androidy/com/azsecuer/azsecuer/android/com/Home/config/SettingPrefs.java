package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.config;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Administrator on 2016.8.8.
 */
public class SettingPrefs {
    private static SharedPreferences sharedPreferences;
    private static final String KEY_AUTOSTART="CheckAutoStart",KEY_NOTIFIACTION="CheckNitifyAction";
    private static final String SHAREDNAME="SettingPreferences";
    public static void saveStart(Context context,boolean isCheccked){//存入本地文件（开机启动）
        if (sharedPreferences==null){//第一次进入需要创建文件
            sharedPreferences=context.getSharedPreferences(SHAREDNAME,context.MODE_PRIVATE);
        }
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean(KEY_AUTOSTART,isCheccked);//写入文件
            editor.commit();//关闭写入
    }
    public static void saveNotfication(Context context,boolean isCheccked){//存入本地文件（）
        if (sharedPreferences==null){//第一次进入需要床架文件
            sharedPreferences=context.getSharedPreferences(SHAREDNAME,context.MODE_PRIVATE);
        }
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean(KEY_NOTIFIACTION,isCheccked);//写入文件
            editor.commit();//关闭写入
    }
    public static boolean getAutoStart(Context context){//进入SettingActivity界面查找本地文件是开启还是关闭（开机启动）
        if (sharedPreferences==null){//第一次进入
            sharedPreferences=context.getSharedPreferences(SHAREDNAME,Context.MODE_PRIVATE);//创建文件
        }
        return sharedPreferences.getBoolean(KEY_AUTOSTART,false);//查找
    }
    public static boolean saveAutoNotfication(Context context){//进入SettingActivity界面查找本地文件是开启还是关闭（任务栏）
        if (sharedPreferences==null){//第一次进入
            sharedPreferences=context.getSharedPreferences(SHAREDNAME,Context.MODE_PRIVATE);//创建文件
        }
        return sharedPreferences.getBoolean(KEY_NOTIFIACTION,false);//查找
    }
}
