package ps.zhifa.test.multiIotDevice.Entity.Cmd;

import lombok.Data;
import ps.zhifa.test.multiIotDevice.Entity.Player;

@Data
public class EconomicalBehaviourCmd extends BehaviourCmd
{
    @Override
    public Type getType() {
        return Type.EconomyCmd;
    }

    public enum SubType{
        BuyHpBottle,
        BuyResurrectStone,
        Charge
    }
    SubType subType;
    int buyNum;
    Player player;

}
