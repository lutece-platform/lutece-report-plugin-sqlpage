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
package fr.paris.lutece.plugins.sqlpage.web;

/**
 * Class which provide constants
 */
public class SQLPageConstants
{
    // Constants
    public static final String DECLARE_PARAM_SYMBOL = ":";
    public static final String INTERROGATION = "?";
    public static final String SPACE = " ";
    public static final String AROBASE = "@";

    // Parameters
    public static final String PARAMETER_ID_SQLPAGE = "id";
    public static final String PARAMETER_SQLPAGE = "sqlpage";
    public static final String PARAMETER_ID_SQLPAGE_PARAMETER = "id_parameter";

    // Markers
    public static final String MARK_SQLPAGE_ID = "id_sqlpage";
    public static final String MARK_SQLPAGE_LIST = "sqlpage_list";
    public static final String MARK_SQLPAGE = "sqlpage";
    public static final String MARK_SQLPAGE_PARAMETER = "sqlpage_parameter";
    public static final String MARK_WORKGROUP_LIST = "workgroup_list";
    public static final String MARK_PAGES_LIST = "pages_list";
    public static final String MARK_PARAMETERS_LIST = "parameters_list";
    
    // Errors
    public static final String ERROR_SQLPAGE_REQUEST_CREATION = "sqlpage.error.sqlpage.requestCreationFailed";

}
