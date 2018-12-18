list
===
select * from tb_bill

findOne
===
select * from tb_bill where id = #{id}

cust
===
select 
	id as "id",
	0 as "pId",
	simplename as "name"
from  blade_dept
where pid > 0 