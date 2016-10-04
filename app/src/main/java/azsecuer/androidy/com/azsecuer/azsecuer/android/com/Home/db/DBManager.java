package azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.db;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import azsecuer.androidy.com.azsecuer.R;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.RubbishInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.TelNumberInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.entity.TelTypeInfo;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.FileUtils;
import azsecuer.androidy.com.azsecuer.azsecuer.android.com.Home.util.PublicUtils;
/**
 * @author: leejohngoodgame
 * @date: 2016/8/11 11:18
 * @email:18328541378@163.com
 */
public class DBManager {
    // 创建
    public static File fileDB;
    /**
     * @param context
     */
    public static File createFile(Context context,String db) {//创建文件夹目录，和创建commonnum.db文件
        //  文件夹 路径
        String dbFileDir = "data/data/" + context.getPackageName() + "/db";
        File fileDir = new File(dbFileDir);
        boolean mkSuer = fileDir.mkdirs();// 创建路径
        fileDB = new File(fileDir,db);// 创建db文件
        Log.i("ql","createFile()");
        return fileDB;
    }
    public static boolean dbFileIsExists() {//判断文件是否创建
            if (fileDB.length() == 0 || !fileDB.exists()) {
                return true;
            }
        return false;
    }
    // 读取电话类型数据
    public static List<TelTypeInfo> readTableClassList() {
        List<TelTypeInfo> typeList = new ArrayList<>();
        // 关于查询
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(fileDB, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from classlist", null);//执行查询
        if (cursor.moveToFirst()) {// 游标已经来到了0
            TelTypeInfo typeInfo = null;
            do {
                typeInfo = new TelTypeInfo(cursor.getString(cursor.getColumnIndex("name"))
                        ,cursor.getInt(cursor.getColumnIndex("idx")));
                typeList.add(typeInfo);//装入集合当中
            } while (cursor.moveToNext());
        } else {
        }
        return typeList;
    }

    /**
     * @param idx 表id
     * @return List<TelNumberInfo> 返回数据
     */
    public static List<TelNumberInfo> readTabelNumber(Integer idx) {
        List<TelNumberInfo> numberList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(fileDB, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from table" + idx, null);//执行查询
        if (cursor.moveToFirst()) {
            TelNumberInfo telNumberInfo = null;
            do {
                telNumberInfo = new TelNumberInfo(cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")));
                numberList.add(telNumberInfo);
            } while (cursor.moveToNext());
        } else {
        }
        return numberList;
    }
    public static List<RubbishInfo> readClean(Context context) {//在数据库中查找手机清理的软件
        List<RubbishInfo> typeList = new ArrayList<>();
        // 关于查询
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(fileDB, null);
        Log.i("qwe",fileDB.length()+"");
        Cursor cursor = sqLiteDatabase.rawQuery("select * from softdetail",null);//执行查询
        if (cursor.moveToFirst()) {// 游标已经来到了0
            RubbishInfo typeInfo = null;
            do {
                String chineseName=cursor.getString(cursor.getColumnIndex("softChinesename"));
                String softEnglishname=cursor.getString(cursor.getColumnIndex("softEnglishname"));
                String apkName=cursor.getString(cursor.getColumnIndex("apkname"));
                String filepath=Environment.getExternalStorageDirectory()+cursor.getString(cursor.getColumnIndex("filepath"));
                File cacheFile= new File(filepath);
                long fileSize= FileUtils.getFileSize(cacheFile);
                String fileSizeStr = PublicUtils.formatSize(fileSize);
                Drawable drawable=null;
                try{
                    drawable=context.getPackageManager().getApplicationIcon(apkName);
                }catch(PackageManager.NameNotFoundException e){
                    drawable =context.getResources().getDrawable(R.drawable.ic_launcher);
                    e.printStackTrace();
                }
                if (fileSize!=0){
                    typeInfo =new RubbishInfo(chineseName,softEnglishname,filepath,apkName,false,drawable,fileSizeStr);
                    typeList.add(typeInfo);//装入集合当中
                }
            } while (cursor.moveToNext());
        } else {
        }
        return typeList;
    }
}