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

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * This is the business class for the object SQLPageParameter
 */
public class SQLPageParameter implements Serializable
{

    /**
     * Generated serial version ID
     */
    private static final long serialVersionUID = 8728701090834913627L;

    // Variables declarations
    private int _nId;
    private int _nIdPage;
    @NotBlank( message = "#i18n{sqlpage.validation.sqlpageparameter.Key.notBlank}" )
    private String _strKey;
    @NotBlank( message = "#i18n{sqlpage.validation.sqlpageparameter.Name.notBlank}" )
    private String _strName;
    private String _strDefaultValue;
    private String _strHelpMessage;
    
    /**
     * @return the _nId
     */
    public int getId( )
    {
        return _nId;
    }
    
    /**
     * @param _nId the _nId to set
     */
    public void setId( int nId )
    {
        this._nId = nId;
    }
    
    /**
     * @return the _nIdPage
     */
    public int getIdPage( )
    {
        return _nIdPage;
    }
    
    /**
     * @param _nIdPage the _nIdPage to set
     */
    public void setIdPage( int nIdPage )
    {
        this._nIdPage = nIdPage;
    }
    
    /**
     * @return the _strKey
     */
    public String getKey( )
    {
        return _strKey;
    }
    
    /**
     * @param _strKey the _strKey to set
     */
    public void setKey( String strKey )
    {
        this._strKey = strKey;
    }
    
    /**
     * @return the _strName
     */
    public String getName( )
    {
        return _strName;
    }
    
    /**
     * @param _strName the _strName to set
     */
    public void setName( String strName )
    {
        this._strName = strName;
    }
    
    /**
     * @return the _strDefaultValue
     */
    public String getDefaultValue( )
    {
        return _strDefaultValue;
    }
    
    /**
     * @param _strDefaultValue the _strDefaultValue to set
     */
    public void setDefaultValue( String strDefaultValue )
    {
        this._strDefaultValue = strDefaultValue;
    }
    
    /**
     * @return the _strHelpMessage
     */
    public String getHelpMessage( )
    {
        return _strHelpMessage;
    }
    
    /**
     * @param _strHelpMessage the _strHelpMessage to set
     */
    public void setHelpMessage( String strHelpMessage )
    {
        this._strHelpMessage = strHelpMessage;
    }
    
}
