// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 2009-2-13 下午 03:08:19
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   EncodingFilter.java

package com.jhkj.mosdc.framework.bean;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionFilter
    implements Filter
{

    public SessionFilter()
    {
        filterConfig = null;
    }

    public void destroy()
    {
        filterConfig = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String method = request.getQueryString();
        HttpSession session = request.getSession(false);
        System.out.println("++++++++++++++++++++++++++++++++++++++ getQueryString "+method);
        System.out.println("++++++++++++++++++++++++++++++++++++++ server1 "+server);
        	if(server.equals("10.120.33.128")||server.equals("10.120.33.2")){
        		System.out.println("++++++++++++++++++++++++++++++++++++++ server 2"+server);
        		 if(method!=null&&method.indexOf("getUserSession") < 0 )
                 {
        			 System.out.println("++++++++++++++++++++++++++++++++++++++ server3 "+server);
                     if(session == null || session.getAttribute("userSession") == null)
                     {
                    	 System.out.println("++++++++++++++++++++++++++++++++++++++ server 4"+server);
                         response.sendRedirect("/kjw/login/login.htm");
                         return;
                     }
                 }
        	}else{
        		if(method!=null&&method.indexOf("findUserSessionTest") < 0 )
                {
                    if(session == null || session.getAttribute("userSession") == null)
                    {
                        response.sendRedirect("/kjw/login/login.htm");
                        return;
                    }
                }
        	}

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config)
        throws ServletException
    {
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            server = addr.getHostAddress().toString();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        filterConfig = config;
    }
    protected String server;
    protected FilterConfig filterConfig;
}