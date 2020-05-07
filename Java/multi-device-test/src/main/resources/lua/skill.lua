--[[
output from excel skill.技能.xlsx
--note:

colums:
{skillFac,技能系数} ,{skillBase,技能基础} ,{cost,技能消耗} ,{cd,CD}
primary key:
#0 [技能]: id
]]
local _T = LangUtil.Language
return{
	[10001] = {skillFac = 0.5,skillBase = 0,cost = 0,cd = 1},--玩家普攻
	[10002] = {skillFac = 3,skillBase = 35,cost = 0,cd = 4},--玩家技能
	[10003] = {skillFac = 1,skillBase = 0,cost = 0,cd = 1},--怪物普攻
	[10004] = {skillFac = 3,skillBase = 50,cost = 0,cd = 10}--怪物技能
}