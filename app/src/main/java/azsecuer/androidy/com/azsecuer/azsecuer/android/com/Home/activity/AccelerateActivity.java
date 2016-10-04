package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.SpeedupAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db.SpeedupManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.SpeedupInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.MemoryManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.MobileManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.PublicUtils;
public class AccelerateActivity extends BaseActionBarActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView tv_brand,tv_model,tv_memory,tv_actionbar_button;
    private ProgressBar progressbar,rb_clean;
    private View bt_Button;
    private ListView lv_clean;
    private SpeedupAdapter speedupAdapter;
    private CheckBox cb_clean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerate);
        setActionBarBack("手机加速");
        initView();
        instenerView();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_actionbar_left_icon:
                finish();
                break;
            // 显示、隐藏系统进程
            case R.id.tv_actionbar_button:
                SpeedupManager sm = SpeedupManager.getInstance(AccelerateActivity.this);
                List<SpeedupInfo> sysInfos = sm.getRuningApp(SpeedupManager.CLASSIFY_SYS, false);
                // 当前按钮显示的文本
                String button_text = tv_actionbar_button.getText().toString();
                String button_text_show = getResources().getString(R.string.speedup_button_systemp_show);
                String button_text_hide = getResources().getString(R.string.speedup_button_systemp_hide);
                // 当前按钮显示的文本为: 隐藏系统进程
                if (button_text.equals(button_text_hide)) {
                    // 将所有系统进程(无需重新加载), 从adapter中移除
                    speedupAdapter.getDataFromAdapter().removeAll(sysInfos);
                    tv_actionbar_button.setText(R.string.speedup_button_systemp_show);
                    speedupAdapter.notifyDataSetChanged();
                    lv_clean.setSelection(0); // 定位到0位置
                }
                // 当前按钮显示的文本为: 显示系统进程
                if (button_text.equals(button_text_show)) {
                    // 将所有系统进程(无需重新加载), 添加到adapter中
                    speedupAdapter.getDataFromAdapter().addAll(sysInfos);
                    tv_actionbar_button.setText(R.string.speedup_button_systemp_hide);
                    speedupAdapter.notifyDataSetChanged();
                    lv_clean.setSelection(speedupAdapter.getCount() - 1); // 定位到最后位置
                }
                break;
            // 一键清理
            case R.id.tb_clean_Bottom:
                List<SpeedupInfo> infos = speedupAdapter.getDataFromAdapter();
                for (SpeedupInfo info : infos) {
                    if (info.isSelected) {
                        String packageName = info.processName;
                        SpeedupManager.getInstance(this).kill(packageName);
                    }
                }
                // 在重新加载所有正在运行进程前重置UI线程
                cb_clean.setChecked(false);
                // 在重新加载所有正在运行进程前重置UI线程
                tv_actionbar_button.setText(R.string.speedup_button_systemp_show);
                // 重新加载所有正在运行进程(此方法 加载所有,但只添加显示用户进程)
                instenerView();
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonVie,boolean isChecked) {
        List<SpeedupInfo> infos = speedupAdapter.getDataFromAdapter();
        for (SpeedupInfo info : infos) {
            info.isSelected = isChecked;
        }
        speedupAdapter.notifyDataSetChanged();
    }
    @Override
    public void initView() {
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);
        tv_brand=(TextView)findViewById(R.id.tv_actionbar_brand);
        tv_model=(TextView)findViewById(R.id.tv_actionbar_model);
        tv_memory=(TextView)findViewById(R.id.tv_actionbar_memory);
        progressbar=(ProgressBar)findViewById(R.id.pb_actionbar_memory);
        bt_Button=findViewById(R.id.bt_Button);
        rb_clean=(ProgressBar)findViewById(R.id.rb_clean);
        lv_clean=(ListView)findViewById(R.id.lv_clean);
        tv_actionbar_button=(TextView)findViewById(R.id.tv_actionbar_button);
        tv_actionbar_button.setOnClickListener(this);
        findViewById(R.id.tb_clean_Bottom).setOnClickListener(this);
        cb_clean=(CheckBox)findViewById(R.id.cb_clean);
        cb_clean.setOnCheckedChangeListener(this);
        speedupAdapter = new SpeedupAdapter(this);
        lv_clean.setAdapter(speedupAdapter);
    }
    @Override
    public void instenerView() {
        updataMemory();
        bt_Button.setVisibility(View.GONE);
        rb_clean.setVisibility(View.VISIBLE);
        lv_clean.setVisibility(View.INVISIBLE);
        // 新线程异步处理加载正在运行的进程
        // 线程这样随意处理，不做管理其实还是很危险的
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 加载数据(使用写好的手机加速业务逻辑处理)
                SpeedupManager sm = SpeedupManager.getInstance(AccelerateActivity.this);
                List<SpeedupInfo> speedupInfos = null;
                // 将用户进程重置到适配器
                speedupInfos = sm.getRuningApp(SpeedupManager.CLASSIFY_USER, true);
                speedupAdapter.resetDataToAdapter(speedupInfos);//　setDatas
                // UI更新操作
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speedupAdapter.notifyDataSetChanged();
                        rb_clean.setVisibility(View.INVISIBLE);
                        bt_Button.setVisibility(View.VISIBLE);
                        lv_clean.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }
    private void updataMemory(){
        String brand=new MobileManager(this).getPhoneName1();//获取手机品牌
        String model=new MobileManager(this).getPhoneModelName();//获取手机型号
        long totalram=MemoryManager.getTotalRamMemory();//总共运行内存
        long remainingram=MemoryManager.getAvailRamMemory(this);//剩余运行内存
        String TotalRAM= PublicUtils.formatSize(totalram);
        String RemainingRAM=PublicUtils.formatSize(remainingram);
        tv_brand.setText(brand);
        tv_model.setText(model);
        tv_memory.setText("可用内存:"+RemainingRAM+"/"+TotalRAM);
        progressbar.setMax((int)((totalram)/1024/1024));
        progressbar.setProgress((int)((totalram-remainingram)/1024/1024));
    }
    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationEnd(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}
}
