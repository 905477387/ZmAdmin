list
===
SELECT
	c.*,
	dict.NAME AS StatusName 
FROM
	tb_charge c
	LEFT JOIN ( SELECT * FROM blade_dict WHERE `code` = 905 ) dict ON c.`Status` = dict.num

transList
===
SELECT
	c.*,
	dict.NAME AS StatusName 
FROM
	tb_charge_list c
	LEFT JOIN ( SELECT * FROM blade_dict WHERE `code` = 905 ) dict ON c.`Status` = dict.num