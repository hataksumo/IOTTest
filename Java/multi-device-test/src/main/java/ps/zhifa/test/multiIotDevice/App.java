package ps.zhifa.test.multiIotDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huaweicloud.sdk.iot.device.IoTDevice;
import ps.zhifa.test.multiIotDevice.Config.*;
import ps.zhifa.test.multiIotDevice.Entity.DropUtil;
import ps.zhifa.test.multiIotDevice.Entity.Monster;
import ps.zhifa.test.multiIotDevice.Entity.Player;
import ps.zhifa.test.multiIotDevice.common.FileUtils;
import ps.zhifa.test.multiIotDevice.service.RpgDemageService;

import java.util.ArrayList;
import java.util.List;

public class App
{
    List<Player> _players;
    DropUtil _dropUtil;

    public void loadConfig()
    {
        //玩家表
        String playerStrCfg = FileUtils.readAllToString("json/player.json");
        JSONObject playerJsonObj = JSON.parseObject(playerStrCfg);
        PlayerConfig.get_instance().init(playerJsonObj.getJSONArray("data"));
        //怪物表
        String monsterStrCfg = FileUtils.readAllToString("json/monster.json");
        JSONObject monsterJsonObj = JSON.parseObject(monsterStrCfg);
        MonsterConfig.get_instance().init(monsterJsonObj.getJSONArray("data"));
        //技能表
        String skillStrCfg = FileUtils.readAllToString("json/skill.json");
        JSONObject skillJsonObj = JSON.parseObject(skillStrCfg);
        SkillConfig.get_instance().init(skillJsonObj.getJSONArray("data"));
        //掉落表
        String dropStrCfg = FileUtils.readAllToString("json/drop.json");
        JSONObject dropJsonObj = JSON.parseObject(dropStrCfg);
        DropConfig.get_instance().init(dropJsonObj.getJSONObject("data"));
        _dropUtil = DropUtil.get_instance();
        //道具表
        String itemStrCfg = FileUtils.readAllToString("json/item.json");
        JSONObject itemJsonObj = JSON.parseObject(itemStrCfg);
        ItemConfig.get_instance().init(itemJsonObj.getJSONArray("data"));
    }

    public void initPlayer()
    {
        
    }

}
