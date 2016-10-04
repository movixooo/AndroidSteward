package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util;

import java.io.File;

/**
 * Created by Administrator on 2016.8.18.
 */
public class FileUtils {
    public static long getFileSize(File file){
        long size = 0 ;
        if (file != null && file.exists()) {//判定当前对象是否有效
            if (file.isFile()) {// 判定是否是文件
                return size += file.length();
            }
            if (file.isDirectory()) {//判断是否是文件夹
                File[] files = file.listFiles();//获取这个文件夹里面的所有目录 文件
                long sizeD = 0;
                if(files != null) { // 保证不会出现NullPointerException
                    for (File file2 : files) {// 遍历这个文件数组
                        sizeD += getFileSize(file2);// 调用自己
                    }
                }
                return sizeD;
            }
        }
        return size;
    }
    public static void deleteFile(File file){
        if(file!=null && file.exists()){
            if(file.isFile()){// 是一个文件
                file.delete(); // 删除
            }
            if(file.isDirectory()){// 是一个文件夹
                File[] files = file.listFiles(); // 当前文件夹内的所有文件 包括文件夹
                if(files!= null){
                    for (File temp:
                            files) {
                        deleteFile(temp);// 遍历出来以后调用后方法本身
                    }
                }
            }
        }
    }
}