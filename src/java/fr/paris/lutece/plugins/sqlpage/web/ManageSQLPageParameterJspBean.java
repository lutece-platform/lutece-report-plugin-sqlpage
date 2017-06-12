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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.sqlpage.business.SQLPage;
import fr.paris.lutece.plugins.sqlpage.business.SQLPageHome;
import fr.paris.lutece.plugins.sqlpage.business.parameter.SQLPageParameter;
import fr.paris.lutece.plugins.sqlpage.business.parameter.SQLPageParameterHome;
import fr.paris.lutece.plugins.sqlpage.service.SQLPageService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

/**
 * This class provides the user interface to manage SQLPageParameter features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageSQLPageParameters.jsp", controllerPath = "jsp/admin/plugins/sqlpage/", right = "SQLPAGE_MANAGEMENT" )
public class ManageSQLPageParameterJspBean extends ManageSQLPageJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_PARAMETERS_SQLPAGE = "/admin/plugins/sqlpage/manage_sqlpage_parameters.html";
    private static final String TEMPLATE_CREATE_SQLPAGE_PARAMETER = "/admin/plugins/sqlpage/create_sqlpage_parameter.html";
    private static final String TEMPLATE_MODIFY_SQLPAGE_PARAMETER = "/admin/plugins/sqlpage/modify_sqlpage_parameter.html";
    
    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_PARAMETER = "sqlpage.manage_sqlpages_parameters.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_PARAMETER = "sqlpage.manage_sqlpages_parameters.createPageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_PARAMETER = "sqlpage.manage_sqlpages_parameters.modifyPageTitle";
    
    // Properties
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "sqlpage.model.entity.sqlpageparameter.attribute.";
    
    // Views
    private static final String VIEW_MANAGE_SQLPAGES = "manageSQLPages";
    private static final String VIEW_MANAGE_SQLPAGE_PARAMETERS = "manageSQLPageParameters";
    private static final String VIEW_CREATE_SQLPAGE_PARAMETER = "createSQLPageParameter";
    private static final String VIEW_MODIFY_SQLPAGE_PARAMETER = "modifySQLPageParameter";
    private static final String VIEW_REDIRECT_MANAGE_SQLPAGE_PARAMETERS = "redirectManageSQLPageParameters";
    
    // Actions
    private static final String ACTION_CREATE_SQLPAGE_PARAMETER = "createSQLPageParameter";
    private static final String ACTION_REMOVE_SQLPAGE_PARAMETER = "removeSQLPageParameter";
    private static final String ACTION_MODIFY_SQLPAGE_PARAMETER = "modifySQLPageParameter";
    private static final String ACTION_CONFIRM_REMOVE_SQLPAGE_PARAMETER = "confirmRemoveSQLPageParameter";
    
    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_SQLPAGE_PARAMETER = "sqlpage.message.confirmRemoveSQLPageParameter";
    private static final String MESSAGE_NOT_AUTHORIZED = "sqlpage.message.notAuthorized";
    
    // Infos
    private static final String INFO_SQLPAGE_PARAMETER_CREATED = "sqlpage.info.sqlpageparameter.created";
    private static final String INFO_SQLPAGE_PARAMETER_UPDATED = "sqlpage.info.sqlpageparameter.updated";
    private static final String INFO_SQLPAGE_PARAMETER_REMOVED = "sqlpage.info.sqlpageparameter.removed";
    
    // Errors
    private static final String ERROR_SQLPAGE_PARAMETER = "sqlpage.manage_sqlpages_parameters.error";
    private static final String ERROR_PARAMETER_KEY_ALREADY_EXIST = "sqlpage.manage_sqlpages_parameters.error.key_already_exist";
    
    // Session variable to store working values
    private SQLPage _sqlPage;
    private SQLPageParameter _sqlPageParameter;
    
    /**
     * Generated serial version ID
     */
    private static final long serialVersionUID = -5806854803196524267L;

    /**
     * Return the Manage SQLPage parameters page
     * 
     * @param request The HTTP request
     * @return The HTML template for manage the parameters of the SQLPage
     */
    @View( VIEW_MANAGE_SQLPAGE_PARAMETERS )
    public String getManageParametersSQLPage( HttpServletRequest request )
    {
        try
        {
            int nId = Integer.parseInt( request.getParameter( SQLPageConstants.PARAMETER_ID_SQLPAGE ) ) ;
            _sqlPage = SQLPageHome.findByPrimaryKey( nId );
            _sqlPageParameter = null;
            
            Map<String, Object> model = getModel( );
            model.put( SQLPageConstants.MARK_SQLPAGE, _sqlPage );
            model.put( SQLPageConstants.MARK_PARAMETERS_LIST, SQLPageParameterHome.getSQLPageParametersList( nId ) );
        
            return getPage( PROPERTY_PAGE_TITLE_MANAGE_PARAMETER, TEMPLATE_MANAGE_PARAMETERS_SQLPAGE, model );
        }
        catch( NumberFormatException e )
        {
            // In the case where the id of the sqlpage is not in the request
            addError( ERROR_SQLPAGE_PARAMETER, getLocale( ) );

            return redirect( request, VIEW_MANAGE_SQLPAGES );
        }
    }
    
    /**
     * Return the Create SQLPage parameter page
     * 
     * @param request The HTTP request
     * @return The HTML template for create a parameter for the SQLPage
     */
    @View( VIEW_CREATE_SQLPAGE_PARAMETER )
    public String getCreateSQLPageParameter( HttpServletRequest request )
    {
        _sqlPageParameter = ( _sqlPageParameter != null ) ? _sqlPageParameter : new SQLPageParameter( );
        
        Map<String, Object> model = getModel( );
        model.put( SQLPageConstants.MARK_SQLPAGE_ID, _sqlPage.getId( ) );
        model.put( SQLPageConstants.MARK_SQLPAGE_PARAMETER, _sqlPageParameter );
        
        return getPage( PROPERTY_PAGE_TITLE_CREATE_PARAMETER, TEMPLATE_CREATE_SQLPAGE_PARAMETER, model );
    }
    
    /**
     * Redirect to the Manage SQLPage Parameter page
     * 
     * @param request the HTTP request
     * @return
     */
    @View( VIEW_REDIRECT_MANAGE_SQLPAGE_PARAMETERS )
    public String getRedirectManageSQLPageParameter( HttpServletRequest request )
    {
        return redirect( request, VIEW_MANAGE_SQLPAGE_PARAMETERS, getMapParameters( ) );
    }
    
    /**
     * Action used for create a new SQLPageParameter
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_SQLPAGE_PARAMETER )
    public String doCreateSQLPageParameter( HttpServletRequest request )
    {
        populate( _sqlPageParameter, request );

        // Check bean constraints
        if ( !validateBean( _sqlPageParameter, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_SQLPAGE_PARAMETER );
        }
        
        // Check if the key for the parameter doesn't already exist for the SQL Page
        if ( SQLPageParameterHome.findSQLPageParameterByName( _sqlPageParameter.getKey( ), _sqlPage.getId( ) ) != null )
        {
            addError( ERROR_PARAMETER_KEY_ALREADY_EXIST, getLocale( ) );
            return redirectView( request, VIEW_CREATE_SQLPAGE_PARAMETER );
        }

        SQLPageParameterHome.create( _sqlPageParameter );
        addInfo( INFO_SQLPAGE_PARAMETER_CREATED, getLocale( ) );

        return redirect( request, VIEW_MANAGE_SQLPAGE_PARAMETERS, getMapParameters( ) );
    }
    
    /**
     * Manages the removal form of a sqlpage parameter
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_SQLPAGE_PARAMETER )
    public String getConfirmRemoveSQLPage( HttpServletRequest request )
    {
        int nIdSQLPageParameter = Integer.parseInt( request.getParameter( SQLPageConstants.PARAMETER_ID_SQLPAGE_PARAMETER ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_SQLPAGE_PARAMETER ) );
        url.addParameter( SQLPageConstants.PARAMETER_ID_SQLPAGE_PARAMETER, nIdSQLPageParameter );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_SQLPAGE_PARAMETER, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a sqlpage parameter
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage sqlpage parameters
     */
    @Action( ACTION_REMOVE_SQLPAGE_PARAMETER )
    public String doRemoveSQLPage( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( SQLPageConstants.PARAMETER_ID_SQLPAGE_PARAMETER ) );
        SQLPageParameterHome.remove( nId );
        
        addInfo( INFO_SQLPAGE_PARAMETER_REMOVED, getLocale( ) );
        
        return redirect( request, VIEW_MANAGE_SQLPAGE_PARAMETERS, getMapParameters( ) );
    }
    
    /**
     * Returns the form to update info about a sqlpage parameter
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_SQLPAGE_PARAMETER )
    public String getModifySQLPageParameter( HttpServletRequest request )
    {
        try
        {
            int nId = Integer.parseInt( request.getParameter( SQLPageConstants.PARAMETER_ID_SQLPAGE_PARAMETER ) );
            _sqlPageParameter = SQLPageParameterHome.findByPrimaryKey( nId );
        
            if ( _sqlPageParameter != null )
            {
                Map<String, Object> model = getModel( );
                model.put( SQLPageConstants.MARK_SQLPAGE_ID, _sqlPage.getId( ) );
                model.put( SQLPageConstants.MARK_SQLPAGE_PARAMETER, _sqlPageParameter );

                return getPage( PROPERTY_PAGE_TITLE_MODIFY_PARAMETER, TEMPLATE_MODIFY_SQLPAGE_PARAMETER, model );
            }
            else
            {
                // In the case where the sql page parameter has not been found
                addError( ERROR_SQLPAGE_PARAMETER, getLocale( ) );

                return redirect( request, VIEW_MANAGE_SQLPAGES );                
            }
        }
        catch( NumberFormatException e )
        {
            // In the case where the id of the sqlpage is not in the request
            addError( ERROR_SQLPAGE_PARAMETER, getLocale( ) );

            return redirect( request, VIEW_MANAGE_SQLPAGES );
        }
    }
    
    /**
     * Process the change form of a sqlpage parameter
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_SQLPAGE_PARAMETER )
    public String doModifySQLPageParameter( HttpServletRequest request )
    {
        String strActualKey = _sqlPageParameter.getKey( );
        populate( _sqlPageParameter, request );

        // Check constraints
        if ( !validateBean( _sqlPageParameter, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_SQLPAGE_PARAMETER, SQLPageConstants.PARAMETER_ID_SQLPAGE_PARAMETER, _sqlPageParameter.getId( ) );
        }
        
        // Check if the key for the parameter doesn't already exist for the SQL Page
        if ( !_sqlPageParameter.getKey( ).equals( strActualKey ) && SQLPageParameterHome.findSQLPageParameterByName( _sqlPageParameter.getKey( ), _sqlPage.getId( ) ) != null )
        {
            addError( ERROR_PARAMETER_KEY_ALREADY_EXIST, getLocale( ) );
            return redirect( request, VIEW_MODIFY_SQLPAGE_PARAMETER, SQLPageConstants.PARAMETER_ID_SQLPAGE_PARAMETER, _sqlPageParameter.getId( ) );
        }

        // Check if user is authorized to change parameter on the sql page
        if ( !SQLPageService.isAuthorized( _sqlPage, getUser( ) ) )
        {
            addError( MESSAGE_NOT_AUTHORIZED, getLocale( ) );

            return redirect( request, VIEW_MODIFY_SQLPAGE_PARAMETER, SQLPageConstants.PARAMETER_ID_SQLPAGE_PARAMETER, _sqlPageParameter.getId( ) );
        }

        SQLPageParameterHome.update( _sqlPageParameter );
        addInfo( INFO_SQLPAGE_PARAMETER_UPDATED, getLocale( ) );

        return redirect( request, VIEW_MANAGE_SQLPAGE_PARAMETERS, getMapParameters( ) );
    }
    
    /**
     * Return the map of the parameters use for the redirection
     * 
     * @return the map of the parameters used for the redirection
     */
    private Map<String, String> getMapParameters( )
    {
        Map<String, String> mapParameters = new HashMap<String, String>( );
        mapParameters.put( SQLPageConstants.PARAMETER_ID_SQLPAGE, ( (_sqlPage != null ) ? String.valueOf( _sqlPage.getId( ) ) : "" ) );
        
        return mapParameters;
    }
    
}
