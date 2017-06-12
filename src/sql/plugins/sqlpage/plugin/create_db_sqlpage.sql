
--
-- Structure for table sqlpage_page
--

DROP TABLE IF EXISTS sqlpage_page;
CREATE TABLE sqlpage_page (
id_sqlpage int(6) NOT NULL,
param_name varchar(50) NOT NULL default '',
title varchar(255) NOT NULL default '',
description varchar(255) NOT NULL default '',
workgroup varchar(50) NOT NULL default '',
PRIMARY KEY (id_sqlpage)
);

--
-- Structure for table sqlpage_fragment
--

DROP TABLE IF EXISTS sqlpage_fragment;
CREATE TABLE sqlpage_fragment (
id_sqlfragment int(6) NOT NULL,
id_page int(11) NOT NULL default '0',
template long varchar NULL ,
sql_query long varchar NULL ,
pool varchar(50) NOT NULL default '',
title varchar(255) NOT NULL default '',
id_order int(11) NOT NULL default '0',
role varchar(50) NOT NULL default '',
PRIMARY KEY (id_sqlfragment)
);

-- --------------------------------------
-- Add the sqlpage_page_parameter table
-- --------------------------------------

DROP TABLE IF EXISTS sqlpage_page_parameter;
CREATE TABLE sqlpage_page_parameter (
	id_parameter int(6) NOT NULL,
	id_sqlpage int(6) NOT NULL,
	param_key varchar(50) NOT NULL,
	param_name varchar(50) NOT NULL,
	default_value varchar(255),
	help_message varchar(255),
	PRIMARY KEY (id_parameter)
);
