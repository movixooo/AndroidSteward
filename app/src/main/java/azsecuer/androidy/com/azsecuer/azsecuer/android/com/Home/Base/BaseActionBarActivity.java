package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import azsecuer.androidy.com.azsecuer.R;
/**
 * Created by Administrator on 2016.8.4.
 */
public abstract class BaseActionBarActivity extends BaseActivity{
    ImageView iv_actionbar_left_icon,iv_actionbar_right_icon;
    TextView tv_actionbar_title;
    public void findActionBarView() throws NotFoundActionBarException{
        iv_actionbar_left_icon=(ImageView)findViewById(R.id.iv_actionbar_left_icon);
        iv_actionbar_right_icon=(ImageView)findViewById(R.id.iv_actionbar_right_icon);
        tv_actionbar_title=(TextView)findViewById(R.id.tv_actionbar_title);
    }
    class NotFoundActionBarException extends Exception{
        public NotFoundActionBarException(){
            super("是否AcitionBar?这里没有找到");
        }
    }
    public void setActionBar(int leftIconResID,int rigthIconResID,String actionBarTitle){//设置最顶层的三个图标
        try{
            findActionBarView();
        }catch (NotFoundActionBarException e){
        }
        if ( leftIconResID==-1){
            iv_actionbar_left_icon.setVisibility(View.INVISIBLE);
        }else{
            iv_actionbar_left_icon.setImageResource(leftIconResID);
        }
        if (rigthIconResID==-1) {
            iv_actionbar_right_icon.setVisibility(View.INVISIBLE);
        }else{
            iv_actionbar_right_icon.setImageResource(rigthIconResID);
        }
        if (actionBarTitle==null){
            tv_actionbar_title.setVisibility(View.INVISIBLE);
        }else{
            tv_actionbar_title.setText(actionBarTitle);
        }
    }
    protected void setActionBarBack(String actonBarTitle){
        setActionBar(R.drawable.btn_homeasup_default,-1,actonBarTitle);
    }
}
