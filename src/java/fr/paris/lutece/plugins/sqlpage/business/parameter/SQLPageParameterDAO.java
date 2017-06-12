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
package fr.paris.lutece.plugins.sqlpage.business.parameter;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * This class provides Data Access methods for SQLPageParameter objects
 */
public class SQLPageParameterDAO implements ISQLPageParameterDAO
{
    
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_parameter ) FROM sqlpage_page_parameter";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sqlpage_page_parameter ( id_parameter, id_sqlpage, param_key, param_name, default_value, help_message ) VALUES ( ?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_UPDATE = "UPDATE sqlpage_page_parameter SET id_parameter = ?, id_sqlpage = ?, param_key = ?, param_name = ?, default_value = ?, help_message = ? WHERE id_parameter = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sqlpage_page_parameter WHERE id_parameter = ?";
    private static final String SQL_QUERY_SELECT_ID_PARAMETER = "SELECT id_parameter, id_sqlpage, param_key, param_name, default_value, help_message FROM sqlpage_page_parameter WHERE id_parameter = ?";
    private static final String SQL_QUERY_SELECTALL_BY_PAGE = "SELECT id_parameter, id_sqlpage, param_key, param_name, default_value, help_message FROM sqlpage_page_parameter WHERE id_sqlpage = ?";
    private static final String SQL_QUERY_SELECT_PARAMETER_BY_NAME = "SELECT id_parameter, id_sqlpage, param_key, param_name, default_value, help_message FROM sqlpage_page_parameter WHERE param_key = ? AND id_sqlpage = ?";

    /**
     * Generates a new primary key
     * 
     * @param plugin
     *            The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );

        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );

        return nKey;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public synchronized void insert( SQLPageParameter sqlPageParameter, Plugin plugin )
    {
        int nPrimaryKey = newPrimaryKey( plugin );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        sqlPageParameter.setId( nPrimaryKey );

        daoUtil.setInt( 1, sqlPageParameter.getId( ) );
        daoUtil.setInt( 2, sqlPageParameter.getIdPage( ) );
        daoUtil.setString( 3, sqlPageParameter.getKey( ) );
        daoUtil.setString( 4, sqlPageParameter.getName( ) );
        daoUtil.setString( 5, sqlPageParameter.getDefaultValue( ) );
        daoUtil.setString( 6, sqlPageParameter.getHelpMessage( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( SQLPageParameter sqlPageParameter, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, sqlPageParameter.getId( ) );
        daoUtil.setInt( 2, sqlPageParameter.getIdPage( ) );
        daoUtil.setString( 3, sqlPageParameter.getKey( ) );
        daoUtil.setString( 4, sqlPageParameter.getName( ) );
        daoUtil.setString( 5, sqlPageParameter.getDefaultValue( ) );
        daoUtil.setString( 6, sqlPageParameter.getHelpMessage( ) );
        daoUtil.setInt( 7, sqlPageParameter.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public SQLPageParameter load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ID_PARAMETER, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery( );

        SQLPageParameter sqlPageParameter = null;

        if ( daoUtil.next( ) )
        {
            sqlPageParameter = new SQLPageParameter( );
            sqlPageParameter.setId( daoUtil.getInt( 1 ) );
            sqlPageParameter.setIdPage( daoUtil.getInt( 2 ) );
            sqlPageParameter.setKey( daoUtil.getString( 3 ) );
            sqlPageParameter.setName( daoUtil.getString( 4 ) );
            sqlPageParameter.setDefaultValue( daoUtil.getString( 5 ) );
            sqlPageParameter.setHelpMessage( daoUtil.getString( 6 ) );
        }

        daoUtil.free( );

        return sqlPageParameter;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SQLPageParameter> selectSQLPageParametersList( int nIdPage, Plugin plugin )
    {
        List<SQLPageParameter> listSQLPageParameters = new ArrayList<SQLPageParameter>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_PAGE, plugin );
        daoUtil.setInt( 1, nIdPage );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            SQLPageParameter sqlPageParameter = new SQLPageParameter( );

            sqlPageParameter.setId( daoUtil.getInt( 1 ) );
            sqlPageParameter.setIdPage( daoUtil.getInt( 2 ) );
            sqlPageParameter.setKey( daoUtil.getString( 3 ) );
            sqlPageParameter.setName( daoUtil.getString( 4 ) );
            sqlPageParameter.setDefaultValue( daoUtil.getString( 5 ) );
            sqlPageParameter.setHelpMessage( daoUtil.getString( 6 ) );

            listSQLPageParameters.add( sqlPageParameter );
        }

        daoUtil.free( );

        return listSQLPageParameters;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public SQLPageParameter selectSQLPageParameterByKey( String strParameterKey, int nIdPage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PARAMETER_BY_NAME, plugin );
        daoUtil.setString( 1, strParameterKey );
        daoUtil.setInt( 2, nIdPage );
        daoUtil.executeQuery( );

        SQLPageParameter sqlPageParameter = null;

        if ( daoUtil.next( ) )
        {
            sqlPageParameter = new SQLPageParameter( );
            sqlPageParameter.setId( daoUtil.getInt( 1 ) );
            sqlPageParameter.setIdPage( daoUtil.getInt( 2 ) );
            sqlPageParameter.setKey( daoUtil.getString( 3 ) );
            sqlPageParameter.setName( daoUtil.getString( 4 ) );
            sqlPageParameter.setDefaultValue( daoUtil.getString( 5 ) );
            sqlPageParameter.setHelpMessage( daoUtil.getString( 6 ) );
        }

        daoUtil.free( );

        return sqlPageParameter;
    }

}
