package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.SoftwareBrowseInffoAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.SoftwareBrowseInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.SoftwareManager;
public class SoftwareManageActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener{
    private ListView lv_software_manage;
    private Intent intent;
    private List<SoftwareBrowseInfo> speedupInfos;
    private SoftwareManager softwareManager;
    private SoftwareBrowseInffoAdapter softwareBrowseInffoAdapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_manage);
        intent=this.getIntent();
        setActionBarBack(intent.getExtras().getString("name"));
        initView();
    }
    @Override
    public void initView() {
        progressBar=(ProgressBar) findViewById(R.id.rb_clean);
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);//返回键
        lv_software_manage=(ListView)findViewById(R.id.lv_clean);
        lv_software_manage.setOnItemClickListener(this);
    }
    @Override
    public void instenerView() {
        intent=this.getIntent();
        speedupInfos=new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                softwareBrowseInffoAdapter=new SoftwareBrowseInffoAdapter(SoftwareManageActivity.this);
                softwareManager=SoftwareManager.getInstance(SoftwareManageActivity.this);
                speedupInfos= softwareManager.getSoftware(intent.getExtras().getInt("key"));
                softwareBrowseInffoAdapter.setDatas(speedupInfos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        softwareBrowseInffoAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                        lv_software_manage.setVisibility(View.VISIBLE);
                        lv_software_manage.setAdapter(softwareBrowseInffoAdapter);
                    }
                });
            }
        }.start();
    }
    @Override
    protected void onResume() {
        super.onResume();
            progressBar.setVisibility(View.VISIBLE);
            instenerView();
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
        final int positions=position;
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("是否确定卸载"+speedupInfos.get(positions).lable)
                .setCancelable(true)//是否能被取消
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//确定按钮
                    //需要注意的是监听事件  这里的事件DialogInterface不要和view混淆
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//点击确定卸载软件
                        Intent intent=new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:"+speedupInfos.get(positions).packagename));
                        startActivity(intent);
                    }
                });
        builder.show();//呈现Dialog
    }
}