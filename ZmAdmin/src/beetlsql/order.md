list
===
SELECT
	o.*,
	c.card_name,
	d.NAME AS status_name,
	u.`name` as charger_name,
	dept.simplename as charger_station 
FROM
	tb_order o
	LEFT JOIN tb_recharge_card c ON o.card_id = c.id
	LEFT JOIN ( SELECT * FROM blade_dict WHERE CODE = 909 ) d ON o.STATUS = d.num
	LEFT JOIN blade_user u ON o.charger = u.id
	LEFT JOIN blade_dept dept ON u.deptid = dept.id 
WHERE
	( o.STATUS = 3 OR o.STATUS = 2 ) 
	AND o.charge_id IS NOT NULL

findOne
===
select * from tb_order where id = #{id}