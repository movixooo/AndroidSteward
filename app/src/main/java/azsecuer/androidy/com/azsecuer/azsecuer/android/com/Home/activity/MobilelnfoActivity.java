package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.MobileChildInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.MobileInfoAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.MobileInfoGroup;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.MobileManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.view.MoveProgressBar;
public class MobilelnfoActivity extends BaseActionBarActivity {
    TextView tv_power_info;
    MyReceiver myReceiver;
    ExpandableListView expandableListView;
    MoveProgressBar progressBar;
    MobileInfoAdapter adapter;
    MobileManager mobileManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_lnfo);
        initView();
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver,intentFilter);
        setActionBarBack("手机检测");
        initData();
        expandableListView.setAdapter(adapter);
    }
    public void initData(){
        adapter =new MobileInfoAdapter(this);
        mobileManager=new MobileManager(this);
        reviceDataUse();
    }
    public void reviceDataUse(){
        final ProgressDialog progressDialog=ProgressDialog.show(this,null,"加载中",false,true);
        new Thread(){
            @Override
            public void run() {
                super.run();
                final MobileInfoGroup mobileInfoGroup=new MobileInfoGroup(getResources().getDrawable(R.drawable.setting_info_icon_version),"设备信息");
                final List<MobileChildInfo> phone =mobileManager.getPhoneMessage();
                final MobileInfoGroup systemInfoGroup=new MobileInfoGroup(getResources().getDrawable(R.drawable.setting_info_icon_space),"系统信息");
                final List<MobileChildInfo> system =mobileManager.getSystemMessage();
                final MobileInfoGroup netInfoGroup=new MobileInfoGroup(getResources().getDrawable(R.drawable.setting_info_icon_root),"网络信息");
                final List<MobileChildInfo> net =mobileManager.getWIFIMessage();
                final MobileInfoGroup creamInfoGroup=new MobileInfoGroup(getResources().getDrawable(R.drawable.setting_info_icon_camera),"照相机信息");
                final List<MobileChildInfo> Cream =mobileManager.getCameraMessage();
                final MobileInfoGroup ramInfoGroup=new MobileInfoGroup(getResources().getDrawable(R.drawable.setting_info_icon_cpu),"储存信息");
                final List<MobileChildInfo> ram =mobileManager.getMemoryMessage(MobilelnfoActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addDataToAdapter(mobileInfoGroup,phone);
                        adapter.addDataToAdapter(systemInfoGroup,system);
                        adapter.addDataToAdapter(netInfoGroup,net);
                        adapter.addDataToAdapter(creamInfoGroup,Cream);
                        adapter.addDataToAdapter(ramInfoGroup,ram);
                        adapter.notifyDataSetChanged();
                        progressDialog.cancel();
                    }
                });
                super.run();

            }
        }.start();


    }
    @Override
    public void initView(){
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);
        tv_power_info=(TextView)findViewById(R.id.tv_power_info);
        progressBar=(MoveProgressBar)findViewById(R.id.progressBar);
        expandableListView=(ExpandableListView)findViewById(R.id.elv_mobilelnfo);
    }
    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            final int currentPower=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            tv_power_info.setText(currentPower+"%");
            progressBar.setProgressMoved(currentPower);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_actionbar_left_icon:
                finish();
                break;
        }
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
