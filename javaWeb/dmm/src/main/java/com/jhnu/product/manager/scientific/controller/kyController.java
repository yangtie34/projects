package com.jhnu.product.manager.scientific.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.product.manager.scientific.service.ScientificService;

@Controller
@RequestMapping("/manager/keyan")
public class kyController {

	@Autowired
	private ScientificService scientificService;

	@RequestMapping(method = RequestMethod.GET, value = "/input")
	// 录入弹窗页面
	public ModelAndView input() {
		ModelAndView mv = new ModelAndView("/manager/keyan/input");
		mv.addObject("tabless", scientificService.getTablesName());
		mv.addObject("forjs", "input");
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView("/manager/keyan/input");
		mv.addObject("tabless", scientificService.getTablesName());
		mv.addObject("forjs", "view");
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/check")
	public ModelAndView check() {
		ModelAndView mv = new ModelAndView("/manager/keyan/input");
		mv.addObject("tabless", scientificService.getTablesName());
		mv.addObject("forjs", "check");
		return mv;
	}
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/subExcel")
	public List uploadExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 这里我用到了jar包
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		MultipartFile file=null;
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			java.util.Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				 file = multiRequest.getFile((String) iter.next());
				/*if (file != null) {
					String fileName = file.getOriginalFilename();

					String path1 = Thread.currentThread()
							.getContextClassLoader().getResource("").getPath()
							+ "download" + File.separator;
					// 下面的加的日期是为了防止上传的名字一样
					String path = path1;
					
					 * + new SimpleDateFormat("yyyyMMddHHmmss") .format(new
					 * Date()) + fileName;
					 
					File localFile = new File(path);
					file.transferTo(localFile);*/
				//}
			}
		}
		return scientificService.readXls(file);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getXls")
    public String download( @RequestParam("titles")String titles,@RequestParam("sheetName")String sheetName, HttpServletRequest request,
            HttpServletResponse response) {
		try {
		if (titles.equals(new String(titles.getBytes("ISO-8859-1"), "ISO-8859-1"))) {      
			
				titles= new String(titles.getBytes("ISO-8859-1"), "utf-8");
        }  
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + sheetName+".xls");
        try {
        	HSSFWorkbook hssf=scientificService.getXls(titles, sheetName);
            OutputStream os = response.getOutputStream();
           // MultipartFile file=new MultipartFile();
          hssf.write(os);
             // 这里主要关闭。
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            //  返回值要注意，要不然就出现下面这句错误！
            //java+getOutputStream() has already been called for this response
        return null;
    }
}
