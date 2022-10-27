package org.sparkera.nft.utils;

import java.io.*;
import java.util.StringTokenizer;

public class TranslateUtil {
    public static String handle(String eString) throws IOException {
        StringTokenizer st = new StringTokenizer(eString, ",!' '.;");
        while (st.hasMoreElements()) {
            String sText;
            sText = st.nextElement().toString();
            String encoding = "GBK";
            File file = new File("F:\\java\\fanyi.txt");
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.toString().equals(sText)) {
                        String msg=bufferedReader.readLine();
                        return msg;
                    }
                }
                read.close();
            }
        }
        return null;
    }
}