/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.sqlpage.web;

import fr.paris.lutece.plugins.sqlpage.business.SQLPage;
import fr.paris.lutece.plugins.sqlpage.business.SQLPageHome;
import fr.paris.lutece.plugins.sqlpage.service.SQLPageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage SQLPage features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageSecondaireSQLPages.jsp", controllerPath = "jsp/admin/plugins/sqlpage/", right = "SQLPAGE_MANAGEMENT_WEBMASTER" )
public class SQLPageSecondJspBean extends ManageSQLPageJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_SQLPAGES = "/admin/plugins/sqlpage/manage_secondaire_sqlpages.html";

    // Parameters
    private static final String PARAMETER_ID_SQLPAGE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_SQLPAGES = "sqlpage.manage_sqlpages.pageTitle";

    // Markers
    private static final String MARK_SQLPAGE_LIST = "sqlpage_list";
    private static final String JSP_MANAGE_SQLPAGES = "jsp/admin/plugins/sqlpage/ManageSecondaireSQLPages.jsp";

    // Properties
    private static final String MESSAGE_NOT_AUTHORIZED = "sqlpage.message.notAuthorized";
    private static final String MESSAGE_PAGE_NOT_FOUND = "sqlpage.message.pageNotFound";

    // Views
    private static final String VIEW_MANAGE_SQLPAGES = "manageSQLPages";
    private static final String VIEW_SHOW_SQLPAGE = "showSQLPage";
    private static final long serialVersionUID = 1L;

    // Session variable to store working values
    private SQLPage _sqlpage;

    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_SQLPAGES, defaultView = true )
    public String getManageSQLPages( HttpServletRequest request )
    {
        _sqlpage = null;

        List<SQLPage> listSQLPages = (List<SQLPage>) SQLPageService.getAuthorizedPages( getUser(  ) );
        Map<String, Object> model = getPaginatedListModel( request, MARK_SQLPAGE_LIST, listSQLPages, JSP_MANAGE_SQLPAGES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_SQLPAGES, TEMPLATE_MANAGE_SQLPAGES, model );
    }

    /**
     * Returns the SQLPage
     *
     * @param request The Http request
     * @return The HTML to test SQLPage
     */
    @View( VIEW_SHOW_SQLPAGE )
    public String getShowSQLPage( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SQLPAGE ) );

        if ( ( _sqlpage == null ) || ( _sqlpage.getId(  ) != nId ) )
        {
            _sqlpage = SQLPageHome.findByPrimaryKey( nId );
        }

        if ( _sqlpage == null )
        {
            addError( MESSAGE_PAGE_NOT_FOUND, getLocale(  ) );

            return redirectView( request, VIEW_MANAGE_SQLPAGES );
        }

        if ( !SQLPageService.isAuthorized( _sqlpage, getUser(  ) ) )
        {
            addError( MESSAGE_NOT_AUTHORIZED, getLocale(  ) );

            return redirectView( request, VIEW_MANAGE_SQLPAGES );
        }
     
        StringBuilder sbHtml = SQLPageService.getStringSQLFragment( nId, request );
        
        return sbHtml.toString( );
    }
}
