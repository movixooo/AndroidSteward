package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.SpeedupInfo;
/**
 * Created by Administrator on 2016.8.21.
 */
public class SpeedupManager {
    private Context context;
    private PackageManager pm;
    private ActivityManager am;
    private HashMap<Integer, List<SpeedupInfo>> speedupInfos = null;
    public static final int CLASSIFY_ALL = 0; // 分类：全部进程
    public static final int CLASSIFY_SYS = 1; // 分类：系统进程
    public static final int CLASSIFY_USER = 2; // 分类：用户进程
    private static SpeedupManager manager = null;
    public static SpeedupManager getInstance(Context context) {
        if (manager == null) {
            manager = new SpeedupManager(context);
        }
        return manager;
    }
    private SpeedupManager(Context context) {
        this.context = context;
        pm = context.getPackageManager();
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 将用来保存 正在运行的系统和用户进程两个集合
        speedupInfos = new HashMap<Integer, List<SpeedupInfo>>();
        // 将用来保存 正在运行的全部进程
        speedupInfos.put(CLASSIFY_ALL, new ArrayList<SpeedupInfo>());
        // 将用来保存 正在运行的系统进程
        speedupInfos.put(CLASSIFY_SYS, new ArrayList<SpeedupInfo>());
        // 将用来保存 正在运行的用户进程
        speedupInfos.put(CLASSIFY_USER, new ArrayList<SpeedupInfo>());
    }
    /** 获取所有正在运行的运程集合 */
    public List<SpeedupInfo> getRuningApp(int classify, boolean isReload) {
        if (isReload) {
            // 清空内部所有数据 list
            speedupInfos.get(CLASSIFY_ALL).clear();
            speedupInfos.get(CLASSIFY_SYS).clear();
            speedupInfos.get(CLASSIFY_USER).clear();
            // 加载正在运行的进程
            loadRuningApp();
        }
        // 返回加载到的指定分类运行进程集合
        return speedupInfos.get(classify);
    }
    public void kill(String packageName) {
        am.killBackgroundProcesses(packageName);
    }
    public void defSpeedup(){
        // 1 获取所有正在运行中的APP
        SpeedupManager speedupManager = SpeedupManager.getInstance(context);
        List<SpeedupInfo> speedupInfos = speedupManager.getRuningApp(0, true);
        // 2 清理这些APP后结束循环旋转的加速动画
        for (SpeedupInfo speedupInfo : speedupInfos) {
            speedupManager.kill(speedupInfo.processName);
        }
    }
    private void loadRuningApp() {
        // API： 加载所有正在运行的APP【不是所有都要】
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        // 分解，解析出我们所需的数据 【对应UI显示所需】
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            // 只有前台进程不要 (等级：前台进程 - 可视.. - 服务.. - 后台.. - 空进程)
            // IMPORTANCE_FOREGROUND [100]
            // IMPORTANCE_VISIBLE [200]
            // IMPORTANCE_SERVICE [300]
            // IMPORTANCE_BACKGROUND [400]
            // IMPORTANCE_EMPTY. [500]
            if (processInfo.importance > 100) {
                String processName = processInfo.processName;// 进程名称(取图标)
                // 不保存自己
                if (!processName.equals("com.androidy.anzysecure")) {
                    try {
                        ApplicationInfo appInfo = pm.getApplicationInfo(processName, 0);
                        Drawable icon = appInfo.loadIcon(pm); // 图标
                        String label = appInfo.loadLabel(pm).toString();// 标签
                        int pid = processInfo.pid;
                        Debug.MemoryInfo memoryInfo = am.getProcessMemoryInfo(new int[] { pid })[0];
                        long memory = memoryInfo.getTotalPrivateDirty() * 1024;// 占用内存
                        SpeedupInfo speedupInfo = null; // 实体【UI所需】
                        // 保存系统进程
                        if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                            speedupInfo = new SpeedupInfo(processName, icon, label, memory, true);
                            speedupInfos.get(CLASSIFY_SYS).add(speedupInfo);
                        }
                        // 保存用户进程
                        else {
                            speedupInfo = new SpeedupInfo(processName, icon, label, memory, false);
                            speedupInfos.get(CLASSIFY_USER).add(speedupInfo);
                        }
                        // 保存全部进程
                        speedupInfos.get(CLASSIFY_ALL).add(speedupInfo);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
