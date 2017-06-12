/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * This class provides instances management methods (create, find, ...) for SQLPageParameter objects
 */
public final class SQLPageParameterHome
{
    
    // Static variable pointed at the DAO instance
    private static ISQLPageParameterDAO _dao = SpringContextService.getBean( "sqlpage.sQLPageParameterDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "sqlpage" );
    
    /**
     * Private constructor - this class doesn't need to be instantiated
     */
    private SQLPageParameterHome( )
    {
    }

    /**
     * Create an instance of the SQLPageParameter class
     * 
     * @param sqlPageParameter
     *            The instance of the sqlPageParameter which contains the informations to store
     * @return The instance of sqlPageParameter which has been created with its primary key.
     */
    public static SQLPageParameter create( SQLPageParameter sqlPageParameter )
    {
        _dao.insert( sqlPageParameter, _plugin );

        return sqlPageParameter;
    }

    /**
     * Update of the sqlPageParameter which is specified in parameter
     * 
     * @param sqlPageParameter
     *            The instance of the SQLPageParameter which contains the data to store
     * @return The instance of the sqlPageParameter which has been updated
     */
    public static SQLPageParameter update( SQLPageParameter sqlPageParameter )
    {
        _dao.store( sqlPageParameter, _plugin );

        return sqlPageParameter;
    }

    /**
     * Remove the sqlPageParameter whose identifier is specified in parameter
     * 
     * @param nKey
     *            The sqlPageParameter Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }
    
    /**
     * Returns an instance of a sqlPageParameter whose identifier is specified in parameter
     * 
     * @param nKey
     *            The sqlPageParameter primary key
     * @return an instance of sqlPageParameter
     */
    public static SQLPageParameter findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    
    /**
     * Load the data of all the sqlPageParameter objects associated to a SQLPage and returns them in a List
     * 
     * @param nIdPage
     *            The Page ID
     * @return the List which contains the data of all the sqlPageParameter objects associated to the SQLPage
     */
    public static List<SQLPageParameter> getSQLPageParametersList( int nIdPage )
    {
        return _dao.selectSQLPageParametersList( nIdPage, _plugin );
    }
    
    /**
     * Return the SQL Page Parameter with the specified name associated to the SQL Page
     * 
     * @param strParameterKey
     *          The key of the parameter to find
     * @param nIdPage
     *          The identifier of the SQL Page
     * @return the SQL Page Parameter of the SQL Page with the specified key 
     */
    public static SQLPageParameter findSQLPageParameterByName( String strParameterKey, int nIdPage )
    {
        return _dao.selectSQLPageParameterByKey( strParameterKey, nIdPage, _plugin );
    }
}
