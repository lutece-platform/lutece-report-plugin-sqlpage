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
package fr.paris.lutece.plugins.sqlpage.service;

import fr.paris.lutece.plugins.sqlpage.business.SQLFragment;
import fr.paris.lutece.plugins.sqlpage.business.SQLFragmentHome;
import fr.paris.lutece.plugins.sqlpage.business.SQLPage;
import fr.paris.lutece.plugins.sqlpage.business.SQLPageHome;
import fr.paris.lutece.plugins.sqlpage.business.query.ResultSetRow;
import fr.paris.lutece.portal.business.page.Page;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.web.xpages.XPage;
import freemarker.core.ParseException;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * SQLPage Service
 */
public final class SQLPageService
{
    private static final String MOKE_TEMPLATE_NAME = "SQLFragmentTemplate";
    private static final String MARK_ROWS = "rows";

    /** Private constructor */
    private SQLPageService(  )
    {
    }

    /**
     * Build the SQL Page
     * @param strName The page name
     * @param request The HTTP request
     * @return The XPAGE
     */
    public static XPage getSQLPage( String strName, HttpServletRequest request )
    {
        XPage xpage = new XPage(  );
        int nPageId = SQLPageHome.findByName( strName );
        SQLPage page = SQLPageHome.findByPrimaryKey( nPageId );

        if ( page == null )
        {
            return null;
        }

        List<SQLFragment> listFragments = SQLFragmentHome.getSQLFragmentsList( nPageId );
        StringBuilder sbHtml = new StringBuilder(  );

        for ( SQLFragment fragment : listFragments )
        {
            try
            {
                if ( isVisible( request, fragment.getRole(  ) ) )
                {
                    Map<String, Object> model = new HashMap<String, Object>(  );
                    List<ResultSetRow> listResults = SQLService.getQueryResults( fragment.getSqlQuery(  ),
                            fragment.getPool(  ), request.getParameterMap(  ) );
                    model.put( MARK_ROWS, listResults );

                    String strTemplate = fragment.getTemplate(  );
                    sbHtml.append( TemplateService.instance(  )
                                                  .process( "" + fragment.getId(  ), strTemplate,
                            request.getLocale(  ), model ) );
                }
            }
            catch ( TemplateException ex )
            {
                AppLogService.error( "SQLPage - Template error building page : " + ex.getMessage(  ), ex );
            }
            catch ( IOException ex )
            {
                AppLogService.error( "SQLPage - Template error building page : " + ex.getMessage(  ), ex );
            }
        }

        xpage.setContent( sbHtml.toString(  ) );
        xpage.setPathLabel( page.getTitle(  ) );
        xpage.setTitle( page.getTitle(  ) );

        return xpage;
    }

    /**
     * Validate a template content
     *
     * @param strTemplate The template content
     * @param locale The Locale
     * @throws TemplateException if the template is not valid
     * @throws java.io.IOException if the template is not valid
     * @throws freemarker.core.ParseException if the template is not valid
     */
    public static void validateTemplate( String strTemplate, Locale locale )
        throws TemplateException, IOException, ParseException
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        List<ResultSetRow> listResults = SQLService.getMokeResults(  );
        model.put( MARK_ROWS, listResults );
        TemplateService.instance(  ).process( MOKE_TEMPLATE_NAME, strTemplate, locale, model );
    }

    /**
     * Checks if the page is visible for the current user
     * @param request The HTTP request
     * @param strRole The role
     * @return true if the page could be shown to the user
     */
    private static boolean isVisible( HttpServletRequest request, String strRole )
    {
        if ( ( strRole == null ) || strRole.trim(  ).equals( "" ) )
        {
            return true;
        }

        if ( !strRole.equals( Page.ROLE_NONE ) && SecurityService.isAuthenticationEnable(  ) )
        {
            return SecurityService.getInstance(  ).isUserInRole( request, strRole );
        }

        return true;
    }

    /**
     * Checks if the page has an authorized workgroup for a given user
     * @param page The page
     * @param user The user
     * @return True if the user can access to the page
     */
    public static boolean isAuthorized( SQLPage page, AdminUser user )
    {
        return AdminWorkgroupService.isAuthorized( page, user );
    }

    /**
     * Filter the list of pages according user authorization
     * @param user The page
     * @return List of authorized pages
     */
    public static List<SQLPage> getAuthorizedPages( AdminUser user )
    {
        List<SQLPage> listPages = new ArrayList<SQLPage>(  );

        for ( SQLPage page : SQLPageHome.getSQLPagesList(  ) )
        {
            if ( isAuthorized( page, user ) )
            {
                listPages.add( page );
            }
        }

        return listPages;
    }
    /**
     * 
     * @param strName The page name
     * @return String
     */
    public static String getSQLTemplate(String strName){
    	
    	 Map<String, String[]> mapParameters = new HashMap<String, String[]>();
         int nPageId = SQLPageHome.findByName( strName );
         SQLPage page = SQLPageHome.findByPrimaryKey( nPageId );

         if ( page == null )
         {
             return null;
         }

         List<SQLFragment> listFragments = SQLFragmentHome.getSQLFragmentsList( nPageId );
         StringBuilder sbHtml = new StringBuilder(  );

         for ( SQLFragment fragment : listFragments )
         {
             try
             {
                
                 Map<String, Object> model = new HashMap<String, Object>(  );
                 List<ResultSetRow> listResults = SQLService.getQueryResults( fragment.getSqlQuery(  ),
                             fragment.getPool(  ), mapParameters );
                  model.put( MARK_ROWS, listResults );

                  String strTemplate = fragment.getTemplate(  );
                  sbHtml.append( TemplateService.instance(  )
                                                   .process( "" + fragment.getId(  ), strTemplate,
                             new Locale("fr","FR"), model ) );
                
             }
             catch ( TemplateException ex )
             {
                 AppLogService.error( "SQLPage - Template error building page : " + ex.getMessage(  ), ex );
             }
             catch ( IOException ex )
             {
                 AppLogService.error( "SQLPage - Template error building page : " + ex.getMessage(  ), ex );
             }
         }

         return  sbHtml.toString(  ) ;
    }
}
