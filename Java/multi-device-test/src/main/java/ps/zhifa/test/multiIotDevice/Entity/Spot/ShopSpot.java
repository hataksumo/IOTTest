package ps.zhifa.test.multiIotDevice.Entity.Spot;

import ps.zhifa.test.multiIotDevice.Config.Data.ItemElementData;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.EconomicalBehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Player;

import java.util.List;

public class ShopSpot extends Spot
{
    //血瓶
    static final int HP_BOTTLE_ID = 10004;
    static final int HP_BOTTLE_PRICE = 10;
    static final int HP_BOTTLE_RECOVER = 100;

    //金币
    static final int COIN_ID = 10000;
    static final int CHARGE_NUM = 6;
    static final int CHARGE_REWORD = 1000;

    //复活石
    static final int RESURRECT_STONE_ID = 10007;
    static final int RESURRECT_STONE_PRICE = 500;
    static final int REVIVE_COST = 1000;

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
        Player p = cmd.getPlayer();
        int buyNum = cmd.getBuyNum();
        if(cmd.getSubType()==EconomicalBehaviourCmd.SubType.BuyHpBottle)
        {
            List<ItemElementData> costs = ItemElementData.formSingleList(COIN_ID,HP_BOTTLE_PRICE * buyNum);
            if(p.pay(costs))
            {
                List<ItemElementData> award = ItemElementData.formSingleList(HP_BOTTLE_ID, buyNum);
                p.award(award);
            }
        }
        else if(cmd.getSubType()==EconomicalBehaviourCmd.SubType.BuyResurrectStone)
        {
            List<ItemElementData> costs = ItemElementData.formSingleList(RESURRECT_STONE_ID,RESURRECT_STONE_PRICE * buyNum);
            if(p.pay(costs))
            {
                List<ItemElementData> award = ItemElementData.formSingleList(RESURRECT_STONE_ID, buyNum);
                p.award(award);
            }
        }
        else if(cmd.getSubType()==EconomicalBehaviourCmd.SubType.Charge)
        {
            List<ItemElementData> award = ItemElementData.formSingleList(RESURRECT_STONE_ID, buyNum);
            p.award(award);
        }
    }
}
