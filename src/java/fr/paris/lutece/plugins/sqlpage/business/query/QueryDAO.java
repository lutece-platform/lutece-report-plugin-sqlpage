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
package fr.paris.lutece.plugins.sqlpage.business.query;

import fr.paris.lutece.plugins.sqlpage.web.SQLPageConstants;
import fr.paris.lutece.portal.service.database.PluginConnectionService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Query DAO
 */
public class QueryDAO
{
    // Constants
    private static final String PARAM_BEGIN_PATTERN = "'@";
    private static final String PARAM_END_PATTERN = "@'";

    /**
     * Returns query results
     * 
     * @param strSQL
     *            The query
     * @param connectionService
     *            the connection service
     * @return Query results
     * @throws SQLQueryException
     *             If an error occurs
     */
    public List<ResultSetRow> getQueryResults( String strSQL, PluginConnectionService connectionService ) throws SQLQueryException
    {
        Connection connection = null;
        Statement statement = null;

        List<ResultSetRow> listRow = new ArrayList<ResultSetRow>( );

        try
        {
            connection = connectionService.getConnection( );
            statement = connection.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

            ResultSet resultSet = statement.executeQuery( strSQL );

            listRow = convertResultSetToResultSetRowList( resultSet );

            statement.close( );
            statement = null;
        }
        catch( SQLException ex )
        {
            AppLogService.error( "SQLPage - SQLService Error " + ex.getMessage( ), ex );
            throw new SQLQueryException( ex.getMessage( ), ex );
        }
        finally
        {
            closeStatement( statement );

            connectionService.freeConnection( connection );
        }

        return listRow;
    }

    /**
     * Returns query results
     * 
     * @param strSQL
     *            The parameterized query to execute
     * @param connectionService
     *            The connection service
     * @param mapKeyValueParameters
     *            The map which associated to each parameter key its value
     * @return query results
     * @throws SQLQueryException
     *             If an error occured
     */
    public List<ResultSetRow> getParameterizedQueryResults( String strSQL, PluginConnectionService connectionService, Map<String, String> mapKeyValueParameters )
            throws SQLQueryException
    {
        // Create the map which positioned each parameter as they appear in the query
        Map<Integer, String> mapPositionOrderedParam = buildMapRequestParamPosition( strSQL );

        // Format the query for replace all parameter
        for ( String strKeyParam : mapPositionOrderedParam.values( ) )
        {
            strSQL = strSQL.replaceAll( PARAM_BEGIN_PATTERN + strKeyParam + PARAM_END_PATTERN, SQLPageConstants.INTERROGATION );
        }

        Connection connection = connectionService.getConnection( );
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement( strSQL );

            for ( Entry<Integer, String> entryPositionParamValue : mapPositionOrderedParam.entrySet( ) )
            {
                String strParamKey = entryPositionParamValue.getValue( );
                String strValueParam = StringUtils.EMPTY;
                if ( mapKeyValueParameters.containsKey( strParamKey ) )
                {
                    strValueParam = mapKeyValueParameters.get( strParamKey );
                }
                preparedStatement.setString( entryPositionParamValue.getKey( ), strValueParam );
            }

            return convertResultSetToResultSetRowList( preparedStatement.executeQuery( ) );
        }
        catch( SQLException ex )
        {
            AppLogService.error( "SQLPage - SQLService Error " + ex.getMessage( ), ex );
            throw new SQLQueryException( ex.getMessage( ), ex );
        }
        finally
        {
            QueryDAO.closeStatement( preparedStatement );
            connectionService.freeConnection( connection );
        }
    }

    /**
     * Convert a ResultSet to a List<ResultSetRow>
     * 
     * @param resultSet
     *            The resultSet to convert
     * @return the resultSet converted to a List<ResultSetRow>
     * @throws SQLException
     */
    public static List<ResultSetRow> convertResultSetToResultSetRowList( ResultSet resultSet ) throws SQLException
    {
        List<ResultSetRow> listResultSetRow = new ArrayList<>( );

        if ( resultSet != null )
        {
            ResultSetMetaData rsmd = resultSet.getMetaData( );

            while ( resultSet.next( ) )
            {
                String strValue;
                ResultSetRow row = new ResultSetRow( );

                for ( int i = 1; i <= rsmd.getColumnCount( ); i++ )
                {
                    if ( resultSet.getObject( rsmd.getColumnName( i ) ) != null )
                    {
                        strValue = resultSet.getObject( rsmd.getColumnName( i ) ).toString( );
                    }
                    else
                    {
                        strValue = " ";
                    }

                    row.addCol( strValue );
                }

                listResultSetRow.add( row );
            }
        }
        return listResultSetRow;
    }

    /**
     * Manage the close of a Statement object
     * 
     * @param statement
     *            The statement to close
     * @throws AppException
     *             If an error occured
     */
    public static void closeStatement( Statement statement ) throws AppException
    {
        try
        {
            if ( statement != null )
            {
                statement.close( );
            }
        }
        catch( SQLException e )
        {
            throw new AppException( "SQL Error executing command : " + e.toString( ) );
        }
    }

    /**
     * Build the map which order all parameter as they appear in the query
     * 
     * @param strSQL
     *            The request to analyze
     * @return the map with all parameter and their position in the query
     */
    private static Map<Integer, String> buildMapRequestParamPosition( String strSQL )
    {
        Map<Integer, String> mapPositionOrderedParam = new HashMap<>( );

        Pattern patternGlobal = Pattern.compile( PARAM_BEGIN_PATTERN );
        Matcher matcherGlobal = patternGlobal.matcher( strSQL );
        int positionCurrentParam = 1;

        while ( matcherGlobal.find( ) )
        {
            int indexEnd = matcherGlobal.end( );
            char [ ] charArray = strSQL.toCharArray( );
            StringBuilder currentParamName = new StringBuilder( );
            for ( int indice = indexEnd; indice < strSQL.length( ); indice++ )
            {
                char currentChar = charArray [indice];
                if ( SQLPageConstants.AROBASE.equals( String.valueOf( currentChar ) ) )
                {
                    break;
                }
                currentParamName.append( currentChar );
            }
            String currentParamKey = currentParamName.toString( );
            mapPositionOrderedParam.put( positionCurrentParam, currentParamKey );
            positionCurrentParam++;
        }

        return mapPositionOrderedParam;
    }
}
