package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.base.BaseDataAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.TelTypeInfo;
/**
 * Created by Administrator on 2016.8.9.
 */
public class TelTypeAdapter extends BaseDataAdapter {//通讯大全第一个界面的适配器
    public TelTypeAdapter(Context context){
        super(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = super.layoutInflater.inflate(R.layout.listview_tel_activity,null);
            viewHolder = new ViewHolder();
            viewHolder.textview= (TextView)convertView.findViewById(R.id.tv_tel_listview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TelTypeInfo typeInfo = (TelTypeInfo) super.datas.get(position);
        viewHolder.textview.setText(typeInfo.getTypeName());
        return convertView;
    }
    class ViewHolder{
        TextView textview;
    }
}