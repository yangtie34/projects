package com.jhkj.mosdc.sc.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSON;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import com.jhkj.mosdc.framework.action.BaseAction;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.sc.service.LeaveSchoolService;
import com.opensymphony.xwork2.Action;

/**
 * 登录Action
 * 
 * @author Administrator
 * 
 */
public class LeaveSchoolAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(LeaveSchoolAction.class);

	private File txtFile;  
	private boolean success;
	private LeaveSchoolService leaveSchoolService;
	
	public void setLeaveSchoolService(LeaveSchoolService leaveSchoolService) {
		this.leaveSchoolService = leaveSchoolService;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	private String txtFileFileName;

	private String txtFileContentType;
	
	public String getTxtFileFileName() {
		return txtFileFileName;
	}
	public void setTxtFileFileName(String txtFileFileName) {
		this.txtFileFileName = txtFileFileName;
	}
	public String getTxtFileContentType() {
		return txtFileContentType;
	}
	public void setTxtFileContentType(String txtFileContentType) {
		this.txtFileContentType = txtFileContentType;
	}
	public File getTxtFile() {
		return txtFile;
	}
	public void setTxtFile(File txtFile) {
		this.txtFile = txtFile;
	}
	public void upload(){

		logger.info("开始上传文件.... ");

		String serverPath = ServletActionContext.getServletContext()
				.getRealPath("/")
				+ "import\\";

		File dataFile = new File(serverPath + "\\" + this.getTxtFileFileName());

		try {
			if (dataFile.exists()) {
				dataFile.delete();
			}
			// 将第一个参数对应的 文件 copy 到 第二个参数对应的文件中
			FileUtil.copyFile(this.txtFile, dataFile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
		}

		logger.info("------上传文件成功======");
		try {  
            Workbook workbook = Workbook.getWorkbook(dataFile);  
            Sheet sheet = workbook.getSheet(0);  
            // j为行数，getCell("列号","行号")  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            for (int i = 1, j = sheet.getRows(); i < j; i++) {  
                Cell c1 = sheet.getCell(0, i);  
                String name = c1.getContents();  
                c1 = sheet.getCell(1, i);  
                String StartTime = c1.getContents(); 
                c1 = sheet.getCell(2, i);  
                String EndTime = c1.getContents(); 
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("CODE", name);
                map.put("STARTTIME", StartTime);
                map.put("ENDTIME", EndTime);
                map.put("TIME", sdf.format(new Date()));
                if(!leaveSchoolService.haveCode(name)){
                	leaveSchoolService.saveStudent(map);
                }
            }  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
		success = true;
		Struts2Utils.getResponse().setContentType("text/html");
		try {
			Struts2Utils.getResponse().getWriter().write("{success:true}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("导入完成------------------------");
		//return "{success:true}";
	
	}
	public String index(){
		return Action.SUCCESS;
	}
	
}
