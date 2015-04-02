<jsp:useBean id="managesqlpageSQLPage" scope="session" class="fr.paris.lutece.plugins.sqlpage.web.SQLPageJspBean" />
<% String strContent = managesqlpageSQLPage.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
