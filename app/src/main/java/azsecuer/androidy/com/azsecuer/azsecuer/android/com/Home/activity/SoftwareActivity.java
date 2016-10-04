package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.MemoryManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.PublicUtils;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.view.PiecharView;

public class SoftwareActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {
    TextView tv_software_inlay,tv_software_extraposition,tv_software_centre1,tv_software_centre2;
    ProgressBar progressbar1,progressbar2;
    ListView lv_tel_list;
    String [] data;
    PiecharView topview;
    int outRomProp360,inRomProp360;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);
        setActionBarBack("软件管理");
        initView();
        instenerView();
    }
    @Override
    public void initView() {
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);
        tv_software_inlay=(TextView)findViewById(R.id.tv_software_inlay);//内置空间
        tv_software_extraposition=(TextView)findViewById(R.id.tv_software_extraposition);//外置空间
        tv_software_centre1=(TextView)findViewById(R.id.tv_software_centre1);//内置空间（已使用/全部）
        tv_software_centre2=(TextView)findViewById(R.id.tv_software_centre2);//外置空间（已使用/全部）
        progressbar1=(ProgressBar)findViewById(R.id.pb_software_centre1);//内置空间的显示条
        progressbar2=(ProgressBar)findViewById(R.id.pb_software_centre2);//外置空间的显示条
        lv_tel_list=(ListView)findViewById(R.id.lv_software_list);
        lv_tel_list.setOnItemClickListener(this);
        topview=(PiecharView)findViewById(R.id.pb_software_activity);
    }
    @Override
    public void instenerView() {
        data=new String[]{"所有软件","系统软件","用户软件"};
        ArrayAdapter<String> adyapter=new ArrayAdapter<String>(this,R.layout.listview_tel_activity,R.id.tv_tel_listview,data);
        lv_tel_list.setAdapter(adyapter);
        new Thread(){
            @Override
            public void run() {
                final long ramall= MemoryManager.getTotalRamMemory();//总运行内存
                final long ramremaining=MemoryManager.getAvailRamMemory(SoftwareActivity.this);//剩余运行内存
                final long allrom= MemoryManager.getTotalExternalMemorySize();//总外置内存
                final long remainingrom=MemoryManager.getAvailableExternalMemorySize();//外置内存剩余大小
                final long serviceram=ramall-ramremaining;//RAM使用情况
                final long servicerom=allrom-remainingrom;//ROM使用情况
                final String RAMall=PublicUtils.formatSize(ramall);//总运行内存
                final String allROM=PublicUtils.formatSize(allrom);//总外置内存
                final String serviceRAM=PublicUtils.formatSize(serviceram);//RAM使用情况
                final String serviceROM=PublicUtils.formatSize(servicerom);//ROM使用情况
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_software_inlay.setText(RAMall);//设置总运行内存
                        tv_software_extraposition.setText(allROM);//设置总外置内存
                        tv_software_centre1.setText(serviceRAM+"/"+RAMall);
                        tv_software_centre2.setText(serviceROM+"/"+allROM);
                        progressbar1.setMax((int)(ramall/1024/1024));
                        progressbar1.setProgress((int)(serviceram/1024/1024));
                        progressbar2.setMax((int)(allrom/1024/1024));
                        progressbar2.setProgress((int)(servicerom/1024/1024));
                        // 计算出饼状图 的角度比例
                        long totalRom = ramall + allrom ;
                        inRomProp360 = (int) ((float) ramall / totalRom * 360);
                        outRomProp360 = 360 - inRomProp360;
                        // 设置饼状图信息
                        int data[][] = new int[][]{{SoftwareActivity.this.getResources().getColor(R.color.cyan),inRomProp360,0},
                                {SoftwareActivity.this.getResources().getColor(R.color.orange),outRomProp360,0}};
                        topview.setAngleWithAnim2(data);

                    }
                });
            }
        }.start();
    }
    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationEnd(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_actionbar_left_icon:
                finish();
            break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle=new Bundle();
        bundle.putInt("key",position);
        bundle.putString("name",data[position]);
        startActivityNow(SoftwareManageActivity.class,bundle);
    }
}
