<jsp:useBean id="manageSqlPageParameter" scope="session" class="fr.paris.lutece.plugins.sqlpage.web.ManageSQLPageParameterJspBean" />
<% String strContent = manageSqlPageParameter.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
