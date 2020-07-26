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
     * 使用AES对文件进行加密和解密   
     *    
     */    
    private static String type = "AES";     
    
    /**   
     * 把文件srcFile加密后存储为destFile   
     * @param srcFile     加密前的文件   
     * @param destFile    加密后的文件   
     * @param privateKey  密钥   
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
     * 把文件srcFile解密后存储为destFile   
     * @param srcFile     解密前的文件   
     * @param destFile    解密后的文件   
     * @param privateKey  密钥   
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
     * 根据filePath创建相应的目录   
     * @param filePath      要创建的文件路经   
     * @return  file        文件   
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
     * 生成指定字符串的密钥   
     * @param secret        要生成密钥的字符串   
     * @return secretKey    生成后的密钥   
     * @throws GeneralSecurityException   
     */    
    private static Key getKey(String secret) throws GeneralSecurityException {     
        KeyGenerator kgen = KeyGenerator.getInstance(type);     
        kgen.init(128, new SecureRandom(secret.getBytes()));     
        SecretKey secretKey = kgen.generateKey();     
        return secretKey;     
    }     
    
    /**   
     * 加密解密流   
     * @param in        加密解密前的流   
     * @param out       加密解密后的流   
     * @param cipher    加密解密   
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
    	  //35是因为数组是从0开始的，26个字母+10个 数字
    	  final int maxNum = 36;
    	  int i; //生成的随机数
    	  int count = 0; //生成的密码的长度
    	  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
    	    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
    	    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    	  
    	  StringBuffer pwd = new StringBuffer("");
    	  Random r = new Random();
    	  while(count < pwd_len){
    	   //生成随机数，取绝对值，防止 生成负数，
    	   
    	   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
    	   
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
    	//System.out.println("获取哈希值成功..");
    	String privateKey =genRandomNum(32);
    	System.out.println("生成随机密钥成功.."+privateKey);
    	SJJM util = new SJJM();
    	
    	System.out.println("开始..");
    	//加密    	
    	util.encrypt("C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.txt", "C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.jiamizhihoude2", privateKey);
    	System.out.println("加密成功..");
    	util.decrypt("C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.jiamizhihoude2", "C:\\\\Users\\\\lenovo\\\\Desktop\\\\test.jiemizhihoude2", privateKey);
    	System.out.println("解密成功..");
    	//util.encrypt("filePath", "encfilePath", privateKey);
    //	util.decrypt("encfilePath", "decfilePath", privateKey);
    	System.out.println("结束..");
		
	}
}
