package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016.8.16.
 */
public class PublicUtils {
    private static DecimalFormat df = new DecimalFormat("#.00");
    private static StringBuilder mstrbuf = new StringBuilder();
    private static final String STR_EMPTY = "";

    /**
     * 获取指定文件大小的字符串单位B? KB? MB? GB?
     *
     * @param filesize
     *            文件大小(byte)
     * @return 文件大小的字符串单位B? KB? MB? GB?
     */
    public static String formatSize(long filesize) {
        if (filesize < 0) {
            return "0 B";
        }
        mstrbuf.replace(0, mstrbuf.length(), STR_EMPTY);
        if (filesize < 1024) {
            mstrbuf.append(filesize);
            mstrbuf.append(" B");
        } else if (filesize < 1048576) {
            mstrbuf.append(df.format((double) filesize / 1024));
            mstrbuf.append(" K");
        } else if (filesize < 1073741824) {
            mstrbuf.append(df.format((double) filesize / 1048576));
            mstrbuf.append(" M");
        } else {
            mstrbuf.append(df.format((double) filesize / 1073741824));
            mstrbuf.append(" G");
        }
        return mstrbuf.toString();
    }

    private static Date date = new Date(0);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(long time) {
        date.setTime(time);
        return format.format(date);
    }
}
