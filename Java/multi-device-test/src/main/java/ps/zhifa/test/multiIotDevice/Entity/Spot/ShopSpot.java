package ps.zhifa.test.multiIotDevice.Entity.Spot;

import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.EconomicalBehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Player;

public class ShopSpot extends Spot
{

    public ShopSpot(String v_name) {
        super(v_name);
    }

    @Override
    public Type getType() {
        return Type.Shop;
    }

    @Override
    public void onPlayerEnter(Player v_player) {

    }

    @Override
    public void step(float v_dt) {

    }

    @Override
    public void excuteCmd(BehaviourCmd v_cmd) {
        EconomicalBehaviourCmd cmd = (EconomicalBehaviourCmd)v_cmd;
        if(cmd.getSubType()==EconomicalBehaviourCmd.SubType.BuyHpBottle)
        {

        }
        else if(cmd.getSubType()==EconomicalBehaviourCmd.SubType.BuyResurrectStone)
        {

        }
        else if(cmd.getSubType()==EconomicalBehaviourCmd.SubType.Charge)
        {

        }
        else if(cmd.getSubType()==EconomicalBehaviourCmd.SubType.Rebonr)
        {

        }
    }
}
