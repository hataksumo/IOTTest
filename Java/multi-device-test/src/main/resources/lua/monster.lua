--[[
output from excel monster.怪物.xlsx
--note:

colums:
{name,名称} ,{attr.maxHp,最大生命} ,{attr.maxMp,最大魔法} ,{attr.atk,攻击} ,{attr.def,防御} ,{skills[1],技能1} ,{skills[2],技能2}
primary key:
#0 [怪物]: id
]]
local _T = LangUtil.Language
return{
	[10000] = {name = "怪物1",attr = {maxHp = 100,maxMp = 0,atk = 35,def = 30},skills = {[1] = 10003,[2] = 10004}},--怪物1
	[10001] = {name = "怪物2",attr = {maxHp = 120,maxMp = 0,atk = 50,def = 0},skills = {[1] = 10003,[2] = 10004}},--怪物2
	[10002] = {name = "怪物3",attr = {maxHp = 150,maxMp = 0,atk = 20,def = 100},skills = {[1] = 10003,[2] = 10004}},--怪物3
	[10003] = {name = "怪物4",attr = {maxHp = 200,maxMp = 0,atk = 10,def = 50},skills = {[1] = 10003,[2] = 10004}}--怪物4
}