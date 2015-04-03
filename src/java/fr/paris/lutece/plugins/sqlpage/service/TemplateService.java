/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.sqlpage.service;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import java.util.Locale;
import java.util.Map;


/**
 * Template Service
 */
public class TemplateService
{
    private static TemplateService _singleton;
    private static Configuration _cfg;

    /**
     * Returns the unique instance
     * @return the unique instance
     */
    public static TemplateService instance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new TemplateService(  );
            _cfg = new Configuration(  );
            _cfg.setObjectWrapper( new DefaultObjectWrapper(  ) );
        }

        return _singleton;
    }

    /**
     * Process a template
     * @param strTemplateName The template name
     * @param strTemplateValue The template content
     * @param locale The locale
     * @param model The model
     * @return The processed template
     * @throws TemplateException If an error occurs
     * @throws java.io.IOException If an error occurs
     */
    public String process( String strTemplateName, String strTemplateValue, Locale locale, Map model )
        throws TemplateException, IOException
    {
        Template t = new Template( strTemplateName, new StringReader( strTemplateValue ), _cfg );
        Writer out = new StringWriter(  );
        t.process( model, out );

        return out.toString(  );
    }
}
