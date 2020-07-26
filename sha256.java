package wanghw;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * �����ļ�sha256ֵ
 *sha256ֵ���ڽ���ȥ�� 
 * 
 */
public class sha256 {
    public static void main(String[] args) {
    	String filePath="C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.txt";
        File file = new File(filePath);
        System.out.println("�ļ� " + file + " SHA256ֵ��:" + getFileSHA1(file));
    }
    
    private static String getFileSHA1(File file) {
        String str = "";
        try {
            str = getHash(file, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    private static String getHash(File file, String hashType) throws Exception {
        InputStream fis = new FileInputStream(file);
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }
    private static String toHexString(byte b[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(Integer.toHexString(aB & 0xFF));
        }
        return sb.toString();
    }
}
