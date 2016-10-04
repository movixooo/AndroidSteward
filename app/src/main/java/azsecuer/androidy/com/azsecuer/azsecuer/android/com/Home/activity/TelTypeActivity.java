package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.IOException;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.TelTypeAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db.AssetsDBManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db.DBManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.TelTypeInfo;
public class TelTypeActivity extends BaseActionBarActivity{
    private ListView lv_tel_list;
    private TelTypeAdapter telTypeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_type);
        setActionBarBack("通讯大全");
        information();
        initView();
        instenerView();
    }
    @Override
    public void initView() {
        lv_tel_list=(ListView) findViewById(R.id.lv_tel_list);
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_actionbar_left_icon:
                finish();//销毁当前类
                break;
        }
    }
    @Override
    public void instenerView() {
       lv_tel_list .setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Bundle bundle=new Bundle();
               int idx = ((TelTypeInfo)telTypeAdapter.getItem(position)).getIdx();//取出数据库中点击的哪一项的id
               String typeName=((TelTypeInfo)telTypeAdapter.getItem(position)).getTypeName();//取出数据库中点击的哪一项的name
               bundle.putInt("idx",idx);            //带着点击的id传到下一个类
               bundle.putString("name",typeName);   //带着点击的name传到下一个类
               startActivityNow(TelListActivity.class,bundle);//跳转到下一个界面带着值
           }
       });
    }
    public void information(){
        initDBData();//判断有没有数据库文件，没有则拷贝一份
        lv_tel_list = (ListView)findViewById(R.id.lv_tel_list);
        telTypeAdapter = new TelTypeAdapter(this);
        telTypeAdapter.setDatas(DBManager.readTableClassList());// 读取电话类型数据传给适配器
        lv_tel_list.setAdapter(telTypeAdapter);
    }
    private void initDBData(){
        DBManager.createFile(this,"commonnum.db");//创建路径和文件
        if(DBManager.dbFileIsExists()){//判断是否有这个文件
            try {
                AssetsDBManager.copyDBFileToDB(this,"commonnum.db",DBManager.fileDB);//拷贝一个commonnum.db
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
        }
    }
    @Override
    public void onAnimationStart(Animation animation) {
    }
    @Override
    public void onAnimationEnd(Animation animation) {
    }
    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}