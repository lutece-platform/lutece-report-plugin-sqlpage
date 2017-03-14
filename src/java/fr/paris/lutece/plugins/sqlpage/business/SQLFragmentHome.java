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
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for SQLFragment objects
 */
public final class SQLFragmentHome
{
    // Static variable pointed at the DAO instance
    private static ISQLFragmentDAO _dao = SpringContextService.getBean( "sqlpage.sQLFragmentDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "sqlpage" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private SQLFragmentHome( )
    {
    }

    /**
     * Create an instance of the sQLFragment class
     * 
     * @param sQLFragment
     *            The instance of the SQLFragment which contains the informations to store
     * @return The instance of sQLFragment which has been created with its primary key.
     */
    public static SQLFragment create( SQLFragment sQLFragment )
    {
        _dao.insert( sQLFragment, _plugin );

        return sQLFragment;
    }

    /**
     * Update of the sQLFragment which is specified in parameter
     * 
     * @param sQLFragment
     *            The instance of the SQLFragment which contains the data to store
     * @return The instance of the sQLFragment which has been updated
     */
    public static SQLFragment update( SQLFragment sQLFragment )
    {
        _dao.store( sQLFragment, _plugin );

        return sQLFragment;
    }

    /**
     * Remove the sQLFragment whose identifier is specified in parameter
     * 
     * @param nKey
     *            The sQLFragment Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a sQLFragment whose identifier is specified in parameter
     * 
     * @param nKey
     *            The sQLFragment primary key
     * @return an instance of SQLFragment
     */
    public static SQLFragment findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the sQLFragment objects and returns them in form of a List
     * 
     * @param nIdPage
     *            The Page ID
     * @return the List which contains the data of all the sQLFragment objects
     */
    public static List<SQLFragment> getSQLFragmentsList( int nIdPage )
    {
        return _dao.selectSQLFragmentsList( nIdPage, _plugin );
    }

    /**
     * Reorder fragments
     * 
     * @param previous
     *            the fragment 1
     * @param next
     *            the fragment 2
     */
    public static void swapFragmentsOrder( SQLFragment previous, SQLFragment next )
    {
        if ( ( next.getIdOrder( ) - previous.getIdOrder( ) ) != 1 )
        {
            next.setIdOrder( previous.getIdOrder( ) + 1 );
        }

        _dao.swapFragmentsOrder( previous, next, _plugin );
    }
}
