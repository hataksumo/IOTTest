package ps.zhifa.test.multiIotDevice.Entity;

import ps.zhifa.test.multiIotDevice.Config.Data.MonsterConfigData;
import ps.zhifa.test.multiIotDevice.Entity.Spot.BattleSpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.Spot;

public class Monster extends ActiveEntity
{
    String _name;
    int _id;

    @Override
    public ActiveEntity findTarget() {
        if(_spot.getType()== Spot.Type.BattleScene)
        {
            BattleSpot bsp = (BattleSpot)_spot;
            _target = bsp.GetPlayer();
            return _target;
        }
        return null;
    }

    public void initWithConfig(MonsterConfigData v_cfgData)
    {
        initWithAttrCfg(v_cfgData.getAttr());
        initWithSkillCfg(v_cfgData.getSkills());
        _name = v_cfgData.getName();
        _id = v_cfgData.getId();
    }
}
