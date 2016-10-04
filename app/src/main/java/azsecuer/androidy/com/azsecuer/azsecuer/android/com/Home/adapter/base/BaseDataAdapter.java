package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.base;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
/**
 * @author: leejohngoodgame
 * @date: 2016/8/11 16:20
 * @email:18328541378@163.com
 */
public abstract class BaseDataAdapter<E> extends BaseAdapter{
    protected List<E> datas = new ArrayList<E>();
    protected Context context;
    protected LayoutInflater layoutInflater;
    /**
     * 构造方法
     */
    public BaseDataAdapter(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public BaseDataAdapter() {
        super();
    }
    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public E getItem(int position) {
        return datas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 设置数据
     * @param datas 数据源
     */
    public void setDatas(List<E> datas){
        this.datas = datas;
    }
    /**
     * 删除
     * @param position 删除的索引
     */
    public void removeDataFromAdpter(int position){
        this.datas.remove(position);
    }
    /**
     * 删除
     * @param e 删除的对象
     */
    public void removeDataFromAdpter(E e){
        this.datas.remove(e);
    }
    /**
     * 添加
     * @param e 添加的对象
     */
    public void addDataToAdapter(E e){
        if(e != null)
            this.datas.add(e);
    }
    /**
     * 清除所有数据
     */
    public void clear(){
        this.datas.clear();
    }

    /**
     * 返回所有的数据源
     */
    public List<E> getDatasFromAdapter(){
        return this.datas;
    }

}
