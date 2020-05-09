package ps.zhifa.test.multiIotDevice.Entity;

import com.huaweicloud.sdk.iot.device.IoTDevice;
import ps.zhifa.test.multiIotDevice.App;
import ps.zhifa.test.multiIotDevice.Config.Data.*;
import ps.zhifa.test.multiIotDevice.Config.ItemConfig;
import ps.zhifa.test.multiIotDevice.Entity.Skill.SkillEffectEntity;
import ps.zhifa.test.multiIotDevice.Entity.Spot.BattleSpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.Spot;
import ps.zhifa.test.multiIotDevice.common.Global;
import ps.zhifa.test.multiIotDevice.common.ItemElementData;
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
    static final int HP_BOTTLE_RECOVER = 100;

    static final int COIN_ID = 10000;
    static final int RESURRECT_STONE_ID = 10007;
    static final int REVIVE_COST = 1000;
    String _name;
    int _eatBottles = 0;
    int _charge = 0;
    int _prepareHpBottleNum = 0;
    int _prepareResurrectStone = 0;
    App _app;

    public Player()
    {
        _demageService = new RpgDemageService();
        _chargeService = new RpgChargeService();
        _rpgService = new RpgDropService();
        _attribute = new Attribute();
    }

    public void initWithConfig(PlayerConfigData v_cfg,App v_app)
    {
        DeviceConfigData deviceConfigData = v_cfg.getDevice();
        AttrConfigData attrConfigData = v_cfg.getAttr();
        initDevice(Global.IOT_SERVER_address,deviceConfigData.getId(),deviceConfigData.getSecret());
        initWithAttrCfg(attrConfigData);
        initWithSkillCfg(v_cfg.getSkills());
        _prepareHpBottleNum = v_cfg.getRebornBuyHpBottleNum();
        _prepareResurrectStone = v_cfg.getRebornBuyResurrectStone();
        _name = v_cfg.getId();
        initBag();
        _app = v_app;
    }

    public boolean initDevice(String v_url,String v_deviceId,String v_passwd)
    {
        _device = new IoTDevice(v_url,v_deviceId,v_passwd);
        _device.addService("HpSensor",_demageService);
        _demageService.setPlayer(this);
        return _device.init() == 0;
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


    public boolean affordable(List<ItemElementData> v_cost)
    {
        for(int i=0;i<v_cost.size();i++)
        {
            ItemElementData cie = v_cost.get(i);
            if(_bag.get(cie.getItemId()) < cie.getCnt())
            {
                return false;
            }
        }
        return true;
    }

    public boolean pay(List<ItemElementData> v_cost)
    {
        if(!affordable(v_cost))
            return false;
        payWhatever(v_cost);
        return true;
    }

    public void payWhatever(List<ItemElementData> v_cost)
    {
        for(int i=0;i<v_cost.size();i++)
        {
            ItemElementData cie = v_cost.get(i);
            int newCnt = _bag.get(cie.getItemId()) - cie.getCnt();
            _bag.put(cie.getItemId(),newCnt);
        }
    }

    public void award(List<ItemElementData> v_cost)
    {
        for(int i=0;i<v_cost.size();i++)
        {
            ItemElementData cie = v_cost.get(i);
            int newCnt = _bag.get(cie.getItemId()) + cie.getCnt();
            _bag.put(cie.getItemId(),newCnt);
        }
    }

    public void pickupDropAward(List<ItemElementData> v_cost)
    {
        award(v_cost);
    }


    public void compulsiveReborn()
    {
        int newCoin = _bag.get(COIN_ID) - REVIVE_COST;
        _bag.put(COIN_ID,newCoin);
        reborn();
        enterSpot(_app.getMainCity());
    }

    public void eatBottle()
    {
        System.out.println("玩家 "+_name+" 嗑药啦");
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


    public void enterSpot(Spot v_spot)
    {
        if(_spot!=null&& _spot!=v_spot)
        {
            _spot.onPlayerLeave();
        }
        v_spot.onPlayerEnter(this);
        this._spot = v_spot;
    }

    public void charge(int v_num)
    {
        _charge = _charge + v_num;
    }

    public boolean hasResurrectStone()
    {
        Integer num = _bag.get(RESURRECT_STONE_ID);
        System.out.println("当前复活石数量为 "+num);
        if(num != null)
            return num>0;
        return false;
    }

    public void useResurrectStone()
    {
        Integer newNum = _bag.get(RESURRECT_STONE_ID) - 1;
        _bag.put(RESURRECT_STONE_ID,newNum);
        reborn();
    }

    public int getCoins()
    {
        return _bag.get(COIN_ID);
    }

    public boolean isPrepared()
    {
        return _bag.get(HP_BOTTLE_ID) >= _prepareHpBottleNum && _bag.get(RESURRECT_STONE_ID) >= _prepareResurrectStone;
    }

    public void prepareBattle()
    {
        int bottleNum = _bag.get(HP_BOTTLE_ID);
        if(bottleNum < _prepareHpBottleNum)
        {
            buyBottles(_prepareHpBottleNum - bottleNum);
        }
        int resurrectNum = _bag.get(RESURRECT_STONE_ID);
        if(resurrectNum < _prepareResurrectStone)
        {
            buyResurrectStone(_prepareResurrectStone - resurrectNum);
        }
    }

    public void buyBottles(int v_num)
    {
        System.out.println(String.format("玩家 %s 买%d个血瓶",_name,v_num));
        while(Shop.get_instance().buyItems(this,HP_BOTTLE_ID,v_num)>0)
        {
            charge();
        }
    }

    public void buyResurrectStone(int v_num)
    {
        System.out.println(String.format("玩家 %s 买%d个复活石",_name,v_num));
        while(Shop.get_instance().buyItems(this,RESURRECT_STONE_ID,v_num)>0)
        {
            charge();
        }
    }

    public void charge()
    {
        _charge = _charge + 6;
        System.out.println("氪金6元，已氪"+_charge);
        _bag.put(COIN_ID,_bag.get(COIN_ID)+1000);
    }

    @Override
    public void onSuffer(int v_orgDmg,int v_realDmg,int v_orgHp,int v_newHp)
    {
        System.out.println(String.format("玩家%s, sufferDmg %d 剩余血量 %d",_name,v_realDmg,_attribute.getHp()));
        _demageService.setName(_name);
        _demageService.setHp(v_newHp);
        _demageService.setMaxHp(_attribute.getMaxHp());
        _demageService.setDef(_attribute.getDef());
        _demageService.setAtk(_attribute.getAtk());
        _demageService.setSufferDemage(v_realDmg);
        _demageService.setRebornTimes(_rebornTimes);
        _demageService.setEatHpBottle(_eatBottles);
        _demageService.firePropertiesChanged("name","hp","maxHp","def","atk","rebornTimes","sufferDemage","eatHpBottle");
        //_demageService.firePropertiesChanged();
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_eatBottles() {
        return _eatBottles;
    }

    public void set_eatBottles(int _eatBottles) {
        this._eatBottles = _eatBottles;
    }

    public int get_charge() {
        return _charge;
    }

    public void set_charge(int _charge) {
        this._charge = _charge;
    }
}
