list
===
select * from tb_bank

findOne
===
select * from tb_bank where id = #{id}

combo
===
SELECT
	BankNo as id,
	0 AS "pId",
	BankName AS name,
	BankNo AS tail
FROM
	tb_bank