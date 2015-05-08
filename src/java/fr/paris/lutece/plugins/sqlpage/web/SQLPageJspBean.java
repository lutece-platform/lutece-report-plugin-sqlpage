/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage SQLPage features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageSQLPages.jsp", controllerPath = "jsp/admin/plugins/sqlpage/", right = "SQLPAGE_MANAGEMENT" )
public class SQLPageJspBean extends ManageSQLPageJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_SQLPAGES = "/admin/plugins/sqlpage/manage_sqlpages.html";
    private static final String TEMPLATE_CREATE_SQLPAGE = "/admin/plugins/sqlpage/create_sqlpage.html";
    private static final String TEMPLATE_MODIFY_SQLPAGE = "/admin/plugins/sqlpage/modify_sqlpage.html";

    // Parameters
    private static final String PARAMETER_ID_SQLPAGE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_SQLPAGES = "sqlpage.manage_sqlpages.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_SQLPAGE = "sqlpage.modify_sqlpage.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_SQLPAGE = "sqlpage.create_sqlpage.pageTitle";

    // Markers
    private static final String MARK_SQLPAGE_LIST = "sqlpage_list";
    private static final String MARK_SQLPAGE = "sqlpage";
    private static final String MARK_WORKGROUP_LIST = "workgroup_list";
    private static final String JSP_MANAGE_SQLPAGES = "jsp/admin/plugins/sqlpage/ManageSQLPages.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_SQLPAGE = "sqlpage.message.confirmRemoveSQLPage";
    private static final String MESSAGE_NOT_AUTHORIZED = "sqlpage.message.notAuthorized";
    private static final String MESSAGE_PAGE_NOT_FOUND = "sqlpage.message.pageNotFound";
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "sqlpage.model.entity.sqlpage.attribute.";

    // Views
    private static final String VIEW_MANAGE_SQLPAGES = "manageSQLPages";
    private static final String VIEW_CREATE_SQLPAGE = "createSQLPage";
    private static final String VIEW_MODIFY_SQLPAGE = "modifySQLPage";

    // Actions
    private static final String ACTION_CREATE_SQLPAGE = "createSQLPage";
    private static final String ACTION_MODIFY_SQLPAGE = "modifySQLPage";
    private static final String ACTION_REMOVE_SQLPAGE = "removeSQLPage";
    private static final String ACTION_CONFIRM_REMOVE_SQLPAGE = "confirmRemoveSQLPage";

    // Infos
    private static final String INFO_SQLPAGE_CREATED = "sqlpage.info.sqlpage.created";
    private static final String INFO_SQLPAGE_UPDATED = "sqlpage.info.sqlpage.updated";
    private static final String INFO_SQLPAGE_REMOVED = "sqlpage.info.sqlpage.removed";
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
     * Returns the form to create a sqlpage
     *
     * @param request The Http request
     * @return the html code of the sqlpage form
     */
    @View( VIEW_CREATE_SQLPAGE )
    public String getCreateSQLPage( HttpServletRequest request )
    {
        _sqlpage = ( _sqlpage != null ) ? _sqlpage : new SQLPage(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_SQLPAGE, _sqlpage );
        model.put( MARK_WORKGROUP_LIST, AdminWorkgroupService.getUserWorkgroups( getUser(  ), getLocale(  ) ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_SQLPAGE, TEMPLATE_CREATE_SQLPAGE, model );
    }

    /**
     * Process the data capture form of a new sqlpage
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_SQLPAGE )
    public String doCreateSQLPage( HttpServletRequest request )
    {
        populate( _sqlpage, request );

        // Check constraints
        if ( !validateBean( _sqlpage, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_SQLPAGE );
        }

        SQLPageHome.create( _sqlpage );
        addInfo( INFO_SQLPAGE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_SQLPAGES );
    }

    /**
     * Manages the removal form of a sqlpage whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_SQLPAGE )
    public String getConfirmRemoveSQLPage( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SQLPAGE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_SQLPAGE ) );
        url.addParameter( PARAMETER_ID_SQLPAGE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_SQLPAGE,
                url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a sqlpage
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage sqlpages
     */
    @Action( ACTION_REMOVE_SQLPAGE )
    public String doRemoveSQLPage( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SQLPAGE ) );
        SQLPageHome.remove( nId );
        addInfo( INFO_SQLPAGE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_SQLPAGES );
    }

    /**
     * Returns the form to update info about a sqlpage
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_SQLPAGE )
    public String getModifySQLPage( HttpServletRequest request )
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

        Map<String, Object> model = getModel(  );
        model.put( MARK_SQLPAGE, _sqlpage );
        model.put( MARK_WORKGROUP_LIST, AdminWorkgroupService.getUserWorkgroups( getUser(  ), getLocale(  ) ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_SQLPAGE, TEMPLATE_MODIFY_SQLPAGE, model );
    }

    /**
     * Process the change form of a sqlpage
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_SQLPAGE )
    public String doModifySQLPage( HttpServletRequest request )
    {
        populate( _sqlpage, request );

        // Check constraints
        if ( !validateBean( _sqlpage, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_SQLPAGE, PARAMETER_ID_SQLPAGE, _sqlpage.getId(  ) );
        }

        if ( !SQLPageService.isAuthorized( _sqlpage, getUser(  ) ) )
        {
            addError( MESSAGE_NOT_AUTHORIZED, getLocale(  ) );

            return redirect( request, VIEW_MODIFY_SQLPAGE, PARAMETER_ID_SQLPAGE, _sqlpage.getId(  ) );
        }

        SQLPageHome.update( _sqlpage );
        addInfo( INFO_SQLPAGE_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_SQLPAGES );
    }
}
