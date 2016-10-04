package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.view;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Administrator on 2016.8.15.
 */
public class MoveProgressBar extends ProgressBar{//延时增加电池电量
    public MoveProgressBar(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }
    public void setProgressMoved(final int targetProgress){
        final Timer timer =new Timer();
        final TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                int moverState=getProgress()>targetProgress?1:2;
                switch (moverState){
                    case 1:
                        setProgress(getProgress()-1);
                    break;
                    case 2:
                        setProgress(getProgress()+1);
                    break;
                }
                if (getProgress()==targetProgress){
                    this.cancel();
                }
            }
        };
        timer.schedule(timerTask,500,15);//执行任务 500为开始任务之前的延时，15为每次增加之间的延时
    }
}