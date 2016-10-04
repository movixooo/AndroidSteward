package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016.8.21.
 */
public class SpeedupInfo {
    public String processName;
    public Drawable icon; // 图标
    public String label;// 标签
    public long memory;
    public boolean isSelected;
    public boolean isSystemApp;

    public SpeedupInfo(String processName,Drawable icon, String label, long memory, boolean isSystemApp) {
        super();
        this.processName = processName;
        this.icon = icon;
        this.label = label;
        this.memory = memory;
        this.isSystemApp = isSystemApp;
        this.isSelected = false;
    }
}
