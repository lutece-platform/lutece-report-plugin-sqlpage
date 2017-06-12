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