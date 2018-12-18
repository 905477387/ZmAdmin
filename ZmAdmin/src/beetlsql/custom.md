list
===
select * from tb_custom

findOne
===
select * from tb_custom where id = #{id}

station
===
SELECT
	StationNo,
	StationAbbreviate
FROM
	tb_custom
GROUP BY
	StationNo
	
cust
===
SELECT
	CustNo,
	CustomerName,
	StationNo
FROM
	tb_custom
GROUP BY
	CustNo