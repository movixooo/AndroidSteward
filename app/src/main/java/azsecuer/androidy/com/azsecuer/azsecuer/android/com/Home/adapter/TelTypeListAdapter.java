package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.base.BaseDataAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.TelNumberInfo;
/**
 * @author: leejohngoodgame
 * @date: 2016/8/11 16:33
 * @email:18328541378@163.com
 */
public class TelTypeListAdapter extends BaseDataAdapter {
    public TelTypeListAdapter(Context conetxt){
        super(conetxt);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//通讯大全第二个界面的适配器
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = super.layoutInflater.inflate(R.layout.listview_list_activity,null);
            viewHolder = new ViewHolder();
            viewHolder.name= (TextView)convertView.findViewById(R.id.tv_list_listview_left);
            viewHolder.number=(TextView)convertView.findViewById(R.id.tv_list_listview_right);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TelNumberInfo telNumberInfo = (TelNumberInfo) super.datas.get(position);
        viewHolder.name.setText(telNumberInfo.getName());
        viewHolder.number.setText(telNumberInfo.getNumber());
        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView number;
    }
}
