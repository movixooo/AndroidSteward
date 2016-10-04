package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity;
import java.io.Serializable;
/**
 * @author: leejohngoodgame
 * @date: 2016/8/11 14:20
 * @email:18328541378@163.com
 */
public class TelTypeInfo implements Serializable{
    private String typeName;
    private int idx;
    public TelTypeInfo(String typeName, int idx) {
        this.typeName = typeName;
        this.idx = idx;
    }
    public int getIdx() {
        return idx;
    }
    public void setIdx(int idx) {
        this.idx = idx;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    @Override
    public String toString() {
        return "TelTypeInfo{" +
                "typeName='" + typeName + '\'' +
                ", idx=" + idx +
                '}';
    }
}
