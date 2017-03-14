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
 * This class provides Data Access methods for SQLFragment objects
 */
public final class SQLFragmentDAO implements ISQLFragmentDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_sqlfragment ) FROM sqlpage_fragment";
    private static final String SQL_QUERY_SELECT = "SELECT id_sqlfragment, id_page, template, sql_query, pool, title, id_order, role FROM sqlpage_fragment WHERE id_sqlfragment = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sqlpage_fragment ( id_sqlfragment, id_page, template, sql_query, pool, title, id_order, role ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sqlpage_fragment WHERE id_sqlfragment = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sqlpage_fragment SET id_sqlfragment = ?, id_page = ?, template = ?, sql_query = ?, pool = ?, title = ?, id_order = ?, role = ? WHERE id_sqlfragment = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_sqlfragment, id_page, template, sql_query, pool, title, id_order, role FROM sqlpage_fragment WHERE id_page = ? ORDER BY id_order";
    private static final String SQL_QUERY_REORDER_FRAGMENTS = "UPDATE sqlpage_fragment SET id_order = ? WHERE id_sqlfragment = ? ";

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
    public void insert( SQLFragment sQLFragment, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        sQLFragment.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, sQLFragment.getId( ) );
        daoUtil.setInt( 2, sQLFragment.getIdPage( ) );
        daoUtil.setString( 3, sQLFragment.getTemplate( ) );
        daoUtil.setString( 4, sQLFragment.getSqlQuery( ) );
        daoUtil.setString( 5, sQLFragment.getPool( ) );
        daoUtil.setString( 6, sQLFragment.getTitle( ) );
        daoUtil.setInt( 7, sQLFragment.getIdOrder( ) );
        daoUtil.setString( 8, sQLFragment.getRole( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public SQLFragment load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        SQLFragment sQLFragment = null;

        if ( daoUtil.next( ) )
        {
            sQLFragment = new SQLFragment( );
            sQLFragment.setId( daoUtil.getInt( 1 ) );
            sQLFragment.setIdPage( daoUtil.getInt( 2 ) );
            sQLFragment.setTemplate( daoUtil.getString( 3 ) );
            sQLFragment.setSqlQuery( daoUtil.getString( 4 ) );
            sQLFragment.setPool( daoUtil.getString( 5 ) );
            sQLFragment.setTitle( daoUtil.getString( 6 ) );
            sQLFragment.setIdOrder( daoUtil.getInt( 7 ) );
            sQLFragment.setRole( daoUtil.getString( 8 ) );
        }

        daoUtil.free( );

        return sQLFragment;
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
    public void store( SQLFragment sQLFragment, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, sQLFragment.getId( ) );
        daoUtil.setInt( 2, sQLFragment.getIdPage( ) );
        daoUtil.setString( 3, sQLFragment.getTemplate( ) );
        daoUtil.setString( 4, sQLFragment.getSqlQuery( ) );
        daoUtil.setString( 5, sQLFragment.getPool( ) );
        daoUtil.setString( 6, sQLFragment.getTitle( ) );
        daoUtil.setInt( 7, sQLFragment.getIdOrder( ) );
        daoUtil.setString( 8, sQLFragment.getRole( ) );
        daoUtil.setInt( 9, sQLFragment.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SQLFragment> selectSQLFragmentsList( int nIdPage, Plugin plugin )
    {
        List<SQLFragment> sQLFragmentList = new ArrayList<SQLFragment>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.setInt( 1, nIdPage );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            SQLFragment sQLFragment = new SQLFragment( );

            sQLFragment.setId( daoUtil.getInt( 1 ) );
            sQLFragment.setIdPage( daoUtil.getInt( 2 ) );
            sQLFragment.setTemplate( daoUtil.getString( 3 ) );
            sQLFragment.setSqlQuery( daoUtil.getString( 4 ) );
            sQLFragment.setPool( daoUtil.getString( 5 ) );
            sQLFragment.setTitle( daoUtil.getString( 6 ) );
            sQLFragment.setIdOrder( daoUtil.getInt( 7 ) );
            sQLFragment.setRole( daoUtil.getString( 8 ) );

            sQLFragmentList.add( sQLFragment );
        }

        daoUtil.free( );

        return sQLFragmentList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void swapFragmentsOrder( SQLFragment fragment1, SQLFragment fragment2, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_REORDER_FRAGMENTS, plugin );
        daoUtil.setInt( 1, fragment2.getIdOrder( ) );
        daoUtil.setInt( 2, fragment1.getId( ) );
        daoUtil.executeUpdate( );
        daoUtil.setInt( 1, fragment1.getIdOrder( ) );
        daoUtil.setInt( 2, fragment2.getId( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
