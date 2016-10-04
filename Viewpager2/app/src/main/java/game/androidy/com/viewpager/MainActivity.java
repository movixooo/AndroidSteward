package game.androidy.com.viewpager;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends Activity {
    ViewPager viewPager;
    View iv1,iv2,iv3;
    List<View> views;
    private List<TabTextView> tabs ;
    int [] tabIds={R.id.news,R.id.recreation,R.id.gossip};
    private View.OnClickListener taboOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {//选项卡的点击监听事件
            viewPager.setCurrentItem(((TabTextView)v).getPosition());
            ((TabTextView) v).setSelect(true);
        }
    };
    private OnSelectChangedListener tabOnSelectListener = new OnSelectChangedListener() {
        @Override
        public void onSelectChangeListener(View view, boolean isSelect) {
            if(isSelect)
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            else
                view.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        iv1= LayoutInflater.from(this).inflate(R.layout.activity_asdqwe,null);//解析布局文件
        iv2= LayoutInflater.from(this).inflate(R.layout.activity_2,null);
        iv3= LayoutInflater.from(this).inflate(R.layout.activity_3,null);
        views = new ArrayList<>();
        views.add(iv1);
        views.add(iv2);
        views.add(iv3);
        tabs=new ArrayList<>();
        for (int i=0;i<3;i++){
            TabTextView tabTextView=(TabTextView)findViewById(tabIds[i]);
            tabTextView.setPosition(i);
            tabTextView.setOnClickListener(taboOnClickListener);
            tabTextView.setListener(tabOnSelectListener);
            tabs.add(tabTextView);
        }
        Viewpager1 viewpager1=new Viewpager1(views);
        viewPager.setAdapter(viewpager1);
        viewPager.setCurrentItem(0);//设置第一个选项卡
        //添加监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动时
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            // 选中时
            // 当页卡被选中 联动到我们的TabTextView
            // 重新设置上我们的选中状态
            @Override
            public void onPageSelected(int position) {
                for(TabTextView tab: tabs){
                    if(tab.getPosition()!=position)
                        tab.setSelect(false);
                    else
                        tab.setSelect(true);
                }
            }
            // 滑动状态更改时
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
