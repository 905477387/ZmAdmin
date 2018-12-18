list
===
select *,Balance as Balance1 from tb_oil_trade

listExcel
===
SELECT c.CardSerialNo,c.CarNo,c.ReserveFund,t.*,t.Balance as Balance1 from tb_oil_trade t left join tb_iccard c on t.CardNo = c.CardNo

listExcelSum
===
select ROUND(sum(Qty),2) as Qty,ROUND(sum(TradeMoney),2) as TradeMoney from tb_oil_trade

findOne
===
select * from tb_oil_trade where id = #{id}