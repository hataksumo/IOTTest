--[[
output from excel item.道具.xlsx
--note:

colums:
{name,名称} ,{iniNum,初始数量} ,{quality,品质} ,{params,参数}
primary key:
#0 [道具]: id
]]
local _T = LangUtil.Language
return{
	[10000] = {name = "金币",iniNum = 1000,quality = 1},--金币
	[10001] = {name = "普通宝石",iniNum = 0,quality = 2},--宝石_1
	[10002] = {name = "精良宝石",iniNum = 0,quality = 3},--宝石_2
	[10003] = {name = "史诗宝石",iniNum = 0,quality = 4},--宝石_3
	[10004] = {name = "血瓶-小",iniNum = 20,quality = 1,params = {200}},--血瓶1
	[10005] = {name = "血瓶-中",iniNum = 0,quality = 2,params = {500}},--血瓶2
	[10006] = {name = "血瓶-大",iniNum = 0,quality = 3,params = {1000}},--血瓶3
	[10007] = {name = "复活石",iniNum = 0,quality = 4}--复活石
}