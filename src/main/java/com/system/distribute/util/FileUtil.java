package com.system.distribute.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * 文件操作类
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 13-3-12
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {

    

    public static final int BUFFER = 1024;


   


    public static int getEncodingByteLen(String sub) {
        int zhLen = (sub.getBytes().length - sub.length()) * 2;
        int enLen = sub.length() * 2 - sub.getBytes().length;
        return zhLen + enLen;
    }

    // 限制名字的长度
    public static String subStr(String str, int limit) {
        String result = str.substring(0, 17);
        int subLen = 17;
        for (int i = 0; i < limit; i++) {
            if (limit < getEncodingByteLen(str.substring(0, (subLen + i) > str
                    .length() ? str.length() : (subLen)))) {
                result = str.substring(0, subLen + i - 1);
                break;
            }
            if ((subLen + i) > str.length()) {
                result = str.substring(0, str.length() - 1);
                break;
            }
        }
        return result;
    }

    /**
     * 生成随机的文件名 将原始文件名去掉,改为一个UUID的文件名,后缀名以原文件名的后缀为准
     *
     * @param fileName
     *            原始文件名+后缀
     * @return
     */
    public static String generateUUIDFileName(String fileName) {

        UUID uuid = UUID.randomUUID();
        String str = fileName;
        System.out.println(str);
        str = uuid.toString() + "." + str.substring(str.lastIndexOf(".") + 1);
        return str;
    }

    /**
     * 功 能: 拷贝文件(只能拷贝文件)
     *
     * @param strSourceFileName
     *            指定的文件全路径名
     * @param strDestDir
     *            拷贝到指定的文件夹
     * @return 如果成功true;否则false
     */
    public static boolean copyTo(String strSourceFileName, String strDestDir) {
        File fileSource = new File(strSourceFileName);
        File fileDest = new File(strDestDir);

        // 如果源文件不存或源文件是文件夹
        if (!fileSource.exists() || !fileSource.isFile()) {
           
            return false;
        }

        // 如果目标文件夹不存在
        if (!fileDest.isDirectory() || !fileDest.exists()) {
            if (!fileDest.mkdirs()) {
                
                return false;
            }
        }

        try {
            String strAbsFilename = strDestDir + File.separator + fileSource.getName();

            FileInputStream fileInput = new FileInputStream(strSourceFileName);
            FileOutputStream fileOutput = new FileOutputStream(strAbsFilename);

            

            int count = -1;

            long nWriteSize = 0;
            long nFileSize = fileSource.length();

            byte[] data = new byte[BUFFER];

            while (-1 != (count = fileInput.read(data, 0, BUFFER))) {

                fileOutput.write(data, 0, count);

                nWriteSize += count;

                long size = (nWriteSize * 100) / nFileSize;
                long t = nWriteSize;

                String msg = null;

                if (size <= 100 && size >= 0) {
                    msg = "\r拷贝文件进度:   " + size + "%   \t" + "\t   已拷贝:   " + t;
                   
                } else if (size > 100) {
                    msg = "\r拷贝文件进度:   " + 100 + "%   \t" + "\t   已拷贝:   " + t;
                   
                }

            }

            fileInput.close();
            fileOutput.close();

            
            return true;

        } catch (Exception e) {
           
            return false;
        }
    }

    /**
     * 删除指定的文件
     *
     * @param strFileName
     *            指定绝对路径的文件名
     * @return 如果删除成功true否则false
     */
    public static boolean delete(String strFileName) {
        File fileDelete = new File(strFileName);

        if (!fileDelete.exists() || !fileDelete.isFile()) {
          
            return false;
        }

       
        return fileDelete.delete();
    }

    /**
     * 移动文件(只能移动文件)
     *
     * @param strSourceFileName
     *            是指定的文件全路径名
     * @param strDestDir
     *            移动到指定的文件夹中
     * @return 如果成功true; 否则false
     */
    public static boolean moveFile(String strSourceFileName, String strDestDir) {
        if (copyTo(strSourceFileName, strDestDir))
            return delete(strSourceFileName);
        else
            return false;
    }

    /**
     * 创建文件夹
     *
     * @param strDir
     *            要创建的文件夹名称
     * @return 如果成功true;否则false
     */
    public static boolean makedir(String strDir) {
        File fileNew = new File(strDir);

        if (!fileNew.exists()) {
           
            return fileNew.mkdirs();
        } else {
           
            return true;
        }
    }

    /**
     * 删除文件夹huowenjian
     *
     * @param strDir
     *            要删除的文件夹名称
     * @return 如果成功true;否则false
     */
    public static void rmFile(String strDir) {
        File rmDir = new File(strDir);
        if (rmDir.isDirectory() && rmDir.exists()) {
            String[] fileList = rmDir.list();

            for (int i = 0; i < fileList.length; i++) {
                String subFile = strDir + File.separator + fileList[i];
                File tmp = new File(subFile);
                if (tmp.isFile())
                    tmp.delete();
                else if (tmp.isDirectory())
                    rmFile(subFile);
                else {
                    
                }
            }
            rmDir.delete();
        } else{
        	delete(strDir);
           	
        }
        
    }

    //转换文件大小
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
    
    public static String getFormatSize(double size) {  
        double kiloByte = size/1024;  
        if(kiloByte < 1) {  
            return size + "Byte(s)";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double teraBytes = gigaByte/1024;  
        if(teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    }  
}
