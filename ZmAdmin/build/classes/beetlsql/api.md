investorLogin
===
SELECT
	id,
	StationNo as station_no,
	StationAbbreviate as station_abbreviate,
	InvestorName as investor_name,
	InvestorPhone as investor_phone,
	InvestorMoney as investor_money,
	'investor' as role_code
FROM
	`tb_investor` 
WHERE
	InvestorPhone = #{phone} 
	AND IsDeleted = 0

stationLogin
===
SELECT
    id,
	account,
	`name`,
	phone ,
    'station' as role_code
FROM
	blade_user 
WHERE
	STATUS = 1 
	AND roleid = 4
	AND phone = #{phone}
	
shopLogin
===
SELECT
    id,
	phone as account,
	`name`,
	phone ,
    'station' as role_code
FROM
	tb_shop 
WHERE
	is_deleted = 0 
	AND phone = #{phone}

customerLogin
===
SELECT
    id,
	CardNo as card_no,
	CustNo as cust_no,
	CustName as cust_name,
	HolderName as holder_name,
	TelNo as tel_no,
	Balance as balance
FROM
	`tb_iccard` 
WHERE
	TelNo = #{phone} 
	AND CardStatus = 2

shopLogin
===
SELECT
	* 
FROM
	`tb_shop` 
WHERE
	phone = #{phone} 

balance
===
SELECT
	Balance as balance
FROM
	`tb_iccard` 
WHERE
	TelNo = #{phone} 
	AND CardStatus = 2

shopList
===
SELECT
	* 
FROM
	`tb_shop` 
WHERE
	is_deleted = 0
	
cardList
===
SELECT
	* 
FROM
	`tb_recharge_card` 
WHERE
	is_deleted = 0 
ORDER BY
	CAST( card_price AS SIGNED INTEGER ) ASC

myOrder
===
SELECT
@ pageTag(){
	o.*,
	c.card_name,
	c.card_price,
	concat('#{text(domain)}',a.url) AS card_url 
@ }
FROM
    `tb_order` o
    LEFT JOIN tb_recharge_card c ON o.card_id = c.id
    LEFT JOIN blade_attach a ON c.card_photo = a.id
WHERE
	o.status = #{status}
AND o.customer_phone = #{phone}
@ pageIgnoreTag() {
    ORDER BY create_time desc
@ }

myChargeOrder
===
SELECT
@ pageTag(){
	o.*,
	c.card_name,
	c.card_price,
	concat('#{text(domain)}',a.url) AS card_url 
@ }
FROM
    `tb_order` o
    LEFT JOIN tb_recharge_card c ON o.card_id = c.id
    LEFT JOIN blade_attach a ON c.card_photo = a.id
WHERE
	o.status = 3
