package com.jhnu.util.common;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;

public class UserUtil {
	
	public static String getLoginUserName(HttpServletRequest request) {
		String userName = null;
		UsernamePasswordCredential sss=(UsernamePasswordCredential) request.getAttribute("credential");
		userName=sss.getUsername();
		if(userName==null){
			 CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator = (CookieRetrievingCookieGenerator) SpringContextUtil.getBean("ticketGrantingTicketCookieGenerator");
		        TicketRegistry ticketRegistry = (TicketRegistry) SpringContextUtil.getBean("ticketRegistry");
		        


		        String serviceTicketId = ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);


		        TicketGrantingTicket serviceTicket = (TicketGrantingTicket) ticketRegistry.getTicket(serviceTicketId);
		        if (serviceTicket != null && serviceTicket.isExpired() == false) {
		            userName = serviceTicket.getAuthentication().getPrincipal().getId();
		        }
		}
        return userName;
	}
}
