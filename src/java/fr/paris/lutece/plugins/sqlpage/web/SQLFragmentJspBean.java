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

import fr.paris.lutece.plugins.sqlpage.business.SQLFragment;
import fr.paris.lutece.plugins.sqlpage.business.SQLFragmentHome;
import fr.paris.lutece.plugins.sqlpage.business.SQLPage;
import fr.paris.lutece.plugins.sqlpage.business.SQLPageHome;
import fr.paris.lutece.portal.business.role.RoleHome;
import fr.paris.lutece.portal.service.database.AppConnectionService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage SQLFragment features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageSQLFragments.jsp", controllerPath = "jsp/admin/plugins/sqlpage/", right = "SQLPAGE_MANAGEMENT" )
public class SQLFragmentJspBean extends ManageSQLPageJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_SQLFRAGMENTS = "/admin/plugins/sqlpage/manage_sqlfragments.html";
    private static final String TEMPLATE_CREATE_SQLFRAGMENT = "/admin/plugins/sqlpage/create_sqlfragment.html";
    private static final String TEMPLATE_MODIFY_SQLFRAGMENT = "/admin/plugins/sqlpage/modify_sqlfragment.html";

    // Parameters
    private static final String PARAMETER_ID_SQLFRAGMENT = "id";
    private static final String PARAMETER_ID_SQLPAGE = "id_page";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_SQLFRAGMENTS = "sqlpage.manage_sqlfragments.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_SQLFRAGMENT = "sqlpage.modify_sqlfragment.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_SQLFRAGMENT = "sqlpage.create_sqlfragment.pageTitle";

    // Markers
    private static final String MARK_SQLFRAGMENT_LIST = "sqlfragment_list";
    private static final String MARK_SQLFRAGMENT = "sqlfragment";
    private static final String MARK_PAGE = "page";
    private static final String MARK_ID_PAGE = "id_page";
    private static final String MARK_POOLS_LIST = "pools_list";
    private static final String MARK_ROLES_LIST = "roles_list";
    private static final String JSP_MANAGE_SQLFRAGMENTS = "jsp/admin/plugins/sqlpage/ManageSQLFragments.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_SQLFRAGMENT = "sqlpage.message.confirmRemoveSQLFragment";
    private static final String PROPERTY_DEFAULT_LIST_SQLFRAGMENT_PER_PAGE = "sqlpage.listSQLFragments.itemsPerPage";
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "sqlpage.model.entity.sqlfragment.attribute.";

    // Views
    private static final String VIEW_MANAGE_SQLFRAGMENTS = "manageSQLFragments";
    private static final String VIEW_CREATE_SQLFRAGMENT = "createSQLFragment";
    private static final String VIEW_MODIFY_SQLFRAGMENT = "modifySQLFragment";

    // Actions
    private static final String ACTION_CREATE_SQLFRAGMENT = "createSQLFragment";
    private static final String ACTION_MODIFY_SQLFRAGMENT = "modifySQLFragment";
    private static final String ACTION_REMOVE_SQLFRAGMENT = "removeSQLFragment";
    private static final String ACTION_CONFIRM_REMOVE_SQLFRAGMENT = "confirmRemoveSQLFragment";

    // Infos
    private static final String INFO_SQLFRAGMENT_CREATED = "sqlpage.info.sqlfragment.created";
    private static final String INFO_SQLFRAGMENT_UPDATED = "sqlpage.info.sqlfragment.updated";
    private static final String INFO_SQLFRAGMENT_REMOVED = "sqlpage.info.sqlfragment.removed";

    // Session variable to store working values
    private SQLFragment _sqlfragment;

    @View( value = VIEW_MANAGE_SQLFRAGMENTS, defaultView = true )
    public String getManageSQLFragments( HttpServletRequest request )
    {
        _sqlfragment = null;
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_SQLFRAGMENT_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        String strIdPage = request.getParameter( PARAMETER_ID_SQLPAGE );
        int nIdPage = Integer.parseInt( strIdPage );

        SQLPage page = SQLPageHome.findByPrimaryKey( nIdPage );

        UrlItem url = new UrlItem( JSP_MANAGE_SQLFRAGMENTS );
        String strUrl = url.getUrl(  );
        List<SQLFragment> listSQLFragments = (List<SQLFragment>) SQLFragmentHome.getSQLFragmentsList( nIdPage );

        // PAGINATOR
        LocalizedPaginator paginator = new LocalizedPaginator( listSQLFragments, _nItemsPerPage, strUrl,
                PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );

        Map<String, Object> model = getModel(  );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_SQLFRAGMENT_LIST, paginator.getPageItems(  ) );
        model.put( MARK_PAGE, page );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_SQLFRAGMENTS, TEMPLATE_MANAGE_SQLFRAGMENTS, model );
    }

    /**
     * Returns the form to create a sqlfragment
     *
     * @param request The Http request
     * @return the html code of the sqlfragment form
     */
    @View( VIEW_CREATE_SQLFRAGMENT )
    public String getCreateSQLFragment( HttpServletRequest request )
    {
        _sqlfragment = ( _sqlfragment != null ) ? _sqlfragment : new SQLFragment(  );

        
        Map<String, Object> model = getModel(  );
        model.put( MARK_SQLFRAGMENT, _sqlfragment );

        addCommons( model );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_SQLFRAGMENT, TEMPLATE_CREATE_SQLFRAGMENT, model );
    }

    /**
     * Process the data capture form of a new sqlfragment
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_SQLFRAGMENT )
    public String doCreateSQLFragment( HttpServletRequest request )
    {
        populate( _sqlfragment, request );

        // Check constraints
        if ( !validateBean( _sqlfragment, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_SQLFRAGMENT );
        }

        SQLFragmentHome.create( _sqlfragment );
        addInfo( INFO_SQLFRAGMENT_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_SQLFRAGMENTS );
    }

    /**
     * Manages the removal form of a sqlfragment whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_SQLFRAGMENT )
    public String getConfirmRemoveSQLFragment( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SQLFRAGMENT ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_SQLFRAGMENT ) );
        url.addParameter( PARAMETER_ID_SQLFRAGMENT, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_SQLFRAGMENT,
                url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a sqlfragment
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage sqlfragments
     */
    @Action( ACTION_REMOVE_SQLFRAGMENT )
    public String doRemoveSQLFragment( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SQLFRAGMENT ) );
        SQLFragmentHome.remove( nId );
        addInfo( INFO_SQLFRAGMENT_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_SQLFRAGMENTS );
    }

    /**
     * Returns the form to update info about a sqlfragment
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_SQLFRAGMENT )
    public String getModifySQLFragment( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_SQLFRAGMENT ) );

        if ( ( _sqlfragment == null ) || ( _sqlfragment.getId(  ) != nId ) )
        {
            _sqlfragment = SQLFragmentHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_SQLFRAGMENT, _sqlfragment );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_SQLFRAGMENT, TEMPLATE_MODIFY_SQLFRAGMENT, model );
    }

    /**
     * Process the change form of a sqlfragment
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_SQLFRAGMENT )
    public String doModifySQLFragment( HttpServletRequest request )
    {
        populate( _sqlfragment, request );

        // Check constraints
        if ( !validateBean( _sqlfragment, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_SQLFRAGMENT, PARAMETER_ID_SQLFRAGMENT, _sqlfragment.getId(  ) );
        }

        SQLFragmentHome.update( _sqlfragment );
        addInfo( INFO_SQLFRAGMENT_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_SQLFRAGMENTS );
    }

    private void addCommons(Map<String, Object> model)
    {
        // Add pools list
        ReferenceList listPools = new ReferenceList(  );
        AppConnectionService.getPoolList( listPools );
        model.put( MARK_POOLS_LIST, listPools );

        //Add roles List
        ReferenceList roleList = RoleHome.getRolesList(  );
        model.put( MARK_ROLES_LIST, roleList );
     }
}
