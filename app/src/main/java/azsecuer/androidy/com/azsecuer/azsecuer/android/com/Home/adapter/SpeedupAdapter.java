package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.SpeedupInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.PublicUtils;

/**
 * Created by Administrator on 2016.8.21.
 */
public class SpeedupAdapter extends BaseAdapter implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private List<SpeedupInfo> dataList = new ArrayList<SpeedupInfo>();
    private LayoutInflater layoutInflater;
    public void addDataToAdapter(List<SpeedupInfo> speedupInfos) {
        if (speedupInfos != null) {
            dataList.addAll(speedupInfos);
        }
    }
    public void resetDataToAdapter(List<SpeedupInfo> speedupInfos) {
        dataList.clear();
        if (speedupInfos != null) {
            dataList.addAll(speedupInfos);
        }
    }
    public List<SpeedupInfo> getDataFromAdapter() {
        return dataList;
    }
    public SpeedupAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }
    @Override
    public SpeedupInfo getItem(int position) {
        // TODO Auto-generated method stub
        return dataList.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    // 视图类型数量
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    // getView中每个convertView在使用前都会来回调此方法进行缓存处理判断
    @Override
    public int getItemViewType(int position) {
        SpeedupInfo info = getItem(position);
        if (info.isSystemApp) {
            return 1;// 1: system
        }
        return 0;// 0: user
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        // UI
        View view = null;
        if (convertView == null) {
            switch (type) {
                case 0:
                    view = layoutInflater.inflate(R.layout.inflate_speedup_listitem_user, null);
                    break;
                case 1:
                    view = layoutInflater.inflate(R.layout.inflate_speedup_listitem_sys, null);
                    break;
            }
        } else {
            view = convertView;
        }
        // 对整个view进行监听处理(单击每个view时checkbox将选中或取消)
        view.setTag(position);
        view.setOnClickListener(this);
        CheckBox cb_speedup_clear = (CheckBox) view.findViewById(R.id.cb_speedup_clear);
        TextView tv_speedup_lable = (TextView) view.findViewById(R.id.tv_speedup_lable);
        TextView tv_speedup_memory = (TextView) view.findViewById(R.id.tv_speedup_memory);
        ImageView iv_speedup_icon = (ImageView) view.findViewById(R.id.iv_speedup_icon);
        cb_speedup_clear.setTag(position);
        cb_speedup_clear.setOnCheckedChangeListener(this);
        // DATA
        SpeedupInfo info = getItem(position);
        // DATA - UI
        cb_speedup_clear.setChecked(info.isSelected);
        tv_speedup_lable.setText(info.label);
        tv_speedup_memory.setText(PublicUtils.formatSize(info.memory));
        iv_speedup_icon.setImageDrawable(info.icon);
        // retur UI
        return view;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        CheckBox cb_speedup_clear = (CheckBox) v.findViewById(R.id.cb_speedup_clear);
        boolean checked = !getItem(position).isSelected;
        // UI修改
        cb_speedup_clear.setChecked(checked);
        // 实体数据的修改
        getItem(position).isSelected = checked;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 实体数据的修改
        int position = (Integer) buttonView.getTag();
        SpeedupInfo info = getItem(position);
        info.isSelected = isChecked;
    }

}
