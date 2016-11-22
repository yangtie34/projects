package test.test1.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommonFilter implements Filter{  
    
  public void destroy() {  
        
  }  
    
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,  
          FilterChain filterchain) throws IOException, ServletException {  
    
      System.out.println("doFilter");
      
      
      
  }  
    
  public void init(FilterConfig config) throws ServletException {  
        System.out.println("init");
  }  
}  