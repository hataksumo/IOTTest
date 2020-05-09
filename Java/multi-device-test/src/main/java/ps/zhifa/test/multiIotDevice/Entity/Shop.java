package ps.zhifa.test.multiIotDevice.Entity;

import ps.zhifa.test.multiIotDevice.Config.Data.ItemConfigData;
import ps.zhifa.test.multiIotDevice.common.ItemElementData;
import ps.zhifa.test.multiIotDevice.Config.ItemConfig;

import java.util.List;

public class Shop
{
    static final int COIN_ID = 10000;
    static final int HP_BOTTLE_ID = 10004;
    static final int RESURRECT_STONE_ID = 10007;

    static Shop _instance;
    public static Shop get_instance()
    {
        if(_instance == null)
        {
            _instance = new Shop();
        }
        return _instance;
    }

    public int buyItems(Player v_player, int v_id,int v_num)
    {
        ItemConfigData configData = ItemConfig.get_instance().get(v_id);
        if(configData == null)
        {
            System.out.println("没有找到Id为"+v_id+"的道具");
            return -1;
        }

        List<ItemElementData> costData = ItemElementData.formSingleList(COIN_ID,configData.getPrice() * v_num);
        List<ItemElementData> awardData = ItemElementData.formSingleList(v_id,v_num);
        int coinsInBag = v_player.getCoins();
        int cost = configData.getPrice() * v_num;
        if(coinsInBag >= cost)
        {
            v_player.award(awardData);
            return 0;
        }
        return cost - coinsInBag;
    }
}
