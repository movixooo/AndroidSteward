package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import java.util.List;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.config.SettingPrefs;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db.SpeedupManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.SpeedupInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.MemoryManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.notification.MainNotificaton;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.view.CustomProc;

/**
 * Created by Administrator on 2016.8.3.
 */
public class HomeActivity extends BaseActionBarActivity{
    private int inRomProp360,outRomProp360;
    CustomProc customProc;
//    int temp=0;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
////            customProc.startAnimSetProgress3(80);
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (SettingPrefs.saveAutoNotfication(this)) {//针对部分手机，退出应用时系统会干掉通知栏
            MainNotificaton.openNotification(this);//进入应用判断本地文件是开启则重新打开通知栏
        }
        initView();
        instenerView();
    }
    @Override
    public void initView() {
        customProc=(CustomProc) findViewById(R.id.customPrco);
        customProc.setOnClickListener(this);
        findViewById(R.id.iv_actionbar_right_icon).setOnClickListener(this);//设置键
        findViewById(R.id.tv_home_message).setOnClickListener(this);        //通讯大全
        findViewById(R.id.tv_home_mobliemgr).setOnClickListener(this);      //手机检测
        findViewById(R.id.tv_home_expedite).setOnClickListener(this);       //手机加速
        findViewById(R.id.tv_home_software).setOnClickListener(this);       //软件管理
        findViewById(R.id.tv_home_clean).setOnClickListener(this);          //垃圾清理
        findViewById(R.id.tv_home_file).setOnClickListener(this);           //文件管理
    }
    @Override
    public void onClick(View v) {
        int data=v.getId();
        switch (data){
            case R.id.iv_actionbar_right_icon:
                startActivityNow(SettingActivity.class);
                break;
            case R.id.tv_home_message:
                startActivityNow(TelTypeActivity.class);
                break;
            case R.id.tv_home_mobliemgr:
                startActivityNow(MobilelnfoActivity.class);
                break;
            case R.id.tv_home_clean:
                startActivityNow(CleanActivity.class);
                break;
            case R.id.tv_home_expedite:
                startActivityNow(AccelerateActivity.class);
                break;
            case R.id.tv_home_software:
                startActivityNow(SoftwareActivity.class);
                break;
            case R.id.tv_home_file:
                startActivityNow(FileActivity.class);
                break;
            case R.id.customPrco:
                rotate();
                break;
        }
    }
    public void rotate(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                SpeedupManager sm = SpeedupManager.getInstance(HomeActivity.this);
                List<SpeedupInfo> speedupInfos = null;
                // 将用户进程直接干死
                speedupInfos = sm.getRuningApp(SpeedupManager.CLASSIFY_USER, true);
                for (SpeedupInfo info : speedupInfos) {
                    String packageName = info.processName;
                    SpeedupManager.getInstance(HomeActivity.this).kill(packageName);
                }
                instenerView();
//                for (int i = 0;i<=100;i++){
//                    handler.sendEmptyMessage(1);
//                    temp = i;
//                    try {
//                        Thread.sleep(400);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }.start();
    }
    @Override
    public void instenerView() {
        // 计算出饼状图 的角度比例
        long totalRom = MemoryManager.getTotalRamMemory();
        long totalRam=MemoryManager.getAvailRamMemory(this);
        customProc.setMax((int)(totalRom/1024/1024/10));
        customProc.startAnimSetProgress3((int)((totalRom-totalRam)/1024/1024/10));


    }

    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationEnd(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}
}
