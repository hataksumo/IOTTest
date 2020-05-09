package ps.zhifa.test.multiIotDevice.Entity;

import ps.zhifa.test.multiIotDevice.common.ItemElementData;
import ps.zhifa.test.multiIotDevice.Config.Data.MonsterConfigData;
import ps.zhifa.test.multiIotDevice.Entity.Drop.DropUtil;
import ps.zhifa.test.multiIotDevice.Entity.Spot.BattleSpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.Spot;

import java.util.ArrayList;
import java.util.List;

public class Monster extends ActiveEntity
{
    String _name;
    int _id;
    String[] _drops;

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
        _drops = v_cfgData.getDrops();
    }

    public List<ItemElementData> drop()
    {
        DropUtil dropUtil = DropUtil.get_instance();
        List<ItemElementData> rtn = new ArrayList<>();
        for(int i=0;i<_drops.length;i++)
        {
            rtn.addAll(dropUtil.getRandom(_drops[i]));
        }
        return rtn;
    }
}
