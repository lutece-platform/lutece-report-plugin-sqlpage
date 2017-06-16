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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.sqlpage.business.query.QueryDAO;
import fr.paris.lutece.plugins.sqlpage.business.query.ResultSetRow;
import fr.paris.lutece.plugins.sqlpage.business.query.SQLQueryException;
import fr.paris.lutece.portal.service.database.AppConnectionService;
import fr.paris.lutece.portal.service.database.PluginConnectionService;

/**
 * SQL Service
 */
public final class SQLService
{
    private static final String PARAMETER_PREFIX = "param";
    private static QueryDAO _dao = new QueryDAO( );
    private static Map _mapConnectionServices = new HashMap( );

    /**
     * Private constructor
     */
    private SQLService( )
    {
    }

    /**
     * Build the model that contains data results
     * 
     * @param strSQL
     *            The query
     * @param strPool
     *            The connection pool
     * @param mapParameters
     *            Parameters of the request that can be variables of the query
     * @param listString
     *            The list of all key of SQLPageParameters associated to the SQLPage
     * @return The results
     * @throws SQLException 
     * @throws SQLQueryException 
     */
    public static List<ResultSetRow> getQueryResults( String strSQL, String strPool, Map<String, String> mapKeyValueParameters ) throws SQLQueryException
    {   
        PluginConnectionService connectionService = getConnectionService( strPool );
       
        if ( mapKeyValueParameters != null )
        {   
            return _dao.getParameterizedQueryResults( strSQL, connectionService, mapKeyValueParameters );
        }
        
        return _dao.getQueryResults( strSQL, connectionService );
    }
    
    /**
     * Checks if the SQL query is valid
     * 
     * @param strSQL
     *            The SQL query
     * @param strPool
     *            The connection pool
     * @throws SQLQueryException
     *             if the query is not valid
     */
    public static void validateSQL( String strSQL, String strPool ) throws SQLQueryException
    {
        String strQuery = buildQueryToCheck( strSQL );
        _dao.getQueryResults( strQuery, getConnectionService( strPool ) );
    }

    /**
     * Returns moke results
     *
     * @return results
     */
    public static List<ResultSetRow> getMokeResults( )
    {
        List<ResultSetRow> listRows = new ArrayList<ResultSetRow>( );

        for ( int i = 0; i < 20; i++ )
        {
            ResultSetRow row = new ResultSetRow( );

            for ( int j = 0; j < 20; j++ )
            {
                row.addCol( "" + j );
            }

            listRows.add( row );
        }

        return listRows;
    }

    /**
     * Get a connection service corresponding to the given poolname. If the pool name is empty, the default connection service of the current plugin is returned
     *
     * @return A Connection Service
     * @param strPoolName
     *            The Poolname
     */
    public static PluginConnectionService getConnectionService( String strPoolName )
    {
        PluginConnectionService connectionService;

        if ( ( strPoolName != null ) && !strPoolName.equals( "" ) )
        {
            if ( _mapConnectionServices.containsKey( strPoolName ) )
            {
                connectionService = (PluginConnectionService) _mapConnectionServices.get( strPoolName );
            }
            else
            {
                connectionService = new PluginConnectionService( strPoolName );
                _mapConnectionServices.put( strPoolName, connectionService );
            }
        }
        else
        {
            connectionService = AppConnectionService.getDefaultConnectionService( );
        }

        return connectionService;
    }

    /**
     * Build a query with fake parameters values
     * 
     * @param strSQL
     *            The SQL query
     * @return The query
     */
    private static String buildQueryToCheck( String strSQL )
    {
        // remove bookmarks from the SQL request
        return strSQL.replaceAll( buildBookmark( PARAMETER_PREFIX, "(.*)" ), "1" );
    }
    
    /**
     * Build a parameter bookmark
     * 
     * @param strPrefix
     *            The prefix
     * @param strParamName
     *            The name of the parameter
     * @return The bookmark
     */
    private static String buildBookmark( String strPrefix, String strParamName )
    {
        StringBuilder sbBookmark = new StringBuilder( );
        
        sbBookmark.append( '@' );        
        if ( StringUtils.isNotBlank( strPrefix ) )
        {
            sbBookmark.append( strPrefix );
        }
        sbBookmark.append( strParamName );
        sbBookmark.append( '@' );

        return sbBookmark.toString( );
    }
}
