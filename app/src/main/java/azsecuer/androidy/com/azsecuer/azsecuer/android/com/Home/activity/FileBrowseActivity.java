package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.FileBrowseAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.FileInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.FileSearchManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.FileUtils;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.PublicUtils;
public class FileBrowseActivity extends BaseActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView tv_filebrowse_number;
    private TextView tv_filebrowse_size;
    private ListView liv_filebrowse;
    private TextView btn_delfile;
    private CheckBox cb_deffile;
    private long deletesize=0;
    private FileBrowseAdapter browseAdapter;
    private FileSearchManager searchManager;
    /** 文件类型(key) */
    private String fileType;
    /** 所有文件分类集合 */
    private HashMap<String, ArrayList<FileInfo>> fileInfos;
    /** 所有文件大小集合 */
    private HashMap<String, Long> fileSizes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browse);
        setActionBarBack("文件浏览");
        // 初始加载当前页面控件
        initView();
        // 控件监听
        instenerView();
        // 加载数据
        loadTheData();
    }
    @Override
    public void initView() {
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);
        // TODO Auto-generated method stub
        tv_filebrowse_number = (TextView) findViewById(R.id.tv_filebrowse_number);
        tv_filebrowse_size = (TextView) findViewById(R.id.tv_filebrowse_size);
        liv_filebrowse = (ListView) findViewById(R.id.liv_filebrowse);
        btn_delfile = (TextView) findViewById(R.id.btn_checkAndButton_button);
        cb_deffile = (CheckBox) findViewById(R.id.cb_checkAndButton_select);
    }
    @Override
    public void instenerView() {
        // TODO Auto-generated method stub
        cb_deffile.setOnCheckedChangeListener(this);
        btn_delfile.setOnClickListener(this);
        liv_filebrowse.setOnItemClickListener(this);
    }
    private void loadTheData() {
        // 取得FileServcieActivity传入的文件类型
        fileType = getIntent().getStringExtra("fileType");
        // 取得文件列表数据信息
        searchManager = FileSearchManager.getInstance(false);
        fileInfos = searchManager.getFileInfos(); // 文件实体集合(Map)
        fileSizes = searchManager.getFileSizes(); // 文件大小集合(Map)
        long size = fileSizes.get(fileType);
        long count = fileInfos.get(fileType).size();
        // 将文件列表数量和大小分别设置到对应的文件控件上
        tv_filebrowse_number.setText(""+ count);
        tv_filebrowse_size.setText(PublicUtils.formatSize(size-deletesize));
        FileActivity.price=deletesize;
        // 将文件实体集合数据适配到文件列表控件上
        browseAdapter = new FileBrowseAdapter(this,liv_filebrowse);
        browseAdapter.addDataToAdapter(fileInfos.get(fileType));
        liv_filebrowse.setAdapter(browseAdapter);
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        int count = browseAdapter.getCount();
        for (int i = 0; i < count; i++) {
            browseAdapter.getItem(i).isSelect = isChecked;
        }
        browseAdapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int viewID = v.getId();
        switch (viewID) {
            case R.id.iv_actionbar_left_icon:
                finish();
                break;
            case R.id.btn_checkAndButton_button:
                delete();
                break;
        }
    }
    //删除方法
    public void delete(){
        new Thread(){
            @Override
            public void run() {
                Iterator<FileInfo> iterator = fileInfos.get(fileType).iterator();
                while(iterator.hasNext()){
                    FileInfo fileInfo = iterator.next();
                    if(fileInfo.isSelect){// 为true 代表着选中状态
                        deletesize=deletesize+fileInfo.file.length();
                        FileActivity.price=deletesize;
                        Log.i("qwe",FileActivity.price+"");
                        FileUtils.deleteFile(fileInfo.file);// 执行删除的操作
                        iterator.remove();// 从数据源中删除它
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() { // 回到UI进行数据更新
                        browseAdapter.addDataToAdapter(fileInfos.get(fileType));
                        browseAdapter.notifyDataSetChanged();
                        loadTheData();//重新加载数据
                        // 控件的显示状态切换
                    }
                });
                super.run();
            }
        }.start();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        FileInfo fileInfo = browseAdapter.getItem(position);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(fileInfo.file);
        String type = fileInfo.openType;
        intent.setDataAndType(data, type);
        startActivity(intent);
    }
    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationEnd(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}
}
