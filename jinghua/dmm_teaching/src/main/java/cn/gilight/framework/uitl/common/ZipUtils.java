package cn.gilight.framework.uitl.common;
import java.io.File;  

import org.apache.tools.ant.Project;  
import org.apache.tools.ant.taskdefs.Zip;  
import org.apache.tools.ant.types.FileSet;  
  
/**
 * @ClassName: ZipUtils
 * @CreateTime Apr 28, 2013 1:23:45 PM
 * @author : Mayi
 * @Description: 压缩文件的通用工具类-采用ant中的org.apache.tools.ant.taskdefs.Zip来实现，更加简单。
 *
 */
public class ZipUtils {  
  
    private File zipFile;  
  
    /**
     * 压缩文件构造函数
     * @param pathName 最终压缩生成的压缩文件：目录+压缩文件名.zip
     */
    public ZipUtils(String finalFile) {  
        zipFile = new File(finalFile);  
    }  
      
    /**
     * 执行压缩操作
     * @param srcPathName 需要被压缩的文件/文件夹
     */
    public String compressExe(String srcPathName) {  
        File srcdir = new File(srcPathName);  
        if (!srcdir.exists()){
//            throw new RuntimeException(srcPathName + "不存在！"); 
            return "文件或文件夹不存在!";
        } 
          
        Project prj = new Project();  
        Zip zip = new Zip();  
        zip.setProject(prj);  
        zip.setDestFile(zipFile);  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(prj);  
        fileSet.setDir(srcdir);  
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");  
        //fileSet.setExcludes(...); //排除哪些文件或文件夹  
        zip.addFileset(fileSet);  
        zip.execute();  
        return "发送成功!";
    }
    /**
     * 测试压缩包
     * @param args
     */

    	public static void main(String[] args) {
    		
    		ZipUtils zca = new ZipUtils("E:\\workspace\\dmm_xg\\src\\main\\webapp\\static\\email\\欠费详细信息.zip");
    		zca.compressExe("E:\\workspace\\dmm_xg\\src\\main\\webapp\\static\\email");
    	}
} 