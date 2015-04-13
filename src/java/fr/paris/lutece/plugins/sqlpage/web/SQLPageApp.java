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
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


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

    /**
     * Returns the content of the page sqlpage.
     * @param request The HTTP request
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
            xpage = SQLPageService.getSQLPage( strName, request );

            if ( xpage == null )
            {
                xpage = getSQLPagesList( request );
            }
        }

        return xpage;
    }

    /**
     * Returns the XPage that displays all SQLPages
     * @param request The HTTP request
     * @return The XPage
     */
    private XPage getSQLPagesList( HttpServletRequest request )
    {
        List<SQLPage> listPages = SQLPageHome.getSQLPagesList(  );

        Map model = getModel(  );
        model.put( MARK_PAGES_LIST, listPages );

        return getXPage( TEMPLATE_XPAGE, request.getLocale(  ), model );
    }
}
