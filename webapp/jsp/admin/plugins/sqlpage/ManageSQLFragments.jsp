<jsp:useBean id="managesqlpageSQLFragment" scope="session" class="fr.paris.lutece.plugins.sqlpage.web.SQLFragmentJspBean" />
<% String strContent = managesqlpageSQLFragment.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
