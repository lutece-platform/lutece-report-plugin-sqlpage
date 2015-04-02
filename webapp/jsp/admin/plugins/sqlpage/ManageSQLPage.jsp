<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="managesqlpage" scope="session" class="fr.paris.lutece.plugins.sqlpage.web.ManageSQLPageJspBean" />

<% managesqlpage.init( request, managesqlpage.RIGHT_MANAGESQLPAGE ); %>
<%= managesqlpage.getManageSQLPageHome ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
