list
===
select * from tb_station

findOne
===
select * from tb_station where id = #{id}

diy
===
select StationNo as ID,StationAbbreviate as TEXT from tb_station