package ps.zhifa.test.multiIotDevice.Entity.Spot;

import ps.zhifa.test.multiIotDevice.Config.Data.MonsterConfigData;
import ps.zhifa.test.multiIotDevice.Config.MonsterConfig;
import ps.zhifa.test.multiIotDevice.Entity.ActiveEntity;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Monster;
import ps.zhifa.test.multiIotDevice.Entity.Player;
import ps.zhifa.test.multiIotDevice.Entity.Skill.SkillEffectEntity;

import java.util.List;

public abstract class Spot
{
    String _name;

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    protected Spot(String v_name)
    {
        _name = v_name;
    }
    public abstract Type getType();

    public enum Type
    {
        City,
        BattleScene,
        Shop
    }
    public abstract void onPlayerEnter(Player v_player);
    public abstract void step(float v_dt);
    public abstract void excuteCmd(BehaviourCmd v_cmd);

}
