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

import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupResource;
import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * This is the business class for the object SQLPage
 */
public class SQLPage implements AdminWorkgroupResource , Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Variables declarations 
    private int _nId;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlpage.ParamName.notEmpty}" )
    @Size( max = 50, message = "#i18n{sqlpage.validation.sqlpage.ParamName.size}" )
    @Pattern( regexp = "[a-z]*", message = "#i18n{sqlpage.validation.sqlpage.ParamName.pattern}" )
    private String _strParamName;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlpage.Title.notEmpty}" )
    @Size( max = 255, message = "#i18n{sqlpage.validation.sqlpage.Title.size}" )
    private String _strTitle;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlpage.Description.notEmpty}" )
    @Size( max = 255, message = "#i18n{sqlpage.validation.sqlpage.Description.size}" )
    private String _strDescription;
    @NotEmpty( message = "#i18n{sqlpage.validation.sqlpage.Workgroup.notEmpty}" )
    @Size( max = 50, message = "#i18n{sqlpage.validation.sqlpage.Workgroup.size}" )
    private String _strWorkgroup;

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
     * Returns the Description
     * @return The Description
     */
    public String getDescription(  )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * @param strDescription The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the Workgroup
     * @return The Workgroup
     */
    @Override
    public String getWorkgroup(  )
    {
        return _strWorkgroup;
    }

    /**
     * Sets the Workgroup
     * @param strWorkgroup The Workgroup
     */
    public void setWorkgroup( String strWorkgroup )
    {
        _strWorkgroup = strWorkgroup;
    }

    /**
     * Returns the ParamName
     * @return The ParamName
     */
    public String getParamName(  )
    {
        return _strParamName;
    }

    /**
     * Sets the ParamName
     * @param strParamName The ParamName
     */
    public void setParamName( String strParamName )
    {
        _strParamName = strParamName;
    }
}
