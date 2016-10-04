package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity;
/**
 * @author: leejohngoodgame
 * @date: 2016/8/11 14:21
 * @email:18328541378@163.com
 */
public class TelNumberInfo {
    private String name;
    private String number;
    public TelNumberInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}
