<%--
  Created by IntelliJ IDEA.
  User: LJH
  Date: 2012-2-17
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<HTML>
<HEAD>
    <TITLE>FusionCharts Free - Simple Column 3D Chart using dataXML method</TITLE>
    <style type="text/css" >
        body {
            font-family: Arial, Helvetica, sans-serif;
            font-size: 12px;
        }

        .border{
          border:blue;

        }
    </style>

</HEAD>
<BODY>
<CENTER>

    <h4>Basic example using dataXML method (with XML data hard-coded in
        JSP page itself)</h4>

    <%
        String strXML = "";

        strXML += "<chart caption='Monthly Unit Sales' xAxisName='Month' yAxisName='Units' decimalPrecision='0' formatNumberScale='0' pieRadius='100'>";
        strXML += "<set label='Jan' value='462' />";
        strXML += "<set label='Feb' value='857' />";
        strXML += "<set label='Mar' value='671' />";
        strXML += "<set label='Apr' value='494' />";
        strXML += "<set label='May' value='761' />";
        strXML += "<set label='Jun' value='960' />";
        strXML += "<set label='Jul' value='629' />";
        strXML += "<set label='Aug' value='622' />";
        strXML += "<set label='Sep' value='376' />";
        strXML += "<set label='Oct' value='494' />";
        strXML += "<set label='Nov' value='761' />";
        strXML += "<set label='Dec' value='960' />";
        strXML += "</chart>";

        //Create the chart - Column 3D Chart with data from strXML variable using dataXML method

    %>
    <jsp:include page="FusionChartsHTMLRenderer.jsp" flush="true">
        <jsp:param name="chartSWF" value="../charts/Pie3D.swf"/>
        <jsp:param name="strURL" value=""/>
        <jsp:param name="strXML" value="<%=strXML%>"/>
        <jsp:param name="chartId" value="myNext"/>
        <jsp:param name="chartWidth" value="600"/>
        <jsp:param name="chartHeight" value="300"/>
        <jsp:param name="debugMode" value="false"/>
    </jsp:include>
    <BR>
    <BR>

    <div></div>
</CENTER>
</BODY>
</HTML>