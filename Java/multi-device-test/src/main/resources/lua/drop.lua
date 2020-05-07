--[[
output from excel drop.掉落.xlsx
--note:

colums:
{type,组类型
1.组内按权重选1个，2.组内按权重独立掉落} ,{weight,权重和} ,{itemId,掉落Id} ,{numMin,掉落最少} ,{numMax,掉落最多}
primary key:
#0 [掉落组]: groupId
#1 [掉落]: groupId,helpCol,loc
]]
local _T = LangUtil.Language
return{
	["pt-1"] = {
		type = 1,
		weight = 10000,
		drops = {
			[1] = {itemId = 10001,numMin = 1,numMax = 1,weight = 3500},
			[2] = {itemId = 10001,numMin = 1,numMax = 1,weight = 1250},
			[3] = {itemId = 10001,numMin = 1,numMax = 1,weight = 250}
		}
	}--普通怪掉落
}