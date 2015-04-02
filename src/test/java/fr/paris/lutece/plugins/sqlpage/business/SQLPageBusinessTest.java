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

import fr.paris.lutece.test.LuteceTestCase;


public class SQLPageBusinessTest extends LuteceTestCase
{
    private final static String TITLE1 = "Title1";
    private final static String TITLE2 = "Title2";
    private final static String DESCRIPTION1 = "Description1";
    private final static String DESCRIPTION2 = "Description2";
    private final static String WORKGROUP1 = "Workgroup1";
    private final static String WORKGROUP2 = "Workgroup2";

    public void testBusiness(  )
    {
        // Initialize an object
        SQLPage sQLPage = new SQLPage(  );
        sQLPage.setTitle( TITLE1 );
        sQLPage.setDescription( DESCRIPTION1 );
        sQLPage.setWorkgroup( WORKGROUP1 );

        // Create test
        SQLPageHome.create( sQLPage );

        SQLPage sQLPageStored = SQLPageHome.findByPrimaryKey( sQLPage.getId(  ) );
        assertEquals( sQLPageStored.getTitle(  ), sQLPage.getTitle(  ) );
        assertEquals( sQLPageStored.getDescription(  ), sQLPage.getDescription(  ) );
        assertEquals( sQLPageStored.getWorkgroup(  ), sQLPage.getWorkgroup(  ) );

        // Update test
        sQLPage.setTitle( TITLE2 );
        sQLPage.setDescription( DESCRIPTION2 );
        sQLPage.setWorkgroup( WORKGROUP2 );
        SQLPageHome.update( sQLPage );
        sQLPageStored = SQLPageHome.findByPrimaryKey( sQLPage.getId(  ) );
        assertEquals( sQLPageStored.getTitle(  ), sQLPage.getTitle(  ) );
        assertEquals( sQLPageStored.getDescription(  ), sQLPage.getDescription(  ) );
        assertEquals( sQLPageStored.getWorkgroup(  ), sQLPage.getWorkgroup(  ) );

        // List test
        SQLPageHome.getSQLPagesList(  );

        // Delete test
        SQLPageHome.remove( sQLPage.getId(  ) );
        sQLPageStored = SQLPageHome.findByPrimaryKey( sQLPage.getId(  ) );
        assertNull( sQLPageStored );
    }
}
