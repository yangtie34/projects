package com.jhkj.mosdc.framework.util;

import javax.persistence.Column;
import javax.persistence.Table;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: LJH
 * Date: 2012-2-6
 */
public class Write2DB {
    public void write() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("struts.xml");
        String path = url.getPath().replace("%20", " ");
        String path2 = path.substring(0, path.lastIndexOf("/"));
        String ClassPath = path2 + "/com/jhkj/mosdc/oa/po/";

        File sqlFile = new File("d:\\stsx.sql");
        System.out.println("sqlFile:" + sqlFile.getAbsolutePath());
        BufferedWriter bfWriter = null;
        if (sqlFile.exists()) {
            sqlFile.delete();
            try {
                if (sqlFile.createNewFile()) {
                    System.out.println("创建");
                } else {
                    System.out.println("文件没有创建");
                }
                bfWriter = new BufferedWriter(new FileWriter(sqlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (sqlFile.createNewFile()) {
                    System.out.println("创建");
                } else {
                    System.out.println("文件没有创建");
                }
                bfWriter = new BufferedWriter(new FileWriter(sqlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(ClassPath);
        File[] listFiles = file.listFiles();
        int id = 2049;
        for (int i = 0; i < listFiles.length; i++) {
            String fileName = listFiles[i].getName();
            String clsName = fileName.substring(0, fileName.lastIndexOf("."));
            try {
                Class cls = Class.forName("com.jhkj.mosdc.oa.po." + clsName);
//                Table ann = (Table) cls.getAnnotation(javax.persistence.Table.class);
//                String tableName = ann.name();

                Method[] methods = cls.getDeclaredMethods();
                for (int k = 0; k < methods.length; k++) {

                    String methodName = methods[k].getName();
                    if (methodName.startsWith("get")) {
                        Class returnType = methods[k].getReturnType();
                        String fieldType = returnType.getName().substring(returnType.getName().lastIndexOf(".") + 1);
                        String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                        //System.out.println("ClassName:" + clsName + ",field:" + field);
                        Column colAnn = methods[k].getAnnotation(javax.persistence.Column.class);
                        int length = colAnn.length();
                        if("Long".equals(fieldType)){
                        	length = colAnn.precision(); 
                        }
//                        String sql = "insert into ts_stsx values (" + (id++) + ",'" + fieldName + "','','" + fieldType + "','','','" + length + "','','','','" + clsName + "','','','','','','','');\r\n";
                        String sql = "insert into ts_stsx (id,sx,sxxslx,cd,ssstm)  values (" + (id++) + ",'" + fieldName + "','" + fieldType + "','" + length + "','" + clsName + "');\r\n";
                        //System.out.println("sql:" + sql);
                        if (bfWriter != null) {
                            try {
                                bfWriter.write(sql);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("失败");
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try{
        	bfWriter.flush();
        	bfWriter.close();
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    
    public void aaa(){
        URL url = Thread.currentThread().getContextClassLoader().getResource("struts.xml");
        String path = url.getPath();
        String path2 = path.substring(0, path.lastIndexOf("/"));
        String ClassPath = path2 + "/com/jhkj/mosdc/oa/po/";

        File sqlFile = new File("d:\\stsx.sql");
        System.out.println("sqlFile:" + sqlFile.getAbsolutePath());
        BufferedWriter bfWriter = null;
        if (sqlFile.exists()) {
            sqlFile.delete();
            try {
                if (sqlFile.createNewFile()) {
                    System.out.println("创建");
                } else {
                    System.out.println("文件没有创建");
                }
                bfWriter = new BufferedWriter(new FileWriter(sqlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (sqlFile.createNewFile()) {
                    System.out.println("创建");
                } else {
                    System.out.println("文件没有创建");
                }
                bfWriter = new BufferedWriter(new FileWriter(sqlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(ClassPath+"VbSssgfp.java");
        int id = 731;
            String fileName = file.getName();
            String clsName = fileName.substring(0, fileName.lastIndexOf("."));
            try {
                Class cls = Class.forName("com.jhkj.mosdc.oa.po." + clsName);
//                Table ann = (Table) cls.getAnnotation(javax.persistence.Table.class);
//                String tableName = ann.name();

                Method[] methods = cls.getDeclaredMethods();
                for (int k = 0; k < methods.length; k++) {

                    String methodName = methods[k].getName();
                    if (methodName.startsWith("get")) {
                        Class returnType = methods[k].getReturnType();
                        String fieldType = returnType.getName().substring(returnType.getName().lastIndexOf(".") + 1);
                        String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                        //System.out.println("ClassName:" + clsName + ",field:" + field);
                        Column colAnn = methods[k].getAnnotation(javax.persistence.Column.class);
                        int length = colAnn.length();
                        if("Long".equals(fieldType)){
                        	length = colAnn.precision(); 
                        }
                        String sql = "insert into ts_stsx (id,sx,sxxslx,cd,ssstm)  values (" + (id++) + ",'" + fieldName + "','" + fieldType + "','" + length + "','" + clsName + "');\r\n";
                        //System.out.println("sql:" + sql);
                        if (bfWriter != null) {
                            try {
                                bfWriter.write(sql);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("失败");
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        
        try{
        	bfWriter.flush();
        	bfWriter.close();
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        Write2DB obj = new Write2DB();
        obj.write();
    }
}
