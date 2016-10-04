package game.androidy.com.viewpager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
/**
 * Created by Administrator on 2016.8.30.
 */
public class Viewpager1 extends PagerAdapter {
    private List<View> datas;
    public Viewpager1(List<View> datas){
        this.datas=datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        container.addView(datas.get(position));
        return datas.get(position);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(datas.get(position));
    }
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles.get(position);
//    }//顶上的东西
}