AND o.charger = #{charger}
@ if (isNotEmpty(search)) {
AND (o.order_no like concat('%', #{search},'%') OR o.customer_name like concat('%', #{search},'%') OR o.customer_phone like concat('%', #{search},'%') OR o.customer_card_no like concat('%', #{search},'%'))
@ }
@ pageIgnoreTag() {
    ORDER BY charge_time desc
@ }

myTrade
===
SELECT
@ pageTag(){
	*
@ }
FROM
    tb_oil_trade
WHERE
	CardNo = #{cardNo}
@ pageIgnoreTag() {
    ORDER BY PayTime desc
@ }

orderInfo
===
SELECT
	o.*,
	c.card_name,
	c.card_price,
	concat('#{text(domain)}',a.url) AS card_url 
FROM
    `tb_order` o
    LEFT JOIN tb_recharge_card c ON o.card_id = c.id
    LEFT JOIN blade_attach a ON c.card_photo = a.id
WHERE
    o.order_no = #{order_no}
    
    
tradeByDay
===
SELECT
	StationNo as station_no,
	ROUND( sum( CASE WHEN OilAbbreviate = '0#' THEN Qty ELSE 0 END ), 3 ) AS oil_0,
	ROUND( sum( CASE WHEN OilAbbreviate = '92#' THEN Qty ELSE 0 END ), 3 ) AS oil_92,
	ROUND( sum( CASE WHEN OilAbbreviate = '95#' THEN Qty ELSE 0 END ), 3 ) AS oil_95,
	DATE_FORMAT( PayTime, '%Y-%m-%d' ) AS time
FROM
	`tb_oil_trade` 
WHERE
	StationNo = #{StationNo} 
	AND DATE_FORMAT( PayTime, '%Y%m' ) = DATE_FORMAT( NOW( ), '%Y%m' ) 
GROUP BY
	time
ORDER BY
	time DESC
	
tradeByMonth
===
SELECT
	station_no,
	ROUND( sum(oil_0), 3 ) as oil_0,
	ROUND( sum(oil_92), 3 ) as oil_92,
	ROUND( sum(oil_95), 3 ) as oil_95,
	ROUND( sum( oil_0 * #{text(InvestorPercent)} / 100 * 0.4 * (case when (select p.proceed_num from tb_proceeds p where p.proceed_time = daytime limit 1) is null then #{text(proceedNum)} else (select p.proceed_num from tb_proceeds p where p.proceed_time = daytime limit 1) end ) / 100), 3 ) AS oil_0_money,
	ROUND( sum( oil_92 * #{text(InvestorPercent)} / 100 * 0.4 * (case when (select p.proceed_num from tb_proceeds p where p.proceed_time = daytime limit 1) is null then #{text(proceedNum)} else (select p.proceed_num from tb_proceeds p where p.proceed_time = daytime limit 1) end ) / 100 ), 3 ) AS oil_92_money,
	ROUND( sum( oil_95 * #{text(InvestorPercent)} / 100 * 0.4 * (case when (select p.proceed_num from tb_proceeds p where p.proceed_time = daytime limit 1) is null then #{text(proceedNum)} else (select p.proceed_num from tb_proceeds p where p.proceed_time = daytime limit 1) end ) / 100 ), 3 ) AS oil_95_money,
	DATE_FORMAT( daytime, '%Y-%m' ) AS time 
FROM
	(
		SELECT
			StationNo AS station_no,
			sum( CASE WHEN OilAbbreviate = '0#' THEN Qty ELSE 0 END ) AS oil_0,
			sum( CASE WHEN OilAbbreviate = '92#' THEN Qty ELSE 0 END ) AS oil_92,
			sum( CASE WHEN OilAbbreviate = '95#' THEN Qty ELSE 0 END ) AS oil_95,
			DATE_FORMAT( PayTime, '%Y-%m-%d' ) AS daytime 
		FROM
			`tb_oil_trade` 
		WHERE
            StationNo = #{StationNo} 
            AND DATE_FORMAT( DATE_SUB( NOW( ), INTERVAL 3 MONTH ), '%Y%m' ) <= DATE_FORMAT( PayTime, '%Y%m' ) 
			AND DATE_FORMAT( PayTime, '%Y%m' ) <> DATE_FORMAT( NOW( ), '%Y%m' ) 
		GROUP BY
			daytime 
	) oil 
GROUP BY
	time 
ORDER BY
	time DESC
    
tradeAll
===
SELECT ROUND(oil_0_money + oil_92_money + oil_95_money, 3) AS trade
FROM (
	SELECT SUM(oil_0 * 0.4 * CASE 
			WHEN (
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time = daytime
				LIMIT 1
			) IS NULL THEN 
			(
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time < daytime
				ORDER BY p.proceed_time DESC LIMIT 1
			)
			ELSE (
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time = daytime
				LIMIT 1
			)
		END / 100) AS oil_0_money
		, SUM(oil_92 * 0.4 * CASE 
			WHEN (
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time = daytime
				LIMIT 1
			) IS NULL THEN 
			(
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time < daytime
				ORDER BY p.proceed_time DESC LIMIT 1
			)
			ELSE (
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time = daytime
				LIMIT 1
			)
		END / 100) AS oil_92_money
		, SUM(oil_95 * 0.4 * CASE 
			WHEN (
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time = daytime
				LIMIT 1
			) IS NULL THEN 
			(
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time < daytime
				ORDER BY p.proceed_time DESC LIMIT 1
			)
			ELSE (
				SELECT p.proceed_num
				FROM tb_proceeds p
				WHERE p.proceed_time = daytime
				LIMIT 1
			)
		END / 100) AS oil_95_money
	FROM (
		SELECT StationNo AS station_no, SUM(CASE 
				WHEN OilAbbreviate = '0#' THEN Qty
				ELSE 0
			END) AS oil_0, SUM(CASE 
				WHEN OilAbbreviate = '92#' THEN Qty
				ELSE 0
			END) AS oil_92
			, SUM(CASE 
				WHEN OilAbbreviate = '95#' THEN Qty
				ELSE 0
			END) AS oil_95
			, DATE_FORMAT(PayTime, '%Y-%m-%d') AS daytime
		FROM `tb_oil_trade`
		WHERE StationNo = #{StationNo}
			AND DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 3 MONTH), '%Y%m') <= DATE_FORMAT(PayTime, '%Y%m')
			AND DATE_FORMAT(PayTime, '%Y%m') <> DATE_FORMAT(NOW(), '%Y%m')
		GROUP BY daytime
	) oil
) a
    
tradeByDayOld
===
SELECT
	StationNo as station_no,
	ROUND( sum( CASE WHEN OilAbbreviate = '0#' THEN TradeMoney ELSE 0 END ) * #{text(InvestorPercent)} / 100, 3 ) AS oil_0,
	ROUND( sum( CASE WHEN OilAbbreviate = '92#' THEN TradeMoney ELSE 0 END ) * #{text(InvestorPercent)} / 100, 3 ) AS oil_92,
	ROUND( sum( CASE WHEN OilAbbreviate = '95#' THEN TradeMoney ELSE 0 END ) * #{text(InvestorPercent)} / 100, 3 ) AS oil_95,
	DATE_FORMAT( PayTime, '%Y-%m-%d' ) AS time
FROM
	`tb_oil_trade` 
WHERE
	StationNo = #{StationNo} 
	AND DATE_FORMAT( PayTime, '%Y%m' ) = DATE_FORMAT( NOW( ), '%Y%m' ) 
GROUP BY
	time
ORDER BY
	time DESC
	
tradeByMonthOld
===
SELECT
	StationNo as station_no,
	ROUND( sum( CASE WHEN OilAbbreviate = '0#' THEN TradeMoney ELSE 0 END ) * #{text(InvestorPercent)} / 100, 3 ) AS oil_0,
	ROUND( sum( CASE WHEN OilAbbreviate = '92#' THEN TradeMoney ELSE 0 END ) * #{text(InvestorPercent)} / 100, 3 ) AS oil_92,
	ROUND( sum( CASE WHEN OilAbbreviate = '95#' THEN TradeMoney ELSE 0 END ) * #{text(InvestorPercent)} / 100, 3 ) AS oil_95,
	DATE_FORMAT( PayTime, '%Y-%m' ) AS time 
FROM
	`tb_oil_trade` 
WHERE
	StationNo = #{StationNo} 
	AND DATE_FORMAT( DATE_SUB( NOW( ), INTERVAL 3 MONTH ), '%Y%m' ) <= DATE_FORMAT( PayTime, '%Y%m' ) 
	AND DATE_FORMAT( PayTime, '%Y%m' ) <> DATE_FORMAT( NOW( ), '%Y%m' ) 
GROUP BY
	time 
ORDER BY
	time DESC
	
	
myInfo
===
SELECT
	'#{text(StationNo)}' as station_no,
	( SELECT count( 1 ) FROM tb_iccard WHERE CustNo IN ( SELECT CustNo FROM tb_custom WHERE StationNo = #{StationNo} ) ) AS card_number,
	'#{text(StationAbbreviate)}' as station_name,
	'' as time
	
myInfoOld
===
SELECT
	StationNo AS station_no,
	ROUND( sum( TradeMoney ) * #{text(InvestorPercent)} / 100, 3 ) AS trade_money,
	( SELECT count( 1 ) FROM tb_iccard WHERE CustNo IN ( SELECT CustNo FROM tb_custom WHERE StationNo = #{StationNo} ) ) AS card_number,
	'#{text(StationAbbreviate)}' as station_name,
    DATE_FORMAT( PayTime, '%Y-%m' ) AS time 
FROM
	`tb_oil_trade` 
WHERE
	StationNo = #{StationNo} 
	AND DATE_FORMAT( PayTime, '%Y%m' ) = DATE_FORMAT( NOW( ), '%Y%m' ) 
GROUP BY
	time
	
myCompany
===
SELECT
	CustNo as cust_no,
	CustomerName as cust_name
FROM
	tb_custom 
WHERE
	StationNo = #{StationNo}
	
notice
===
SELECT
@ pageTag(){
	bn.id id,bn.title title,bn.type type,bn.content content,bn.publishtime punlishtime,bn.createtime createtime,bn.pic pic,bn.version version,bu.name name
@ }
FROM
    blade_notice bn
    inner join blade_user bu on bn.creater = bu.id
WHERE
	type = #{type}
@ pageIgnoreTag() {
    ORDER BY bn.createtime desc
@ }
