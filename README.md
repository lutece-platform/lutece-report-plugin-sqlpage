
#Plugin sqlpage

##Introduction

This plugin lets create pages with SQL statements and Freemarker HTML templates. Each page may contain one or more SQL fragments. Each fragment has its own HTML template.

##Usage

Templates are using [Freemarker Template Engine](http://freemarker.org) syntax.

 **Table sample** 

The query : `SELECT first_name, last_name FROM core_admin_user` 

The template :

```

    <table>
        <thead>
        <tr>
            <th>Firstname</th>
            <th>Lastname</th>
        </tr>
        </thead>
        <tbody>
            <#list rows as row>
                <tr>
                <#list row.cols as col>
                     <td>${col}</td>
                </#list>
                </tr>
            </#list>
        </tbody>
    </table>

```

 **Form sample** 

The query : `SELECT first_name, last_name FROM core_admin_user WHERE id_user = @param1@` 

The template :

```

<#list rows as row>
    <#list row.cols as col>
        <#if col_index == 0><#assign firstname="${col}" /></#if>
        <#if col_index == 1><#assign lastname="${col}" /></#if>
    </#list>
</#list>

<h1>User</h1>
Firstname : ${firstname} <br>
Lastname : ${lastname} <br>                        

```


[Maven documentation and reports](http://dev.lutece.paris.fr/plugins/plugin-sqlpage/)



 *generated by [xdoc2md](https://github.com/lutece-platform/tools-maven-xdoc2md-plugin) - do not edit directly.*