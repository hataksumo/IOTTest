--[[
output from excel player.玩家.xlsx
--note:

colums:
{device.id,设备Id} ,{device.secret,设备秘钥} ,{attr.maxHp,最大生命} ,{attr.maxMp,最大魔法} ,{attr.atk,攻击} ,{attr.def,防御} ,{skills[1],技能1} ,{skills[2],技能2} ,{rebornBuyHpBottleNum,回家买血瓶数} ,{rebornBuyResurrectStone,回家购买复活石}
primary key:
#0 [玩家]: id
]]
local _T = LangUtil.Language
return{
	test_player_001 = {device = {id = "test_player_001",secret = "qqhilvMgAl7"},attr = {maxHp = 350,maxMp = 100,atk = 60,def = 30},skills = {[1] = 10001,[2] = 10002},rebornBuyHpBottleNum = 20,rebornBuyResurrectStone = 0},--玩家1
	test_player_002 = {device = {id = "test_player_002",secret = "qqhilvMgAl7"},attr = {maxHp = 350,maxMp = 100,atk = 60,def = 30},skills = {[1] = 10001,[2] = 10002},rebornBuyHpBottleNum = 40,rebornBuyResurrectStone = 0},--玩家2
	test_player_003 = {device = {id = "test_player_003",secret = "qqhilvMgAl7"},attr = {maxHp = 350,maxMp = 100,atk = 60,def = 30},skills = {[1] = 10001,[2] = 10002},rebornBuyHpBottleNum = 20,rebornBuyResurrectStone = 1},--玩家3
	test_player_004 = {device = {id = "test_player_004",secret = "qqhilvMgAl7"},attr = {maxHp = 350,maxMp = 100,atk = 60,def = 30},skills = {[1] = 10001,[2] = 10002},rebornBuyHpBottleNum = 40,rebornBuyResurrectStone = 1}--玩家4
}