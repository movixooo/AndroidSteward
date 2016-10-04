package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016.8.17.
 */
public class RubbishInfo{
    public String softChineseName;
    public String softEnglishName;
    public String filePath;
    public String softPackageName;
    public boolean isChecked;
    public Drawable drawable;
    public String fileSizeStr;
    public RubbishInfo(String softChineseName, String softEnglishName, String filePath, String softPackageName, boolean isChecked, Drawable drawable, String fileSizeStr) {
        this.softChineseName = softChineseName;
        this.softEnglishName = softEnglishName;
        this.filePath = filePath;
        this.softPackageName = softPackageName;
        this.isChecked = isChecked;
        this.drawable = drawable;
        this.fileSizeStr = fileSizeStr;
    }

    @Override
    public String toString() {
        return "RubbishInfo{" +
                "softChineseName='" + softChineseName + '\'' +
                ", softEnglisgName='" + softEnglishName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", softPackageName='" + softPackageName + '\'' +
                ", isChecked=" + isChecked +
                ", drawable=" + drawable +
                ", fileSize=" + fileSizeStr +
                '}';
    }
}
