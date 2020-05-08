package ps.zhifa.test.multiIotDevice.Entity;

import com.huaweicloud.sdk.iot.device.IoTDevice;
import ps.zhifa.test.multiIotDevice.Config.Data.*;
import ps.zhifa.test.multiIotDevice.Config.ItemConfig;
import ps.zhifa.test.multiIotDevice.Entity.Skill.SkillEffectEntity;
import ps.zhifa.test.multiIotDevice.Entity.Spot.BattleSpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.Spot;
import ps.zhifa.test.multiIotDevice.common.Global;
import ps.zhifa.test.multiIotDevice.service.RpgChargeService;
import ps.zhifa.test.multiIotDevice.service.RpgDemageService;
import ps.zhifa.test.multiIotDevice.service.RpgDropService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends ActiveEntity
{
    IoTDevice _device;
    RpgDemageService _demageService;
    RpgChargeService _chargeService;
    RpgDropService _rpgService;
    Map<Integer,Integer> _bag;
    static final int HP_BOTTLE_ID = 10004;
    static final int HP_BOTTLE_PRICE = 10;
    static final int HP_BOTTLE_RECOVER = 100;

    static final int COIN_ID = 10000;
    static final int CHARGE_NUM = 6;
    static final int CHARGE_REWORD = 1000;
    static final int RESURRECT_STONE_ID = 10007;
    static final int RESURRECT_STONE_PRICE = 500;
    static final int REVIVE_COST = 1000;
    int _eatBottles = 0;
    int _charge = 0;
    int _rebornBuyHpBottleNum = 0;
    int _rebornBuyResurrectStone = 0;


    public Player()
    {
        _demageService = new RpgDemageService();
        _chargeService = new RpgChargeService();
        _rpgService = new RpgDropService();
        _attribute = new Attribute();
    }

    public void initWithConfig(PlayerConfigData v_cfg)
    {
        DeviceConfigData deviceConfigData = v_cfg.getDevice();
        AttrConfigData attrConfigData = v_cfg.getAttr();
        _device = new IoTDevice(Global.IOT_SERVER_address,deviceConfigData.getId(),deviceConfigData.getSecret());
        initWithAttrCfg(attrConfigData);
        initWithSkillCfg(v_cfg.getSkills());
        _rebornBuyHpBottleNum = v_cfg.getRebornBuyHpBottleNum();
        _rebornBuyResurrectStone = v_cfg.getRebornBuyHpBottleNum();
    }

    public void initDevice(String v_url,String v_deviceId,String v_passwd)
    {
        _device = new IoTDevice(v_url,v_deviceId,v_passwd);
    }

    public void initBag()
    {
        _bag = new HashMap<>();
        List<ItemConfigData> itemConfigs = ItemConfig.get_instance().getData();
        for(int i=0;i<itemConfigs.size();i++)
        {
            ItemConfigData itemConfigData = itemConfigs.get(i);
            _bag.put(itemConfigData.getId(),itemConfigData.getIniNum());
        }
    }

    @Override
    public ActiveEntity findTarget() {
        if(_spot.getType()==Spot.Type.BattleScene)
        {
            BattleSpot bsp = (BattleSpot)_spot;
            _target = bsp.GetAMonsterTarget();
            return _target;
        }
        return null;
    }

//    @Override
//    public void _onReborn() {
//        int bottleNum = _bag.get(HP_BOTTLE_ID);
//        if(bottleNum < _rebornBuyHpBottleNum)
//        {
//            buyBottles(_rebornBuyHpBottleNum - bottleNum);
//        }
//        int resurrectNum = _bag.get(RESURRECT_STONE_ID);
//        if(resurrectNum < _rebornBuyResurrectStone)
//        {
//            buyResurrectStone(_rebornBuyResurrectStone - resurrectNum);
//        }
//    }

    public boolean affordable(List<ElementData> v_cost)
    {
        for(int i=0;i<v_cost.size();i++)
        {
            ElementData cie = v_cost.get(i);
            if(_bag.get(cie.getItemId()) < cie.getCnt())
            {
                return false;
            }
        }
        return true;
    }

    public boolean pay(List<ElementData> v_cost)
    {
        if(!affordable(v_cost))
            return false;
        payWhatever(v_cost);
        return true;
    }

    public void payWhatever(List<ElementData> v_cost)
    {
        for(int i=0;i<v_cost.size();i++)
        {
            ElementData cie = v_cost.get(i);
            int newCnt = _bag.get(cie.getItemId()) - cie.getCnt();
            _bag.put(cie.getItemId(),newCnt);
        }
    }

    public void award(List<ElementData> v_cost)
    {
        for(int i=0;i<v_cost.size();i++)
        {
            ElementData cie = v_cost.get(i);
            int newCnt = _bag.get(cie.getItemId()) + cie.getCnt();
            _bag.put(cie.getItemId(),newCnt);
        }
    }


    public void compulsiveReborn()
    {
        int newCoin = _bag.get(COIN_ID) - REVIVE_COST;
        _bag.put(COIN_ID,newCoin);
    }

//    public void buyBottles(int v_num)
//    {
//        int cost =  HP_BOTTLE_PRICE * v_num;
//        if(_bag.get(COIN_ID)<cost)
//        {
//            return;
//        }
//        int newCoin = _bag.get(COIN_ID) - cost;
//        _bag.put(COIN_ID,newCoin);
//        _bag.put(HP_BOTTLE_ID,_bag.get(HP_BOTTLE_ID) + v_num);
//    }
//
//    public void buyResurrectStone(int v_num)
//    {
//        int cost =  RESURRECT_STONE_PRICE * v_num;
//        if(_bag.get(RESURRECT_STONE_ID)<cost)
//        {
//            return;
//        }
//        int newCoin = _bag.get(COIN_ID) - cost;
//        _bag.put(COIN_ID,newCoin);
//        _bag.put(RESURRECT_STONE_ID,_bag.get(RESURRECT_STONE_ID) + v_num);
//    }
//
//    public void charge()
//    {
//        _charge = _charge + CHARGE_NUM;
//        _bag.put(COIN_ID,_bag.get(COIN_ID) + CHARGE_REWORD);
//    }

    public void eatBottle()
    {
        int bottleNum = _bag.get(HP_BOTTLE_ID);
        if(bottleNum > 0)
        {
            SkillEffectEntity see = new SkillEffectEntity();
            see.setSkillOrgDmg(HP_BOTTLE_RECOVER);
            getHeal(see);
            _bag.put(HP_BOTTLE_ID,bottleNum - 1);
            _eatBottles = _eatBottles +1;
        }
    }

//    public Boolean revive()
//    {
//        int resurrectStoneNum = _bag.get(RESURRECT_STONE_ID);
//        if(resurrectStoneNum > 0)
//        {
//            _bag.put(RESURRECT_STONE_ID,resurrectStoneNum - 1);
//            reborn();
//            return true;
//        }
//        while(_bag.get(COIN_ID) < REVIVE_COST)
//        {
//            charge();
//        }
//        _bag.put(COIN_ID,_bag.get(COIN_ID) - REVIVE_COST);
//        reborn();
//        return true;
//    }

    public void enterSpot(Spot v_spot)
    {
        v_spot.onPlayerEnter(this);
        this._spot = v_spot;
    }

}
