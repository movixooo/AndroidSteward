package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter.base.BaseDataAdapter;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.RubbishInfo;

/**
 * Created by Administrator on 2016.8.17.
 */
public class RubbishInfoAdapter extends BaseDataAdapter<RubbishInfo>{

    public RubbishInfoAdapter(Context context) {
        super(context);
    }
    /**
     * 没有做相应的ViewHolder优化
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView == null){
            view = super.layoutInflater.inflate(R.layout.list_clean_activity,null);
        }else{
            view = convertView;
        }
        RubbishInfo rubbishInfo = getItem(position);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.cb_clean);
        TextView tv_softChineseName = (TextView)view.findViewById(R.id.tv_clean_left1);
        TextView tv_soft_rubbish_file_size = (TextView)view.findViewById(R.id.tv_clean_right);
        ImageView iv_rubbish_soft_icon = (ImageView)view.findViewById(R.id.iv_cclean);
        tv_soft_rubbish_file_size.setText(rubbishInfo.fileSizeStr+"");
        iv_rubbish_soft_icon.setImageDrawable(rubbishInfo.drawable);
        tv_softChineseName.setText(rubbishInfo.softChineseName);
        // 设置上Tag
        checkBox.setTag(rubbishInfo);
        checkBox.setChecked(rubbishInfo.isChecked);
        checkBox.setOnCheckedChangeListener(checkedChangeListener);
        return view;
    }
    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // 根据Tag中的值去修改相应的操作
            ((RubbishInfo)buttonView.getTag()).isChecked = isChecked;
        }
    };
}
