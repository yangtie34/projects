<%@ page contentType="text/html; charset=GBK"%>
<% out.println("总 内 存: " + java.lang.Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");

 out.println("<br>");

 out.println("可用内存: " + java.lang.Runtime.getRuntime().freeMemory()  / 1024 / 1024 + "MB");%>