package ps.zhifa.test.multiIotDevice.Entity.Cmd;

import lombok.Data;
import ps.zhifa.test.multiIotDevice.Entity.ActiveEntity;

@Data
public class BattleBehaviourCmd extends BehaviourCmd
{
    int skillLoc;
    ActiveEntity caster;
    ActiveEntity target;

    @Override
    public Type getType() {
        return Type.BattleCmd;
    }

    public enum SubType
    {
        castSkill,
        idle,
        eatBottle,
        setTarget,
        clearTarget
    }
    SubType subType;
}
