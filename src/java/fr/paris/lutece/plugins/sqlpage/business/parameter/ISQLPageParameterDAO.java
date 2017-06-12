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

/**
 * ISQLPageParameterDAO Interface
 */
public interface ISQLPageParameterDAO
{
    /**
     * Insert a new record in the table.
     * 
     * @param sqlPageParameter
     *            instance of the SQLPageParameter object to insert
     * @param plugin
     *            the Plugin
     */
    void insert( SQLPageParameter sqlPageParameter, Plugin plugin );

    /**
     * Update the record in the table
     * 
     * @param sqlPageParameter
     *            the reference of the SQLPageParameter
     * @param plugin
     *            the Plugin
     */
    void store( SQLPageParameter sqlPageParameter, Plugin plugin );

    /**
     * Delete a record from the table
     * 
     * @param nId
     *            The identifier of the SQLPageParameter to delete
     * @param plugin
     *            the Plugin
     */
    void delete( int nId, Plugin plugin );
    
    /**
     * Load the data from the table
     * 
     * @param nId
     *            The identifier of the sqlPageParameter
     * @param plugin
     *            the Plugin
     * @return The instance of the sqlPageParameter
     */
    SQLPageParameter load( int nId, Plugin plugin );
    
    /**
     * Load the data of all the sqlPageParameter objects associated to a SQLPage
     * 
     * @param nIdPage
     *             The identifier of the SQLPage
     * @param plugin
     *             the Plugin
     * @return The List which contains the data of all the sqlPageParameter objects
     */
    List<SQLPageParameter> selectSQLPageParametersList( int nIdPage, Plugin plugin );
    
    /**
     * Select the parameter associated to a SQL Page by its name
     * 
     * @param strParameterKey
     *              The key of the parameter to find
     * @param nIdPage
     *              The identifier of the SQLPage where to search for the parameter
     * @param plugin
     *             the Plugin          
     * @return The parameter with the specified name associated to the SQLPage
     */
    SQLPageParameter selectSQLPageParameterByKey( String strParameterKey, int nIdPage, Plugin plugin );
}
