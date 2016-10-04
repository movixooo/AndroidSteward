package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity;
import android.graphics.drawable.Drawable;
/**
 * Created by Administrator on 2016.8.22.
 */
public class SoftwareBrowseInfo {
    public String lable;//标签，空间
    public String packagename;//包名
    public String version;//版本
    public Drawable drawable;//图片
    public boolean isSelected;
    public SoftwareBrowseInfo(){}
    public SoftwareBrowseInfo(String lable, String packagename, String version, Drawable drawable) {
        this.lable = lable;
        this.packagename = packagename;
        this.version = version;
        this.drawable = drawable;
        this.isSelected = false;
    }
}

