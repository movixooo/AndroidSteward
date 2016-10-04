package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base.BaseActionBarActivity;
public class WelcomeActivity extends BaseActionBarActivity implements Animation.AnimationListener{
    ImageView tv_welcome_icon1,tv_welcome_icon2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        instenerView();
    }
    public void initView(){
        tv_welcome_icon1=(ImageView)findViewById(R.id.iv_welcome_icon1);
        tv_welcome_icon2=(ImageView)findViewById(R.id.iv_welcome_icon2);
    }
    public void instenerView(){//开启动画
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.anim_welcome_icon1);
        Animation animation1=AnimationUtils.loadAnimation(this,R.anim.anim_welcome_icon2);
        tv_welcome_icon1.startAnimation(animation);
        tv_welcome_icon2.startAnimation(animation1);
        animation.setAnimationListener(this);
    }
    @Override
    public void onAnimationStart(Animation animation){}
    @Override
    public void onAnimationEnd(Animation animation){
        startActivityNow(HomeActivity.class);
        finish();
    }
    @Override
    public void onAnimationRepeat(Animation animation){}
    @Override
    public void onClick(View v){}
}