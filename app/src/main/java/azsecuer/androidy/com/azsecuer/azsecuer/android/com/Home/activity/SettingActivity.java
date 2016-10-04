package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.config.SettingPrefs;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.notification.MainNotificaton;
public class SettingActivity extends BaseActionBarActivity implements CompoundButton.OnCheckedChangeListener{
    private ToggleButton iv_setting_open,iv_setting_autostart;
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        notificationManager=(NotificationManager)this.getSystemService(this.NOTIFICATION_SERVICE);//获取到通知栏服务
        setActionBarBack("设置");
        initView();
        instenerView();
    }
    @Override
    public void onClick(View view) {
        int key=view.getId();
        switch (key){
            case R.id.iv_actionbar_left_icon://返回键
                finish();
                break;
            case R.id.iv_setting_aboutus://关于手机
                Toast.makeText(SettingActivity.this,"开发者很帅",Toast.LENGTH_SHORT).show();
                break;             //等待填充
            case R.id.iv_setting_help://帮助说明
                Toast.makeText(SettingActivity.this,"开发者是天才",Toast.LENGTH_SHORT).show();
                break;             //等待填充
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//ToggleButton的监听
        switch (buttonView.getId()){
            case R.id.iv_setting_open:
                SettingPrefs.saveStart(this,iv_setting_open.isChecked());//设置开关
            break;
            case R.id.iv_setting_autostart:
                SettingPrefs.saveNotfication(this,iv_setting_autostart.isChecked());//设置开关
                if (iv_setting_autostart.isChecked()){//判断通知栏是否打开
                    MainNotificaton.openNotification(this);//开启通知栏
                }else{
                    MainNotificaton.closeNotification(this);//关闭通知栏
                }
            break;
        }
    }
    @Override
    public void initView() {
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);//返回键
        iv_setting_open=(ToggleButton)findViewById(R.id.iv_setting_open);//开机启动
        iv_setting_autostart=(ToggleButton)findViewById(R.id.iv_setting_autostart);//通知图标
        findViewById(R.id.iv_setting_aboutus).setOnClickListener(this);//关于手机
        findViewById(R.id.iv_setting_help).setOnClickListener(this);//帮助说明
        //获取设置信息
        iv_setting_open.setChecked(SettingPrefs.getAutoStart(this));//从本地文件中得到开关状态
        iv_setting_autostart.setChecked(SettingPrefs.saveAutoNotfication(this));//从本地文件中得到开关状态
        iv_setting_open.setOnCheckedChangeListener(this);
        iv_setting_autostart.setOnCheckedChangeListener(this);
    }
    @Override
    public void instenerView() {}
    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationEnd(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}
}