package wanghw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
 
public class SJJM {
    /**   
     * ʹ��AES���ļ����м��ܺͽ���   
     *    
     */    
    private static String type = "AES";     
    
    /**   
     * ���ļ�srcFile���ܺ�洢ΪdestFile   
     * @param srcFile     ����ǰ���ļ�   
     * @param destFile    ���ܺ���ļ�   
     * @param privateKey  ��Կ   
     * @throws GeneralSecurityException   
     * @throws IOException   
     */    
    public void encrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {     
        Key key = getKey(privateKey);     
        Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");     
        cipher.init(Cipher.ENCRYPT_MODE, key);     
    
        FileInputStream fis = null;     
        FileOutputStream fos = null;     
        try {     
            fis = new FileInputStream(srcFile);     
            fos = new FileOutputStream(mkdirFiles(destFile));     
    
            crypt(fis, fos, cipher);     
        } catch (FileNotFoundException e) {     
            e.printStackTrace();     
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            if (fis != null) {     
                fis.close();     
            }     
            if (fos != null) {     
                fos.close();     
            }     
        }     
    }     
    
    /**   
     * ���ļ�srcFile���ܺ�洢ΪdestFile   
     * @param srcFile     ����ǰ���ļ�   
     * @param destFile    ���ܺ���ļ�   
     * @param privateKey  ��Կ   
     * @throws GeneralSecurityException   
     * @throws IOException   
     */    
    public void decrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {     
        Key key = getKey(privateKey);     
        Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");     
        cipher.init(Cipher.DECRYPT_MODE, key);     
    
        FileInputStream fis = null;     
        FileOutputStream fos = null;     
        try {     
            fis = new FileInputStream(srcFile);     
            fos = new FileOutputStream(mkdirFiles(destFile));     
    
            crypt(fis, fos, cipher);     
        } catch (FileNotFoundException e) {     
            e.printStackTrace();     
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            if (fis != null) {     
                fis.close();     
            }     
            if (fos != null) {     
                fos.close();     
            }     
        }     
    }     
    
    /**   
     * ����filePath������Ӧ��Ŀ¼   
     * @param filePath      Ҫ�������ļ�·��   
     * @return  file        �ļ�   
     * @throws IOException   
     */    
    private File mkdirFiles(String filePath) throws IOException {     
        File file = new File(filePath);     
        if (!file.getParentFile().exists()) {     
            file.getParentFile().mkdirs();     
        }     
        file.createNewFile();     
    
        return file;     
    }     
    
    /**   
     * ����ָ���ַ�������Կ   
     * @param secret        Ҫ������Կ���ַ���   
     * @return secretKey    ���ɺ����Կ   
     * @throws GeneralSecurityException   
     */    
    private static Key getKey(String secret) throws GeneralSecurityException {     
        KeyGenerator kgen = KeyGenerator.getInstance(type);     
        kgen.init(128, new SecureRandom(secret.getBytes()));     
        SecretKey secretKey = kgen.generateKey();     
        return secretKey;     
    }     
    
    /**   
     * ���ܽ�����   
     * @param in        ���ܽ���ǰ����   
     * @param out       ���ܽ��ܺ����   
     * @param cipher    ���ܽ���   
     * @throws IOException   
     * @throws GeneralSecurityException   
     */    
    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {     
        int blockSize = cipher.getBlockSize() * 1000;     
        int outputSize = cipher.getOutputSize(blockSize);     
    
        byte[] inBytes = new byte[blockSize];     
        byte[] outBytes = new byte[outputSize];     
    
        int inLength = 0;     
        boolean more = true;     
        while (more) {     
            inLength = in.read(inBytes);     
            if (inLength == blockSize) {     
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);     
                out.write(outBytes, 0, outLength);     
            } else {     
                more = false;     
            }     
        }     
        if (inLength > 0)     
            outBytes = cipher.doFinal(inBytes, 0, inLength);     
        else    
            outBytes = cipher.doFinal();     
        out.write(outBytes);     
    }     
    
    public static String genRandomNum(int pwd_len){
    	  //35����Ϊ�����Ǵ�0��ʼ�ģ�26����ĸ+10�� ����
    	  final int maxNum = 36;
    	  int i; //���ɵ������
    	  int count = 0; //���ɵ�����ĳ���
    	  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
    	    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
    	    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    	  
    	  StringBuffer pwd = new StringBuffer("");
    	  Random r = new Random();
    	  while(count < pwd_len){
    	   //�����������ȡ����ֵ����ֹ ���ɸ�����
    	   
    	   i = Math.abs(r.nextInt(maxNum));  //���ɵ������Ϊ36-1
    	   
    	   if (i >= 0 && i < str.length) {
    	    pwd.append(str[i]);
    	    count ++;
    	   }
    	  }
    	  
    	  return pwd.toString();
    	 }
    	
    public static void main(String[] args) throws GeneralSecurityException, IOException {
    	

    	//String privateKey = "abcdefghigklmnopqrstuvwxyz123454";
    //	String filePath = "C:\\Users\\lenovo\\Desktop\\test.jiamizhihoude";
    	//String privateKey = md5.md5HashCode32(filePath); 
    	
    	
    	//String privateKey = md5.md5HashCode32("C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.txt"); 
    	//System.out.println("��ȡ��ϣֵ�ɹ�..");
    	String privateKey =genRandomNum(32);
    	System.out.println("���������Կ�ɹ�.."+privateKey);
    	SJJM util = new SJJM();
    	
    	System.out.println("��ʼ..");
    	//����    	
    	util.encrypt("C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.txt", "C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.jiamizhihoude2", privateKey);
    	System.out.println("���ܳɹ�..");
    	util.decrypt("C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.jiamizhihoude2", "C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.jiemizhihoude2", privateKey);
    	System.out.println("���ܳɹ�..");
    	//util.encrypt("filePath", "encfilePath", privateKey);
    //	util.decrypt("encfilePath", "decfilePath", privateKey);
    	System.out.println("����..");
		
	}
}
