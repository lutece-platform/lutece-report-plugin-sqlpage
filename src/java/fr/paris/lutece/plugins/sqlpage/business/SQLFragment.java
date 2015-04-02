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

import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;


/**
 * This is the business class for the object SQLFragment
 */
public class SQLFragment
{
    // Variables declarations 
    private int _nId;
    private int _nIdPage;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlfragment.Template.notEmpty}" )
    private String _strTemplate;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlfragment.SqlQuery.notEmpty}" )
    private String _strSqlQuery;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlfragment.Pool.notEmpty}" )
    @Size( max = 50, message = "#i18n{sqlpage.validation.sqlfragment.Pool.size}" )
    private String _strPool;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlfragment.Title.notEmpty}" )
    @Size( max = 255, message = "#i18n{sqlpage.validation.sqlfragment.Title.size}" )
    private String _strTitle;
    private int _nIdOrder;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlfragment.Role.notEmpty}" )
    @Size( max = 50, message = "#i18n{sqlpage.validation.sqlfragment.Role.size}" )
    private String _strRole;

    /**
     * Returns the Id
     * @return The Id
     */
    public int getId(  )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * @param nId The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the IdPage
     * @return The IdPage
     */
    public int getIdPage(  )
    {
        return _nIdPage;
    }

    /**
     * Sets the IdPage
     * @param nIdPage The IdPage
     */
    public void setIdPage( int nIdPage )
    {
        _nIdPage = nIdPage;
    }

    /**
     * Returns the Template
     * @return The Template
     */
    public String getTemplate(  )
    {
        return _strTemplate;
    }

    /**
     * Sets the Template
     * @param strTemplate The Template
     */
    public void setTemplate( String strTemplate )
    {
        _strTemplate = strTemplate;
    }

    /**
     * Returns the SqlQuery
     * @return The SqlQuery
     */
    public String getSqlQuery(  )
    {
        return _strSqlQuery;
    }

    /**
     * Sets the SqlQuery
     * @param strSqlQuery The SqlQuery
     */
    public void setSqlQuery( String strSqlQuery )
    {
        _strSqlQuery = strSqlQuery;
    }

    /**
     * Returns the Pool
     * @return The Pool
     */
    public String getPool(  )
    {
        return _strPool;
    }

    /**
     * Sets the Pool
     * @param strPool The Pool
     */
    public void setPool( String strPool )
    {
        _strPool = strPool;
    }

    /**
     * Returns the Title
     * @return The Title
     */
    public String getTitle(  )
    {
        return _strTitle;
    }

    /**
     * Sets the Title
     * @param strTitle The Title
     */
    public void setTitle( String strTitle )
    {
        _strTitle = strTitle;
    }

    /**
     * Returns the IdOrder
     * @return The IdOrder
     */
    public int getIdOrder(  )
    {
        return _nIdOrder;
    }

    /**
     * Sets the IdOrder
     * @param nIdOrder The IdOrder
     */
    public void setIdOrder( int nIdOrder )
    {
        _nIdOrder = nIdOrder;
    }

    /**
     * Returns the Role
     * @return The Role
     */
    public String getRole(  )
    {
        return _strRole;
    }

    /**
     * Sets the Role
     * @param strRole The Role
     */
    public void setRole( String strRole )
    {
        _strRole = strRole;
    }
}
