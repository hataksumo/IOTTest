package ps.zhifa.test.multiIotDevice.common;

import java.io.*;

public class FileUtils
{
    static String rootPath = null;


    public static byte[] readAll(String v_fileName)
    {
        if(rootPath == null)
        {
            rootPath = FileUtils.class.getResource("/").getFile();
        }
        File theFile = new File(rootPath+v_fileName);
        Long fileLen = theFile.length();
        byte[] data = new byte[fileLen.intValue()];
        try
        {
            FileInputStream fis = new FileInputStream(theFile);
            fis.read(data);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        return data;
    }

    public static String readAllToString(String v_fileName,String v_sEncode)
    {
        byte[] data = readAll(v_fileName);
        try{
            return new String(data,v_sEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readAllToString(String v_fileName)
    {
        return readAllToString(v_fileName,"utf-8");
    }
}
