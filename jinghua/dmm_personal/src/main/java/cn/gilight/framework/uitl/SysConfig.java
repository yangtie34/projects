package cn.gilight.framework.uitl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**   
* @Description: TODO
* @author Sunwg  
* @date 2016年3月9日 下午2:05:02   
*/
public class SysConfig {
	
	private static Logger logger = Logger.getLogger(SysConfig.class);
	
	private static final String configFile = "/sysConfig.properties";

	/** 
	* @Fields serverUrl : 本项目外网访问地址（注意是域名）
	*/ 
	
	private int cardPayAbnormalNum;
	
	private String schoolName;
	
	private String schoolYear;
	
	private String school_motto;
	
	private String school_img;
	
	private String school_baike_url;
	
	private String schoolRoad;
	
	private String gpsPosition;
	
	private String lunchTime;
	
	private String supperTime;
	
	private String uploadImageSavePath;
	
	private String dmmUrl;
	

	private static SysConfig config = new SysConfig();
	
	private SysConfig(){
		Properties p = new Properties();
		InputStream inStream = this.getClass().getResourceAsStream(configFile);
		if(inStream == null){
			logger.error("根目录下找不到sysConfig.properties文件");
			return;
		}
		try {
			p.load(new InputStreamReader(inStream, "UTF-8"));     
			
			/* 重点在这里，读取properties文件进行赋值 */
			this.cardPayAbnormalNum = new Integer(p.getProperty("job.card.payAbnormalNum").trim());
			this.schoolName = p.getProperty("sys.schoolName").trim();
			this.schoolYear = p.getProperty("sys.schoolYear").trim();
			this.school_motto = p.getProperty("sys.school_motto").trim();
			this.school_img = p.getProperty("sys.school_img").trim();
			this.school_baike_url = p.getProperty("sys.school_baike_url").trim();
			this.gpsPosition = p.getProperty("sys.gpsPosition").trim();
			this.schoolRoad = p.getProperty("sys.schoolRoad").trim();
			this.lunchTime = p.getProperty("job.smart.lunch_time").trim();
			this.supperTime = p.getProperty("job.smart.supper_time").trim();
			this.uploadImageSavePath = p.getProperty("sys.upload.image_save_path").trim();
			this.dmmUrl = p.getProperty("sys.dmmUrl").trim();
			inStream.close();
		} catch (IOException e) {
			logger.error("load sysConfig.properties error,class根目录下找不到sysConfig.properties文件");
			e.printStackTrace();
		}
		logger.info("load sysConfig.properties success");
	}
	
	/** 
	* @Description: 返回实例 
	* @return SysConfig
	*/
	public static SysConfig instance(){
		return config;
	}
	
	public int getCardPayAbnormalNum() {
		return cardPayAbnormalNum;
	}
	
	public String getSchoolName() {
		return schoolName;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchool_motto() {
		return school_motto;
	}

	public void setSchool_motto(String school_motto) {
		this.school_motto = school_motto;
	}

	public String getSchool_img() {
		return school_img;
	}

	public void setSchool_img(String school_img) {
		this.school_img = school_img;
	}

	public String getSchool_baike_url() {
		return school_baike_url;
	}

	public void setSchool_baike_url(String school_baike_url) {
		this.school_baike_url = school_baike_url;
	}

	public String getGpsPosition() {
		return gpsPosition;
	}

	public String getSchoolRoad() {
		return schoolRoad;
	}
	public String getDmmUrl() {
		return dmmUrl;
	}

	public void setDmmUrl(String dmmUrl) {
		this.dmmUrl = dmmUrl;
	}

	public String getLunchTime() {
		return lunchTime;
	}
	
	public String getSupperTime() {
		return supperTime;
	}

	public String getUploadImageSavePath() {
		return uploadImageSavePath;
	}
}