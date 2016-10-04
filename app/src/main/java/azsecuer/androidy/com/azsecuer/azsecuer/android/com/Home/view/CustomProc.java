package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import azsecuer.androidy.com.azsecuer.R;
/**
 * Created by Administrator on 2016.8.26.
 */
public class CustomProc extends View {
    /**
     *  画笔
     */
    private Paint paint;
    /**
     * 圆环颜色 背景色
     */
    private int roundColor;
    /**
     * 圆环颜色 progress顔色
     */
    private int roundProgressColor;
    /**
     * 進度文字的顔色
     */
    private int textColor;
    /**
     * 进度文字大小 size
     */
    private float textSize;
    /**
     * 圆环的宽度
     */
    private float roundWidth;
    /**
     * 进度条的最大值
     */
    private int max;
    /**
     * 当前的进度值
     */
    private int progress;
    /**
     * 进度文字 是否显示的标示 flag
     */
    private boolean textDisplayable;
    /**
     * 进度圆环的风格
     */
    private int style;
    /**
     * 默认提供两种风格、
     * STOKE 空心、
     * FILL 实心
     */
    public static final int STOKE = 0;
    public static final int FILL = 1;

    /**
     *  不能使用到xml
     * @param context
     */
    public CustomProc(Context context) {
        this(context,null);
    }

    /**
     * @param context
     * @param attrs 基本属性
     *              可以在xml中直接对属性赋值
     */
    public CustomProc(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomProc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(); // 初始化画笔
        /**
         * 初始化其他的一些属性
         * 如果没有取到相应的值 给一个默认值
         *
         */
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomProc);
        // 属性的设置
        roundColor = array.getColor(R.styleable.CustomProc_roundColor,context.getResources().getColor(R.color.colorAccent));
        roundProgressColor = array.getColor(R.styleable.CustomProc_roundProgressColor, Color.GREEN);
        textSize = array.getDimension(R.styleable.CustomProc_textSize,15);
        textColor = array.getColor(R.styleable.CustomProc_textColor, Color.BLUE);
        roundWidth = array.getDimension(R.styleable.CustomProc_roundWidth,5);
        max = array.getInteger(R.styleable.CustomProc_max,100);
        textDisplayable = array.getBoolean(R.styleable.CustomProc_textDisplayable,true);
        style = array.getInt(R.styleable.CustomProc_style,0);
        array.recycle();// 释放一下
    }

    /**
     *  画出相应的东西
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * #1先画背景
         */
        int centerX = getWidth() / 2 ;
        int raduis = (int)(centerX - roundWidth/2);
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        /**
         *  x 中心坐标          半径          画笔
         *  float cx, float cy, float radius, @NonNull Paint paint
         */
        canvas.drawCircle(centerX,centerX,raduis,paint);// 画出圆环
        /**
         *  #2 画出文本信息 progress int
         *  String text, float cx, float cy,  @NonNull Paint paint
         *  100%
         */
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);// 去除锯齿
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);// 设置文字的风格
        //注意类型转换
        String drawText = (int)(((float)progress / (float) max) * 100) +"%";
        float drawTextWidth = paint.measureText(drawText);// 画的文本信息的宽度
        if(textDisplayable && style == STOKE){// 是否需要画出文本信息
            //　注意　ｘｙ　的计算方法
            /**
             *  drawText 画的文本信息
             *  x  画文本的起始位置
             *  y  画文本的起始位置  baseLine
             *  画笔
             */
            canvas.drawText(drawText,centerX-drawTextWidth/2,centerX+textSize/2,paint);
        }
        /**
         * #3 画当前进度  圆弧
         */
        paint.setStrokeWidth(roundWidth);// 宽度
        paint.setColor(roundProgressColor);// 颜色
        // 定义出圆弧的形状和边界
        RectF rectF  = new RectF(centerX-raduis,centerX-raduis,centerX+raduis,centerX+raduis);
        Log.i("rectF",rectF.toString());
        switch (style){
            case STOKE:
                /**
                 * 画出圆弧形状
                 * oval 圆弧对象
                 * startAngle 开始角度
                 * sweepAngle 圆弧扫过的角度
                 * useCenter 为true 画出来就是一个扇形 false 为圆弧
                 * @NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,
                 @NonNull Paint paint
                 */
                paint.setStyle(Paint.Style.STROKE); // 画笔的风格 顺时针
                canvas.drawArc(rectF,-90,((float) progress/(float) max)*360,false,paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawArc(rectF,0,((float) progress/(float) max)*360,true,paint);
                break;
        }
    }
    public Paint getPaint() {
        return paint;
    }
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }
    public boolean isTextDisplayable() {
        return textDisplayable;
    }
    public void setTextDisplayable(boolean textDisplayable) {
        this.textDisplayable = textDisplayable;
    }
    public synchronized int getProgress() {
        return progress;
    }
    public synchronized void setProgress(int progress) {
        if(progress<0)
            this.progress = 0;
        if(progress>max)
            this.progress = max;
        if(progress <= max){
            this.progress = progress;
            postInvalidate();// 回调OnDraw
        }
    }

    /**
     * 基本动画一点点的增加
     * @param targetProgress
     */
    public synchronized  void startAnimSetProgress(final  int targetProgress){
        setProgress(0);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setProgress(getProgress() + 1);
                if (getProgress() >= targetProgress) {
                    setProgress(targetProgress);
                    this.cancel();
                }
            }
        };
        timer.schedule(timerTask,40,40);
    }
    /**
     * 先回退 后增加的动画
     * @param targetProgress
     */
    public synchronized  void startAnimSetProgress2(final  int targetProgress){
        setProgress(targetProgress);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int state = 0 ; // 动画状态 为0 代表减少 为1 代表增加、
            @Override
            public void run() {
                switch (state){
                    case 0:
                        setProgress(getProgress()-1);
                        if(getProgress() == 0)
                            state = 1;
                        break;
                    case 1:
                        setProgress(getProgress()+1);
                        if(getProgress() == targetProgress)
                            this.cancel();
                        break;
                }
            }
        };
        timer.schedule(timerTask,40,40);
    }

    /**
     * 先回退 后增加
     * 需要調整的方法
     */
    public synchronized void startAnimSetProgress3(final  int targetProgress){
        setProgress(targetProgress);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int state = 0 ; // 动画状态 为0 代表减少 为1 代表增加、
            int speedAngle = 1 ; // 圆弧速度
            @Override
            public void run() {
                switch (state){
                    case 0:
                        setProgress(getProgress()-speedAngle);
                        speedAngle++;
                        if(getProgress()-speedAngle<=0)
                        {
                            state = 1;
                            setProgress(0);
                        }
                        break;
                    case 1:
                        setProgress(getProgress()+speedAngle);
                        speedAngle--;
                        if(getProgress()+speedAngle>=targetProgress){
                            this.cancel();
                            setProgress(targetProgress);
                        }
                        break;
                }
            }
        };
        timer.schedule(timerTask,40,60);
    }
    public synchronized int getMax() {
        return max;
    }
    public synchronized void setMax(int max) {
        if(max < 0)
            this.setProgress(0);
        this.max = max;
    }
    public float getRoundWidth() {
        return roundWidth;
    }
    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }
    public float getTextSize() {
        return textSize;
    }
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }
    public int getTextColor() {
        return textColor;
    }
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
    public int getRoundProgressColor() {
        return roundProgressColor;
    }
    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }
    public int getRoundColor() {
        return roundColor;
    }
    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }
}
