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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.sqlpage.business.SQLPage;
import fr.paris.lutece.plugins.sqlpage.business.SQLPageHome;
import fr.paris.lutece.plugins.sqlpage.business.query.SQLQueryException;
import fr.paris.lutece.plugins.sqlpage.service.SQLPageService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * This class provides a simple implementation of an XPage
 */
@Controller( xpageName = "sqlpage", pageTitleI18nKey = "sqlpage.xpage.sqlpage.pageTitle", pagePathI18nKey = "sqlpage.xpage.sqlpage.pagePathLabel" )
public class SQLPageApp extends MVCApplication
{
    private static final String TEMPLATE_XPAGE = "/skin/plugins/sqlpage/sqlpage.html";
    private static final String PARAMETER_SQLPAGE = "sqlpage";
    private static final String MARK_PAGES_LIST = "pages_list";
    private static final String VIEW_HOME = "home";
    private static final long serialVersionUID = 1L;
	private static final String ACTION_DISPLAY_SQLPAGE = "displaysqlpage";

    /**
     * Returns the content of the page sqlpage.
     * 
     * @param request
     *            The HTTP request
     * @return The view
     */
    @View( value = VIEW_HOME, defaultView = true )
    public XPage viewHome( HttpServletRequest request )
    {
        XPage xpage;
        String strName = request.getParameter( PARAMETER_SQLPAGE );

        if ( strName == null )
        {
            xpage = getSQLPagesList( request );
        }
        else
        {
            try
            {
                xpage = SQLPageService.getSQLPage( strName, request );

                if ( xpage == null )
                {
                    xpage = getSQLPagesList( request );
                }
            }
            catch ( SQLQueryException ex )
            {
                // An error occured during the creation of the request
                addError( SQLPageConstants.ERROR_SQLPAGE_REQUEST_CREATION, request.getLocale( ) );
                
                return getSQLPagesList( request );
            }
        }

        return xpage;
    }

    /**
     * Returns the XPage that displays all SQLPages
     * 
     * @param request
     *            The HTTP request
     * @return The XPage
     */
    private XPage getSQLPagesList( HttpServletRequest request )
    {
        List<SQLPage> listPages = SQLPageHome.getSQLPagesList( );

        Map<String, Object> model = getModel( );
        model.put( MARK_PAGES_LIST, listPages );

        return getXPage( TEMPLATE_XPAGE, request.getLocale( ), model );
    }
    
    /**
     * Process the data capture form of a new ticket
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_DISPLAY_SQLPAGE )
    public XPage doDisplaySQLPage( HttpServletRequest request )
    {
        return viewHome(  request );
    }
    
    
}
