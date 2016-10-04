package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
/**
 * Created by Administrator on 2016.8.16.
 */
public class MemoryManager {
    // ------------------------------------------------------------------------------------------------
    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }


    /** 获取有效的可用的运行内存 byte */
    public static long getAvailRamMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        // 获取手机运行内存大小
        am.getMemoryInfo(outInfo);
        long avail = outInfo.availMem; // byte
        return avail;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static long getTotalRamMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        // 获取手机运行内存大小
        am.getMemoryInfo(outInfo);
        long avail = outInfo.totalMem;
        return avail;
    }
    /** 获取所有运行内存信息 byte */
    public static long getTotalRamMemory() {
        try {
            FileReader fr = new FileReader("/proc/meminfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            // 根据给定的正则表达式的匹配来拆分此字符串
            // \\s表示空白符(空格,回车,换行等), +号表示一个或多个
            String[] array = text.split("\\s+");
            return Long.valueOf(array[1]) * 1024; // 原为kb, 转为b
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    // -------------------------------------------------------------------------
    // 目的：获取手机存储空间大小ROM
    // 获取手机自身的内存大小 system + cache + data + 等等组成
    // API 1: Environment
    // Environment.getDownloadCacheDirectory();
    // Environment.getRootDirectory();
    // Environment.getDataDirectory(); (里面包含了内置存储大小,也是最重要的位置)
    // 获取这些位置的大小:
    // 1 知道了文件目录,去递归计算(有的系统位置无权限,不考虑)
    // 2 API : StatFS (取文件系统信息)
    // StatFS fs = new StatFS(path);
    // fs.getBlockCount() * fs.getBlockSize()
    // (api:18)fs.getTotalBytes()

    /** 当前项目内未使用 */
    @Deprecated
    public static String getInRomPath() {
        if (Environment.getExternalStorageState() != Environment.MEDIA_UNMOUNTED) {
            File file = Environment.getExternalStorageDirectory();
            return file.getPath();
        }
        return null;
    }
    /** 当前项目内未使用 */
    @Deprecated
    public static String getOutRomPath() {
        Map<String, String> map = System.getenv();
        if (map.containsKey("SECONDARY_STORAGE")) {
            String path = map.get("SECONDARY_STORAGE");
            String paths[] = path.split(":");
            return paths[0];
        }
        return null;
    }
    /**
     * 当前项目内未使用 system + cache + data 等组成
     */
    @TargetApi(18)
    @Deprecated
    public static long getTotalInRom() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long dataFileSize = stat.getTotalBytes();

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        long cacheFileSize = stat.getTotalBytes();

        path = Environment.getRootDirectory();
        stat = new StatFs(path.getPath());
        long rootFileSize = stat.getBlockSizeLong() * stat.getBlockCountLong();
        // long rootFileSize = stat.getTotalBytes();

        return dataFileSize + cacheFileSize + rootFileSize;
    }

    // 目的：获取手机存储空间大小ROM
    // 获取手机外置存储卡的内存大小, 没有API可直接得到,外置路径无法完全统一(厂商不一,数量不一)
    // 1. 获取读取环境参数值 System.getenv(); 从而得到外置路径
    // 2. 反射调用StorageManager类隐藏方法getVolumePaths(); 从而得到本机所有存储路径

    /** 获取手机存储空间位置 */
    @TargetApi(18)
    public static String getInRomPath(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            // 通过反射拿到此类中指定方法
            Method method = sm.getClass().getMethod("getVolumePaths", MemoryManager.class);
            // 通过反射执行此方法
            String[] paths = (String[]) method.invoke(sm, MemoryManager.class);
            // 第一位置放的是自身存储
            return paths[0];
        } catch (Exception e) {
            return null;
        }
    }

    /** 获取手机存储空间全部大小 */
    @TargetApi(18)
    public static long getTotalInRom(Context context) {
        String inRomPath = getInRomPath(context);
        if (inRomPath != null) {
            StatFs fs = new StatFs(inRomPath);
            return fs.getTotalBytes();
        }
        return -1;
    }

    /** 获取手机存储空间有效空闲大小 */
    @TargetApi(18)
    public static long getAvailableInRom(Context context) {
        String inRomPath = getInRomPath(context);
        if (inRomPath != null) {
            StatFs fs = new StatFs(inRomPath);
            return fs.getAvailableBytes();
        }
        return -1;
    }

    /** 获取手机外置存储空间位置 */
    @TargetApi(18)
    public static String getOutRomPath(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            // 通过反射拿到此类中指定方法
            Method method = sm.getClass().getMethod("getVolumePaths", MemoryManager.class);
            // 通过反射执行此方法
            String[] paths = (String[]) method.invoke(sm, MemoryManager.class);
            // 第二位置放外置的存储
            return paths[1];
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /** 获取手机外置存储空间有效空闲大小 */
    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }
}
