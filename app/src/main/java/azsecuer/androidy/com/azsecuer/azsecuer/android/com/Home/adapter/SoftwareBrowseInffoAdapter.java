package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.base.BaseDataAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.SoftwareBrowseInfo;

/**
 * Created by Administrator on 2016.8.22.
 */
public class SoftwareBrowseInffoAdapter extends BaseDataAdapter<SoftwareBrowseInfo> {
    public SoftwareBrowseInffoAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = super.layoutInflater.inflate(R.layout.listview_software_manage,null);
            viewHolder = new ViewHolder();
            viewHolder.drawable=(ImageView) convertView.findViewById(R.id.iv_software_left);//图片
            viewHolder.lable= (TextView)convertView.findViewById(R.id.tv_software_top);//软件名
            viewHolder.packagename=(TextView)convertView.findViewById(R.id.tv_software_bottom);//包名
            viewHolder.version=(TextView)convertView.findViewById(R.id.tv_software_right);//版本
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SoftwareBrowseInfo softwareBrowseInfo = super.datas.get(position);
        viewHolder.drawable.setImageDrawable(softwareBrowseInfo.drawable);
        viewHolder.lable.setText(softwareBrowseInfo.lable);
        viewHolder.packagename.setText(softwareBrowseInfo.packagename);
        viewHolder.version.setText(softwareBrowseInfo.version);
        return convertView;
    }
    class ViewHolder{
        ImageView drawable;
        TextView lable,packagename,version;
    }
}
