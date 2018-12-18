list
===
select * from tb_shop where is_deleted = 0

findOne
===
select * from tb_shop where id = #{id}

combo
===
select id, name, 0 as pid from tb_shop where is_deleted = 0