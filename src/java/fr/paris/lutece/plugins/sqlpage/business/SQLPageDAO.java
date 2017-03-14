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
package fr.paris.lutece.plugins.sqlpage.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for SQLPage objects
 */
public final class SQLPageDAO implements ISQLPageDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_sqlpage ) FROM sqlpage_page";
    private static final String SQL_QUERY_SELECT = "SELECT id_sqlpage, title, description, workgroup, param_name FROM sqlpage_page WHERE id_sqlpage = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sqlpage_page ( id_sqlpage, title, description, workgroup, param_name ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sqlpage_page WHERE id_sqlpage = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sqlpage_page SET id_sqlpage = ?, title = ?, description = ?, workgroup = ?, param_name = ? WHERE id_sqlpage = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_sqlpage, title, description, workgroup, param_name FROM sqlpage_page";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_sqlpage FROM sqlpage_page";
    private static final String SQL_QUERY_SELECT_BY_NAME = "SELECT id_sqlpage FROM sqlpage_page WHERE param_name = ?";

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
    public void insert( SQLPage sQLPage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        sQLPage.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, sQLPage.getId( ) );
        daoUtil.setString( 2, sQLPage.getTitle( ) );
        daoUtil.setString( 3, sQLPage.getDescription( ) );
        daoUtil.setString( 4, sQLPage.getWorkgroup( ) );
        daoUtil.setString( 5, sQLPage.getParamName( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public SQLPage load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        SQLPage sQLPage = null;

        if ( daoUtil.next( ) )
        {
            sQLPage = new SQLPage( );
            sQLPage.setId( daoUtil.getInt( 1 ) );
            sQLPage.setTitle( daoUtil.getString( 2 ) );
            sQLPage.setDescription( daoUtil.getString( 3 ) );
            sQLPage.setWorkgroup( daoUtil.getString( 4 ) );
            sQLPage.setParamName( daoUtil.getString( 5 ) );
        }

        daoUtil.free( );

        return sQLPage;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( SQLPage sQLPage, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, sQLPage.getId( ) );
        daoUtil.setString( 2, sQLPage.getTitle( ) );
        daoUtil.setString( 3, sQLPage.getDescription( ) );
        daoUtil.setString( 4, sQLPage.getWorkgroup( ) );
        daoUtil.setString( 5, sQLPage.getParamName( ) );
        daoUtil.setInt( 6, sQLPage.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SQLPage> selectSQLPagesList( Plugin plugin )
    {
        List<SQLPage> sQLPageList = new ArrayList<SQLPage>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            SQLPage sQLPage = new SQLPage( );

            sQLPage.setId( daoUtil.getInt( 1 ) );
            sQLPage.setTitle( daoUtil.getString( 2 ) );
            sQLPage.setDescription( daoUtil.getString( 3 ) );
            sQLPage.setWorkgroup( daoUtil.getString( 4 ) );
            sQLPage.setParamName( daoUtil.getString( 5 ) );

            sQLPageList.add( sQLPage );
        }

        daoUtil.free( );

        return sQLPageList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdSQLPagesList( Plugin plugin )
    {
        List<Integer> sQLPageList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            sQLPageList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );

        return sQLPageList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int selectByName( String strName, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_NAME, plugin );
        daoUtil.setString( 1, strName );
        daoUtil.executeQuery( );

        int nKey = -1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 );
        }

        daoUtil.free( );

        return nKey;
    }
}
