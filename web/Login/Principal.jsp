<%-- 
    Document   : Principal
    Created on : 12/10/2015, 09:01:02 AM
    Author     : H-URBINA-M
--%>
<%@include file="../WEB-INF/jspf/aaadmin.jspf" %>
<%@page contentType="text/html" import="BusinessServices.Beans.BeanUsuario" pageEncoding="UTF-8" autoFlush='true' session="true"%>
<%    BeanUsuario Usuario = (BeanUsuario) session.getAttribute("objUsuario" + session.getId());
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Expires" content="0"/>
        <meta http-equiv="Last-Modified" content="0"/>
        <meta http-equiv="Cache-Control" content="no-cache, mustrevalidate"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <title>.:: <%=Usuario.getSistema() + " - " + Usuario.getPaterno() + " " + Usuario.getMaterno() + ", " + Usuario.getNombres()%>::.</title>
    </head>
    <frameset rows="*" frameborder="no" border="0" framespacing="0" >
        <frame src="MainPrincipal.jsp" name="fra_Principal" id="fra_Principal" title="Principal" />
    </frameset>
    <noframes>
        <body oncontextmenu='return false'></body>
    </noframes>
</html>