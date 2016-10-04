package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.mgr;
import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.FileInfo;
/**
 * Created by Administrator on 2016.8.25.
 */
public class FileSearchManager {
    /** 所有文件分类集合 */
    private HashMap<String, ArrayList<FileInfo>> fileInfos;
    /** 所有文件大小集合 */
    private HashMap<String, Long> fileSizes;
    /** 全部文件大小总合 (注意数据的重置) */
    private long totalSize;
    /** 是否终止搜索 (注意数据的重置) */
    private boolean isStopSearch = false;
    /** 文件搜索监听接口对象(默认已实现) */
    private OnFileSearchListener listener = new OnFileSearchListener() {

        @Override
        public void onSearchStart(int searchLocationRom) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSearching(String fileType, long totalSize) {
        }

        @Override
        public void onSearchEnd(boolean isExceptionEnd, int searchLocationRom) {
            // TODO Auto-generated method stub
        }
    };

    private FileSearchManager() {
        initDataList();
    }

    private static FileSearchManager searchManager = new FileSearchManager();

    public static FileSearchManager getInstance(boolean isCleardata) {
        if (isCleardata) {
            searchManager.initDataList();
        }
        return searchManager;
    }

    private void initDataList() {
        totalSize = 0;
        isStopSearch = false;
        fileInfos = new HashMap<String, ArrayList<FileInfo>>();
        fileSizes = new HashMap<String, Long>();
        fileInfos.clear();
        fileInfos.put(FileSearchTypeEvent.TYPE_OTHER, new ArrayList<FileInfo>());
        fileInfos.put(FileSearchTypeEvent.TYPE_APK, new ArrayList<FileInfo>());
        fileInfos.put(FileSearchTypeEvent.TYPE_AUDIO, new ArrayList<FileInfo>());
        fileInfos.put(FileSearchTypeEvent.TYPE_IMAGE, new ArrayList<FileInfo>());
        fileInfos.put(FileSearchTypeEvent.TYPE_TXT, new ArrayList<FileInfo>());
        fileInfos.put(FileSearchTypeEvent.TYPE_VIDEO, new ArrayList<FileInfo>());
        fileInfos.put(FileSearchTypeEvent.TYPE_ZIP, new ArrayList<FileInfo>());
        fileSizes.clear();
        fileSizes.put(FileSearchTypeEvent.TYPE_OTHER, 0L);
        fileSizes.put(FileSearchTypeEvent.TYPE_APK, 0L);
        fileSizes.put(FileSearchTypeEvent.TYPE_AUDIO, 0L);
        fileSizes.put(FileSearchTypeEvent.TYPE_IMAGE, 0L);
        fileSizes.put(FileSearchTypeEvent.TYPE_TXT, 0L);
        fileSizes.put(FileSearchTypeEvent.TYPE_VIDEO, 0L);
        fileSizes.put(FileSearchTypeEvent.TYPE_ZIP, 0L);
    }

    /** 获取到所有文件分类集合 (一般应在搜索结束后来使用) */
    public HashMap<String, ArrayList<FileInfo>> getFileInfos() {
        return fileInfos;
    }

    public HashMap<String, Long> getFileSizes() {
        return fileSizes;
    }

    /** 设置中止搜索 */
    public void setStopSearch(boolean isClearData) {
        isStopSearch = true;
        if (isClearData) {
            initDataList();
        }
    }

    /** 设置文件搜索监听(一般会在文件搜索前调用) */
    public void setOnFileSearchListener(OnFileSearchListener listener) {
        this.listener = listener;
    }

    /** 搜索内置空间所有文件 */
    public void startSearchFromInRom(Context context) {
        System.gc();
        String inRomPath = MemoryManager.getInRomPath();
        if (inRomPath != null) {
            File inRomFile = new File(inRomPath);
            try {
                // 回调接口, 开始搜索
                listener.onSearchStart(0);
                // 文件搜索业务逻辑-递归操作
                fileSearch(inRomFile, 0);
                // 回调接口, 结束搜索
                listener.onSearchEnd(false, 0);
            } catch (Exception e) {
                listener.onSearchEnd(true, 0);
            }
        } else {
            listener.onSearchEnd(true, 0);
        }
    }

    /** 搜索外置空间所有文件 */
    public void startSearchFromOutRom(Context context) {
        System.gc();
        String outRomPath = MemoryManager.getOutRomPath();
        if (outRomPath != null) {
            File outRomFile = new File(outRomPath);
            try {
                listener.onSearchStart(1);
                fileSearch(outRomFile, 1);
                listener.onSearchEnd(false, 1);
            } catch (Exception e) {
                listener.onSearchEnd(true, 1);
            }
        } else {
            listener.onSearchEnd(true, 1);
        }
    }

    /**
     * 文件搜索(从指定目录中)
     *
     * @param targetFileDir
     *            搜索目标目录
     * @param romType
     *            目录类型0:内置空间 1:外置空间
     */
    private void fileSearch(File targetFileDir, int romType) throws Exception {
        // 终止搜索
        if (isStopSearch) {
            throw new Exception("终止搜索");
        }
        // 不能读的文件不操作
        if (targetFileDir == null || !targetFileDir.canRead()) {
            return;
        }
        // 是文件
        if (targetFileDir.isFile()) {
            addFileToList(targetFileDir, romType); // 将此文件添加到文件集合
        }
        // 是目录
        if (targetFileDir.isDirectory()) {
            File[] files = targetFileDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileSearch(file, romType); // 　递归文件搜索
                }
            }
        }
    }

    public void removeFileFromList(){}

    private void addFileToList(File file, int romType) {
        String[] iconTypes = FileSearchTypeEvent.getFileIconAndType(file);
        String fileType = iconTypes[2];// 文件类型(图像?音频?)
        String iconName = iconTypes[1];// 此文件对应所用图像名称
        String openType = FileSearchTypeEvent.getMIMEType(file);// 此文件打开类型
        FileInfo fileInfo = new FileInfo(file, romType, fileType, iconName, openType);
        // 将此文件添加到对应分类集合中
        fileInfos.get(fileType).add(fileInfo);
        // 将此类型文件总大小添加到对应集合
        long size = fileSizes.get(fileType) + file.length();
        fileSizes.put(fileType, size);
        // 回调接口, 搜索到文件
        totalSize = totalSize + file.length();
        listener.onSearching(fileType, totalSize);
    }

    public interface OnFileSearchListener {
        /**
         * 当搜索开始时将来调用的方法
         *
         * @param searchLocationRom
         *            目标文件(表明是开始搜索谁了)0:内置 1:外置
         */
        void onSearchStart(int searchLocationRom);

        /**
         * 在搜索过程中将来回调的方法(每搜索到一个回调一次)
         *
         * @param fileType
         *            此文件的类型
         * @param totalSize
         *            所有文件总大小
         */
        void onSearching(String fileType, long totalSize);

        /**
         * 当搜索结束后将来调用的方法
         *
         * @param isExceptionEnd
         *            是否为异常结束
         * @param searchLocationRom
         *            目标文件(表明是搜索谁结束了)0:内置 1:外置
         */
        void onSearchEnd(boolean isExceptionEnd, int searchLocationRom);
    }
}

