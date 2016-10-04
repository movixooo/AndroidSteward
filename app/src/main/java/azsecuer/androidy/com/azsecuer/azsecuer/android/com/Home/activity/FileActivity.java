package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashMap;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.FileServiceAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.FileClassInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.FileSearchManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr.FileSearchTypeEvent;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.PublicUtils;
public class FileActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener{
    private FileServiceAdapter adapter;
    public static long price;
    public static String name;
    private TextView tv_search,tv_file_size;
    private HashMap<String, Long> fileSizes;
    private Thread searchThread;
    private boolean is=false;
    private ListView listview;
    private FileSearchManager searchManager;
    private long totalSize;
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                // onSearchStart 当开始搜索时更新UI
                case 0:
                    tv_search.setClickable(false);
                    tv_search.setText("搜索中");
                    break;
                // onSearching 每搜索到一个文件更新UI
                case 1:
                    // String fileType = (String) msg.obj;
                    // long size = fileSizes.get(fileType);
                    tv_file_size.setText(PublicUtils.formatSize(totalSize));
                    break;
                // onSearchEnd 搜索结束更新UI
                case 2:
                    int searchLocationRom = msg.arg1;
                    switch (searchLocationRom) {
                        // 内置空间搜索结束后UI更新 (click为true,可再进行深度搜索)
                        case 0:
                            tv_search.setClickable(true);
                            tv_search.setText("深度搜索");
                            break;
                        // 外置空间搜索结束后UI更新(click为false,搜索完毕)
                        case 1:
                            tv_search.setClickable(false);
                            tv_search.setText("搜索完毕");
                            break;
                    }
                    int count = adapter.getCount();
                    for (int i = 0; i < count; i++) {
                        FileClassInfo info = adapter.getItem(i);
                        info.size = fileSizes.get(info.fileType);
                        info.loadingOver = true;
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        setActionBarBack("文件管理");
        initView();
        instenerView();
        loadTheData();
    }
    private void loadTheData() {
        adapter = new FileServiceAdapter(this);
        adapter.addDataToAdapter(new FileClassInfo("文本文件", FileSearchTypeEvent.TYPE_TXT));
        adapter.addDataToAdapter(new FileClassInfo("图像文件", FileSearchTypeEvent.TYPE_IMAGE));
        adapter.addDataToAdapter(new FileClassInfo("APK文件", FileSearchTypeEvent.TYPE_APK));
        adapter.addDataToAdapter(new FileClassInfo("视频文件", FileSearchTypeEvent.TYPE_VIDEO));
        adapter.addDataToAdapter(new FileClassInfo("音频文件", FileSearchTypeEvent.TYPE_AUDIO));
        adapter.addDataToAdapter(new FileClassInfo("压缩文件", FileSearchTypeEvent.TYPE_ZIP));
        adapter.addDataToAdapter(new FileClassInfo("其它文件", FileSearchTypeEvent.TYPE_OTHER));
        listview.setAdapter(adapter);
        totalSize = 0;
        searchManager = FileSearchManager.getInstance(true);
        fileSizes = searchManager.getFileSizes();
        // 异步搜索内置文件
        asyncSearchInRomFile();
    }
    // 异步搜索内置文件
    private void asyncSearchInRomFile() {
        searchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                searchManager.setOnFileSearchListener(searchListener);
                searchManager.startSearchFromInRom(FileActivity.this);
            }
        });
        searchThread.start();
    }
    // 异步搜索外置文件
    private void asyncSearchOutRomFile() {
        searchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                searchManager.setOnFileSearchListener(searchListener);
                searchManager.startSearchFromOutRom(FileActivity.this);
            }
        });
        searchThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("qwe",name+"     "+price);
        if (price!=0&&is){
            fileSizes = searchManager.getFileSizes(); // 文件大小集合(Map)
            adapter = new FileServiceAdapter(this);
            if (name=="TXT"){
                adapter.addDataToAdapter(new FileClassInfo("文本文件", FileSearchTypeEvent.TYPE_TXT,fileSizes.get(FileSearchTypeEvent.TYPE_TXT)-price));
            }else{
                adapter.addDataToAdapter(new FileClassInfo("文本文件", FileSearchTypeEvent.TYPE_TXT,fileSizes.get(FileSearchTypeEvent.TYPE_TXT)));
            }
            if (name=="IMAGE"){
                adapter.addDataToAdapter(new FileClassInfo("图像文件", FileSearchTypeEvent.TYPE_IMAGE,fileSizes.get(FileSearchTypeEvent.TYPE_IMAGE)-price));
            }else{
                adapter.addDataToAdapter(new FileClassInfo("图像文件", FileSearchTypeEvent.TYPE_IMAGE,fileSizes.get(FileSearchTypeEvent.TYPE_IMAGE)));
            }
            if (name=="APK"){
                adapter.addDataToAdapter(new FileClassInfo("APK文件", FileSearchTypeEvent.TYPE_APK,fileSizes.get(FileSearchTypeEvent.TYPE_APK)-price));
            }else{
                adapter.addDataToAdapter(new FileClassInfo("APK文件", FileSearchTypeEvent.TYPE_APK,fileSizes.get(FileSearchTypeEvent.TYPE_APK)));
            }
            if (name=="VIDEO"){
                adapter.addDataToAdapter(new FileClassInfo("视频文件", FileSearchTypeEvent.TYPE_VIDEO,fileSizes.get(FileSearchTypeEvent.TYPE_VIDEO)-price));
            }else{
                adapter.addDataToAdapter(new FileClassInfo("视频文件", FileSearchTypeEvent.TYPE_VIDEO,fileSizes.get(FileSearchTypeEvent.TYPE_VIDEO)));
            }
            if (name=="AUDIO"){
                adapter.addDataToAdapter(new FileClassInfo("音频文件", FileSearchTypeEvent.TYPE_AUDIO,fileSizes.get(FileSearchTypeEvent.TYPE_AUDIO)-price));
            }else{
                adapter.addDataToAdapter(new FileClassInfo("音频文件", FileSearchTypeEvent.TYPE_AUDIO,fileSizes.get(FileSearchTypeEvent.TYPE_AUDIO)));
            }
            if (name=="ZIP"){
                adapter.addDataToAdapter(new FileClassInfo("压缩文件", FileSearchTypeEvent.TYPE_ZIP,fileSizes.get(FileSearchTypeEvent.TYPE_ZIP)-price));
            }else{
                adapter.addDataToAdapter(new FileClassInfo("压缩文件", FileSearchTypeEvent.TYPE_ZIP,fileSizes.get(FileSearchTypeEvent.TYPE_ZIP)));
            }
            if (name=="OTHER"){
                adapter.addDataToAdapter(new FileClassInfo("其它文件", FileSearchTypeEvent.TYPE_OTHER,fileSizes.get(FileSearchTypeEvent.TYPE_OTHER)-price));
            }else{
                adapter.addDataToAdapter(new FileClassInfo("其它文件", FileSearchTypeEvent.TYPE_OTHER,fileSizes.get(FileSearchTypeEvent.TYPE_OTHER)));
            }
            tv_file_size.setText(PublicUtils.formatSize(totalSize-price));
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 离开当前页面时，中止搜索
        searchManager.setStopSearch(true);
        // 离开当前页面时，中止线程
        if (searchThread != null) {
            searchThread.interrupt();
            searchThread = null;
        }
        System.gc();
    }
    /** 搜索监听,回调接口 */
    private FileSearchManager.OnFileSearchListener searchListener = new FileSearchManager.OnFileSearchListener() {
        @Override
        public void onSearchStart(int searchLocationRom) {
            // 线程通信
            mainHandler.sendEmptyMessage(0); // onSearchStart
        }
        @Override
        public void onSearching(String fileType, long totalSize) {
            // 保存文件总大小(全局)
            FileActivity.this.totalSize = totalSize;
            // 线程通信
            Message message = mainHandler.obtainMessage();
            message.what = 1; // onSearching
            message.obj = fileType;
            mainHandler.sendMessage(message);
        }
        @Override
        public void onSearchEnd(boolean isExceptionEnd, int searchLocationRom) {
            // 线程通信
            Message message = mainHandler.obtainMessage();
            message.what = 2;
            message.arg1 = searchLocationRom;
            mainHandler.sendMessage(message); // onSearchEnd
        }
    };
    @Override
    public void initView() {
        listview=(ListView)findViewById(R.id.lv_clean);
        listview.setOnItemClickListener(this);
        tv_file_size=(TextView)findViewById(R.id.tv_file_size);//显示大小
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this); //返回键
        tv_search=(TextView) findViewById(R.id.tv_file_depth);
        tv_search.setOnClickListener(this);          //深度加速
        tv_search.setClickable(false);
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
            case R.id.tv_file_depth:
                // 按下"深度搜索"后,click失效
                tv_search.setClickable(false);
                // 按下"深度搜索"后,更新Adapter上UI
                int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    adapter.getItem(i).loadingOver = false;
                }
                adapter.notifyDataSetChanged();
                // 执行"深度搜索" - 异步搜索外置文件
                asyncSearchOutRomFile();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        is=true;
        Bundle bundle=new Bundle();
        FileClassInfo classInfo = adapter.getItem(position);
        bundle.putString("fileType", classInfo.fileType);
        name=classInfo.fileType;
        startActivityNow(FileBrowseActivity.class,bundle);
    }
}
