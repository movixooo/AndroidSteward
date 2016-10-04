package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import java.util.ArrayList;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.MobileChildInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.PublicUtils;
/**
 * Created by Administrator on 2016.8.16.
 */
public class MobileManager {
    private Context context;
    private WifiManager wifiManager;
    private ConnectivityManager connManager;
    private PackageManager packageManager;

    public MobileManager(Context context) {
        this.context = context;
        packageManager = context.getPackageManager();
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    // 是否同意指定权限(Manifest.permission.ACCESS_NETWORK_STATE)
    public boolean isGranterPermission(String permission) {
        int result = packageManager.checkPermission(permission, context.getPackageName());
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    // 内存信息 -------------------------------------------------
    public ArrayList<MobileChildInfo> getMemoryMessage(Context context) {
        ArrayList<MobileChildInfo> childs = new ArrayList<MobileChildInfo>();
        String maxRam = PublicUtils.formatSize(MemoryManager.getTotalRamMemory());
        String freeRam = PublicUtils.formatSize(MemoryManager.getAvailRamMemory(context));
        String inRom = PublicUtils.formatSize(MemoryManager.getTotalInRom(context));
        String outRom = PublicUtils.formatSize(MemoryManager.getTotalExternalMemorySize());
        childs.add(new MobileChildInfo("最大RAM:", maxRam));
        childs.add(new MobileChildInfo("空闲RAM:", freeRam));
        childs.add(new MobileChildInfo("内置空间:",outRom));
        childs.add(new MobileChildInfo("外置空间:", inRom));
        return childs;
    }

    // 设备信息 -------------------------------------------------
    public ArrayList<MobileChildInfo> getPhoneMessage() {
        ArrayList<MobileChildInfo> childs = new ArrayList<MobileChildInfo>();
        childs.add(new MobileChildInfo("手机品牌:", getPhoneName1()));
        childs.add(new MobileChildInfo("手机制造商:", getPhoneName2()));
        childs.add(new MobileChildInfo("手机型号:", getPhoneModelName()));
        childs.add(new MobileChildInfo("手机分辨率:", getPhoneResolution()));
        return childs;
    }
    /** 设备品牌 */
    public String getPhoneName1() {
        return Build.BRAND.toUpperCase();
    }

    /** 设备制造商 */
    public String getPhoneName2() {
        return Build.MANUFACTURER;
    }

    /** 设备型号名称(xt910) */
    public String getPhoneModelName() {
        // 带国家用 PRODUCT
        return Build.MODEL.toUpperCase();
    }

    /** 获取手机分辨率 */
    public String getPhoneResolution() {
        String resolution = "";
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        resolution = metrics.widthPixels + "*" + metrics.heightPixels;
        return resolution;
    }

    // 系统信息 -------------------------------------------------
    public ArrayList<MobileChildInfo> getSystemMessage() {
        ArrayList<MobileChildInfo> childs = new ArrayList<MobileChildInfo>();
        childs.add(new MobileChildInfo("系统版本:", getSystemVersion()));
        childs.add(new MobileChildInfo("基带版本:", getSystemBasebandVersion()));
        return childs;
    }

    /** 设备系统版本号 (4.1.2?) */
    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /** 设备系统基带版本 */
    public String getSystemBasebandVersion() {
        return Build.RADIO;
    }

    // 网络信息 -------------------------------------------------
    public ArrayList<MobileChildInfo> getWIFIMessage() {
        ArrayList<MobileChildInfo> childs = new ArrayList<MobileChildInfo>();
        childs.add(new MobileChildInfo("网络类型:", getNetworkType()));
        childs.add(new MobileChildInfo("WIFI名称:", getWifiName()));
        childs.add(new MobileChildInfo("WIFI-IP:", getWifiIP()));
        childs.add(new MobileChildInfo("WIFI速度:", getWifiSpeed()));
        return childs;
    }

    /** 设备网络连接类型 (OFFLINE ? WIFI ? MOBILE) permission.ACCESS_NETWORK_STATE */
    public String getNetworkType() {
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return "OFFLINE";
        }
        return info.getTypeName();
    }

    /** 设备Wifi名称 */
    public String getWifiName() {
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getSSID() + "";
    }

    /** 设备Wifi的IP */
    public String getWifiIP() {
        WifiInfo info = wifiManager.getConnectionInfo();
        long ip = info.getIpAddress();
        return longToIP(ip);
    }

    /** 设备Wifi的速度 */
    public String getWifiSpeed() {
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getLinkSpeed() + "";
    }

    private String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        sb.append(".");
        // 将高1位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        return sb.toString();
    }

    // 相机信息 -------------------------------------------------
    public ArrayList<MobileChildInfo> getCameraMessage() {
        ArrayList<MobileChildInfo> childs = new ArrayList<MobileChildInfo>();
        childs.add(new MobileChildInfo("相机像素:", getCameraResolution()));
        childs.add(new MobileChildInfo("闪光灯状态:", getCameraFlashMode()));
        return childs;
    }

    /** 获取相机像素 */
    public String getCameraResolution() {
        String cameraResolution = "";
        try {
            // Camera camera = Camera.open();
            // Camera.Parameters parameters = camera.getParameters();
            // List<Size> sizes = parameters.getSupportedPictureSizes();
            // Size size = null;
            // for (Size s : sizes) {
            // if (size == null) {
            // size = s;
            // } else if (size.height * s.width < s.height * s.width) {
            // size = s;
            // }
            // }
            // cameraResolution = (size.width * size.height) / 10000 + "万像素";
            // camera.release();
        } catch (Exception e) {
            return "访问出错";
        }
        return cameraResolution;
    }

    /** 获取闪光灯状态 */
    public String getCameraFlashMode() {
        String flashMode = "";
        try {
            // Camera camera = Camera.open();
            // Camera.Parameters parameters = camera.getParameters();
            // flashMode = parameters.getFlashMode();
            // if (flashMode.equals(Parameters.FLASH_MODE_AUTO)) {
            // flashMode = "自动模式";
            // }
            // if (flashMode.equals(Parameters.FLASH_MODE_OFF)) {
            // flashMode = "关闭模式";
            // }
            // if (flashMode.equals(Parameters.FLASH_MODE_ON)) {
            // flashMode = "打开模式";
            // }
            // camera.release();
            // flashMode = "其它模式";
        } catch (Exception e) {
            flashMode = "访问出错";
            return flashMode;
        }
        return flashMode;
    }
}
