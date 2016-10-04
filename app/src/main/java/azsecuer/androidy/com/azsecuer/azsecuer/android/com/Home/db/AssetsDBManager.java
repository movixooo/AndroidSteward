package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db;
import android.content.Context;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * @author: leejohngoodgame
 * @date: 2016/8/11 11:19
 * @email:18328541378@163.com
 */
public class AssetsDBManager {
    // 提供一个COPY方法
    public static  void copyDBFileToDB(Context context, String assetsFilePath, File toFile)throws IOException{
        //获取输入流
        InputStream inputStream = context.getResources().getAssets().open(assetsFilePath);
        byte [] buff = new byte[1024];// 创建缓冲区
        int len = 0;
        // 缓冲输入流
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        // 文件流
        FileOutputStream fileOutputStream = new FileOutputStream(toFile);
        //缓冲输出流
        BufferedOutputStream bufferedOutputStream =new BufferedOutputStream(fileOutputStream);
        // 进行拷贝
        while((len = bufferedInputStream.read(buff))!=-1){
            bufferedOutputStream.write(buff,0,len);// 有多少写多少
        }
        bufferedOutputStream.flush();// 要记得flush() 刷新
        bufferedOutputStream.close();//关闭流
        bufferedInputStream.close();
        fileOutputStream.close();
        inputStream.close();
    }
}
