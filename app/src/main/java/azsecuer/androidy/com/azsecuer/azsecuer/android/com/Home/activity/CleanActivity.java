package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.RubbishInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.RubbishInfoAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db.AssetsDBManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db.DBManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.FileUtils;
public class CleanActivity extends BaseActionBarActivity {
    ListView lv_clean;
    RubbishInfoAdapter rubbishInfoAdapter;
    List<RubbishInfo> rubbishInfoList;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        setActionBarBack("垃圾清理");
        asynLoadRubbishInfo();
        initView();
        lv_clean.setAdapter(rubbishInfoAdapter);
    }
    private void asynLoadRubbishInfo(){
        initDBData();
        rubbishInfoList = new ArrayList<>();
        rubbishInfoAdapter = new RubbishInfoAdapter(this);
        new Thread(){
            @Override
            public void run() {
                super.run();
                //TODO 读取数据库 1.
                rubbishInfoList=DBManager.readClean(CleanActivity.this);
                rubbishInfoAdapter.setDatas(rubbishInfoList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rubbishInfoAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                        lv_clean.setVisibility(View.VISIBLE);
                    }
                });
            }

        }.start();
    }
    private void initDBData(){
        DBManager.createFile(this,"clearpath.db");//创建路径和文件clearpath
        if(DBManager.dbFileIsExists()){//判断是否有这个文件
            try {
                AssetsDBManager.copyDBFileToDB(this,"clearpath.db",DBManager.fileDB);//拷贝一个commonnum.db
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
        }
    }
    @Override
    public void initView() {
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);//返回键
        findViewById(R.id.tv_clean_bottom).setOnClickListener(this);//删除所选文件
        lv_clean =(ListView)findViewById(R.id.lv_clean);
        progressBar=(ProgressBar)findViewById(R.id.rb_clean);
    }
    @Override
    public void instenerView() {}
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
            case R.id.tv_clean_bottom:
                asynDeleteFile();
                break;
        }
    }
    private void asynDeleteFile(){
        lv_clean.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(){
            @Override
            public void run() { // 删除的逻辑
                Iterator<RubbishInfo> iterator = rubbishInfoList.iterator();
                while(iterator.hasNext()){
                    RubbishInfo rubbishInfo = iterator.next();
                    if(rubbishInfo.isChecked){// 为true 代表着选中状态
                        File file = new File(rubbishInfo.filePath);
                        FileUtils.deleteFile(file);// 执行删除的操作
                        iterator.remove();// 从数据源中删除它
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() { // 回到UI进行数据更新
                        rubbishInfoAdapter.setDatas(rubbishInfoList);
                        rubbishInfoAdapter.notifyDataSetChanged();
                        // 控件的显示状态切换
                        progressBar.setVisibility(View.INVISIBLE);
                        lv_clean.setVisibility(View.VISIBLE);
                    }
                });
                super.run();
            }
        }.start();
    }
}
