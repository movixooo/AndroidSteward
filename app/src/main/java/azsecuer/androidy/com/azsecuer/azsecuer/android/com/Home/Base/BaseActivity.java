package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.Base;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import azsecuer.androidy.com.azsecuer.R;
public abstract class BaseActivity extends Activity implements View.OnClickListener ,Animation.AnimationListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
    public abstract void initView();
    public abstract void instenerView();
    protected void startActivityNow(Class target){//跳转到target类里
        Intent intent=new Intent(this,target);
        startActivity(intent);
    }
    protected void startActivityNow(Class target, Bundle bundle){//带上bunble的值跳转到target类里
        Intent intent=new Intent(this,target);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
