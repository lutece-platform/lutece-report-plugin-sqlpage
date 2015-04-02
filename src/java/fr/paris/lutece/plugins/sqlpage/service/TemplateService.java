/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.sqlpage.service;

import fr.paris.lutece.portal.service.util.AppLogService;
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
 *
 * @author levy
 */
public class TemplateService
{
    private static TemplateService _singleton;
    private static Configuration _cfg;
    
    
    public static TemplateService instance()
    {
        if( _singleton == null )
        {
            _singleton = new TemplateService();
            _cfg = new Configuration();
            _cfg.setObjectWrapper(new DefaultObjectWrapper());
        }
        return _singleton;
    }
    
    public String process( String strTemplateName, String strTemplateValue , Locale locale ,  Map model )
    {
        String strOutput = "";
        try
        {
            Template t = new Template( strTemplateName, new StringReader( strTemplateValue ), _cfg);
            Writer out = new StringWriter();
            t.process(model, out);

            strOutput = out.toString();
        }
        catch (IOException | TemplateException ex)
        {
            AppLogService.error( "SQLPage TemplateService Error : " + ex.getMessage() , ex );
        }
        return strOutput;
        
    }
}
