package game.androidy.com.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016.8.30.
 */
public class TabTextView extends TextView{
    private int position;
    private boolean isSelect;
    private OnSelectChangedListener listener;

    public OnSelectChangedListener getListener() {
        return listener;
    }

    public void setListener(OnSelectChangedListener listener) {
        this.listener = listener;
    }
    public int getPosition(){return position;}

    public void setPosition(int position){this.position=position;}

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        if(listener!=null){
            // 监听到更改以后调用相应的方法 这个时候并不知道调用的方法具体是谁
            listener.onSelectChangeListener(this,select);
        }
    }
    public TabTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public TabTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabTextView(Context context) {
        this(context,null);
    }

}
