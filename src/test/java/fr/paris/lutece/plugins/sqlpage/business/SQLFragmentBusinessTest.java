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


public class SQLFragmentBusinessTest extends LuteceTestCase
{
    private final static int IDPAGE1 = 1;
    private final static int IDPAGE2 = 2;
    private final static String TEMPLATE1 = "Template1";
    private final static String TEMPLATE2 = "Template2";
    private final static String SQLQUERY1 = "SqlQuery1";
    private final static String SQLQUERY2 = "SqlQuery2";
    private final static String POOL1 = "Pool1";
    private final static String POOL2 = "Pool2";
    private final static String TITLE1 = "Title1";
    private final static String TITLE2 = "Title2";
    private final static int IDORDER1 = 1;
    private final static int IDORDER2 = 2;
    private final static String ROLE1 = "Role1";
    private final static String ROLE2 = "Role2";

    public void testBusiness(  )
    {
        // Initialize an object
        SQLFragment sQLFragment = new SQLFragment(  );
        sQLFragment.setIdPage( IDPAGE1 );
        sQLFragment.setTemplate( TEMPLATE1 );
        sQLFragment.setSqlQuery( SQLQUERY1 );
        sQLFragment.setPool( POOL1 );
        sQLFragment.setTitle( TITLE1 );
        sQLFragment.setIdOrder( IDORDER1 );
        sQLFragment.setRole( ROLE1 );

        // Create test
        SQLFragmentHome.create( sQLFragment );

        SQLFragment sQLFragmentStored = SQLFragmentHome.findByPrimaryKey( sQLFragment.getId(  ) );
        assertEquals( sQLFragmentStored.getIdPage(  ), sQLFragment.getIdPage(  ) );
        assertEquals( sQLFragmentStored.getTemplate(  ), sQLFragment.getTemplate(  ) );
        assertEquals( sQLFragmentStored.getSqlQuery(  ), sQLFragment.getSqlQuery(  ) );
        assertEquals( sQLFragmentStored.getPool(  ), sQLFragment.getPool(  ) );
        assertEquals( sQLFragmentStored.getTitle(  ), sQLFragment.getTitle(  ) );
        assertEquals( sQLFragmentStored.getIdOrder(  ), sQLFragment.getIdOrder(  ) );
        assertEquals( sQLFragmentStored.getRole(  ), sQLFragment.getRole(  ) );

        // Update test
        sQLFragment.setIdPage( IDPAGE2 );
        sQLFragment.setTemplate( TEMPLATE2 );
        sQLFragment.setSqlQuery( SQLQUERY2 );
        sQLFragment.setPool( POOL2 );
        sQLFragment.setTitle( TITLE2 );
        sQLFragment.setIdOrder( IDORDER2 );
        sQLFragment.setRole( ROLE2 );
        SQLFragmentHome.update( sQLFragment );
        sQLFragmentStored = SQLFragmentHome.findByPrimaryKey( sQLFragment.getId(  ) );
        assertEquals( sQLFragmentStored.getIdPage(  ), sQLFragment.getIdPage(  ) );
        assertEquals( sQLFragmentStored.getTemplate(  ), sQLFragment.getTemplate(  ) );
        assertEquals( sQLFragmentStored.getSqlQuery(  ), sQLFragment.getSqlQuery(  ) );
        assertEquals( sQLFragmentStored.getPool(  ), sQLFragment.getPool(  ) );
        assertEquals( sQLFragmentStored.getTitle(  ), sQLFragment.getTitle(  ) );
        assertEquals( sQLFragmentStored.getIdOrder(  ), sQLFragment.getIdOrder(  ) );
        assertEquals( sQLFragmentStored.getRole(  ), sQLFragment.getRole(  ) );

        // List test
        SQLFragmentHome.getSQLFragmentsList(  );

        // Delete test
        SQLFragmentHome.remove( sQLFragment.getId(  ) );
        sQLFragmentStored = SQLFragmentHome.findByPrimaryKey( sQLFragment.getId(  ) );
        assertNull( sQLFragmentStored );
    }
}
