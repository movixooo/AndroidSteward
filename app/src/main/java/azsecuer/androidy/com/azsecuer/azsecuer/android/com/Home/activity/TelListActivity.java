package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.TelTypeListAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db.DBManager;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.TelNumberInfo;
public class TelListActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener{
    private ListView tel_list_activity;
    TelTypeListAdapter telTypeListAdapter;
    List<TelNumberInfo> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_list);
        Intent intent=this.getIntent();
        setActionBarBack(intent.getExtras().getString("name"));//接收上一个类传来的name值，传给提示的方法
        initView();
        instenerView();
    }
    @Override
    public void initView() {
        findViewById(R.id.iv_actionbar_left_icon).setOnClickListener(this);
        tel_list_activity=(ListView)findViewById(R.id.lv_list_tel);
        tel_list_activity.setOnItemClickListener(this);
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
    public void instenerView(){
        int idx = this.getIntent().getExtras().getInt("idx");//接收上一个类传来的id
        telTypeListAdapter=new TelTypeListAdapter(this);
        datas = DBManager.readTabelNumber(idx);//通过id找到数据库id下面的数据
        telTypeListAdapter.setDatas(datas);
        tel_list_activity.setAdapter(telTypeListAdapter);//调用适配器
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDiaLog(this,datas.get(position).getName(),datas.get(position).getNumber());
    }
    private void showDiaLog(final Context context,String NAME,String NUMBER){//提示框且拨打电话的方法
        //创造者
        final String number=NUMBER;
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("拨号").setMessage("是否确定拨打"+NAME+"？\n"+"tel:"+number)
                .setCancelable(true)//是否能被取消
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//确定按钮
                    //需要注意的是监听事件  这里的事件DialogInterface不要和view混淆
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启打电话的操作
                        //申请权限
                       Intent intent=new Intent(Intent.ACTION_CALL);//创建一个打电话的意图
                       intent.setData(Uri.parse("tel://"+number));//将电话的number转化为uri设置给intent
                       startActivity(intent);//开启打电话的操作
                    }
                });
        builder.show();//呈现Dialog
    }
    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationEnd(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}
}
