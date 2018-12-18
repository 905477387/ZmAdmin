list
===
SELECT
	c.*,
	dict.NAME AS StatusName 
FROM
	tb_charge c
	LEFT JOIN ( SELECT * FROM blade_dict WHERE `code` = 905 ) dict ON c.`Status` = dict.num
WHERE IsDeleted = 0

transList
===
SELECT
	c.*,
	dict.NAME AS StatusName 
FROM
	tb_charge_list c
	LEFT JOIN ( SELECT * FROM blade_dict WHERE `code` = 905 ) dict ON c.`Status` = dict.num
WHERE IsDeleted = 0

findOne
===
select * from tb_charge where id = #{id}

year
===
SELECT YEAR
	( CreateTime ) AS ID,
	YEAR ( CreateTime ) AS TEXT 
FROM
	tb_charge 
GROUP BY
	YEAR ( CreateTime )
	
month
===
SELECT
	1 AS ID,
	1 AS TEXT UNION ALL
SELECT
	2 AS ID,
	2 AS TEXT UNION ALL
SELECT
	3 AS ID,
	3 AS TEXT UNION ALL
SELECT
	4 AS ID,
	4 AS TEXT UNION ALL
SELECT
	5 AS ID,
	5 AS TEXT UNION ALL
SELECT
	6 AS ID,
	6 AS TEXT UNION ALL
SELECT
	7 AS ID,
	7 AS TEXT UNION ALL
SELECT
	8 AS ID,
	8 AS TEXT UNION ALL
SELECT
	9 AS ID,
	9 AS TEXT UNION ALL
SELECT
	10 AS ID,
	10 AS TEXT UNION ALL
SELECT
	11 AS ID,
	11 AS TEXT UNION ALL
SELECT
	12 AS ID,
	12 AS TEXT