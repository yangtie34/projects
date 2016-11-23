package cn.gilight.framework.uitl.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
 
public class FileUtil {
    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     * @param fileName    文件的名
     */
    public static byte[] readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] content = readBytes(in);
            in.close();
            return content;
        } catch (IOException e) {
            return null;
        }
    }
    
    public static byte[] readBytes(InputStream in) throws IOException {  
        BufferedInputStream bufin = new BufferedInputStream(in);  
        int buffSize = 1024;  
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);  
  
  
        byte[] temp = new byte[buffSize];  
        int size = 0;  
        while ((size = bufin.read(temp)) != -1) {  
            out.write(temp, 0, size);  
        }  
        bufin.close();  
  
        byte[] content = out.toByteArray();  
        return content;  
    }   
 
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     * 
     * @param fileName
     *            文件名
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，rn这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。
                if (((char) tempchar) != 'r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != 'r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == 'r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
 
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * 
     * @param fileName
     *            文件名
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
 
    /**
     * 随机读取文件内容
     * 
     * @param fileName
     *            文件名
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }
 
    /**
     * 显示输入流中还剩的字节数
     * 
     * @param in
     */
    public static int showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
            return in.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
 
    public static void main(String[] args) {
        String fileName = "E:/workspace/dmm_sys/src/main/resources/photo/test.jpg";
        // ReadFromFile.readFileByBytes(fileName);
        // /ReadFromFile.readFileByChars(fileName);
        // ReadFromFile.readFileByLines(fileName);
        // ReadFromFile.readFileByRandomAccess(fileName);
        byte[] content=readFileByBytes(fileName);
        for (int i = 0; i < content.length; i++) {
			System.out.println(content[i]);
		}
        // 按方法A追加文件
        // AppendToFile.appendMethodA(fileName, content);
        // AppendToFile.appendMethodA(fileName, "append end. n");
        // 显示文件内容
        // ReadFromFile.readFileByLines(fileName);
        // 按方法B追加文件
        // AppendToFile.appendMethodB(fileName, content);
        // AppendToFile.appendMethodB(fileName, "append end. n");
        // 显示文件内容
        // ReadFromFile.readFileByLines(fileName);
    
    }
 
    /**
     * A方法追加文件：使用RandomAccessFile
     * 
     * @param fileName
     *            文件名
     * @param content
     *            追加的内容
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * B方法追加文件：使用FileWriter
     * 
     * @param fileName
     * @param content
     */
    public static void appendMethodB(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
