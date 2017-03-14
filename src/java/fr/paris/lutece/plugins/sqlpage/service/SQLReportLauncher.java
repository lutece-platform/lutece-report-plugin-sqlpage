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

import fr.paris.lutece.plugins.reportlauncher.business.dto.PageDTO;
import fr.paris.lutece.plugins.sqlpage.business.SQLPage;
import fr.paris.lutece.plugns.reportlauncher.service.IReportLauncherService;
import fr.paris.lutece.portal.business.user.AdminUser;

import java.util.ArrayList;
import java.util.List;

public class SQLReportLauncher implements IReportLauncherService
{
    public static final String URL_BO_SQLPAGE = "jsp/admin/plugins/sqlpage/ManageSQLPages.jsp?view=showSQLPage&sqlpage=";
    public static final String URL_FO_SQLPAGE = "jsp/site/Portal.jsp?page=sqlpage&sqlpage=";

    @Override
    public List<PageDTO> getPage( AdminUser user )
    {
        List<SQLPage> listSQLPages = (List<SQLPage>) SQLPageService.getAuthorizedPages( user );
        List<PageDTO> listPageDTO = new ArrayList<PageDTO>( );

        for ( SQLPage sqlPage : listSQLPages )
        {
            PageDTO pagedto = new PageDTO( );
            pagedto.setName( sqlPage.getTitle( ) );
            pagedto.setValue( sqlPage.getParamName( ) );

            listPageDTO.add( pagedto );
        }

        return listPageDTO;
    }

    @Override
    public String getBOUrl( )
    {
        return URL_BO_SQLPAGE;
    }

    @Override
    public String getFOUrl( )
    {
        return URL_FO_SQLPAGE;
    }
}
