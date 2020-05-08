package ps.zhifa.test.multiIotDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ps.zhifa.test.multiIotDevice.Config.*;
import ps.zhifa.test.multiIotDevice.Config.Data.PlayerConfigData;
import ps.zhifa.test.multiIotDevice.Entity.*;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.EconomicalBehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Drop.DropUtil;
import ps.zhifa.test.multiIotDevice.Entity.Spot.BattleSpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.CitySpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.ShopSpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.Spot;
import ps.zhifa.test.multiIotDevice.common.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class App
{
    static final int FPS = 60;

    List<Player> _players;
    List<Spot> _spots;
    CitySpot _citySpot;
    ShopSpot _shopSpot;
    DropUtil _dropUtil;
    long _lastFramTime;
    boolean _inited;

    public App()
    {
        _inited = false;
    }


    protected void loadConfig()
    {
        //玩家表
        String playerStrCfg = FileUtils.readAllToString("json/player.json");
        JSONObject playerJsonObj = JSON.parseObject(playerStrCfg);
        PlayerConfig.get_instance().init(playerJsonObj.getJSONArray("data"));
        //怪物表
        String monsterStrCfg = FileUtils.readAllToString("json/monster.json");
        JSONObject monsterJsonObj = JSON.parseObject(monsterStrCfg);
        MonsterConfig.get_instance().init(monsterJsonObj.getJSONArray("data"));
        //技能表
        String skillStrCfg = FileUtils.readAllToString("json/skill.json");
        JSONObject skillJsonObj = JSON.parseObject(skillStrCfg);
        SkillConfig.get_instance().init(skillJsonObj.getJSONArray("data"));
        //掉落表
        String dropStrCfg = FileUtils.readAllToString("json/drop.json");
        JSONObject dropJsonObj = JSON.parseObject(dropStrCfg);
        DropConfig.get_instance().init(dropJsonObj.getJSONObject("data"));
        _dropUtil = DropUtil.get_instance();
        //道具表
        String itemStrCfg = FileUtils.readAllToString("json/item.json");
        JSONObject itemJsonObj = JSON.parseObject(itemStrCfg);
        ItemConfig.get_instance().init(itemJsonObj.getJSONArray("data"));
    }

    protected void initPlayer()
    {
        PlayerConfig playerConfig = PlayerConfig.get_instance();
        List<PlayerConfigData> playerDatas = playerConfig.getData();
        for(int i=0;i<playerDatas.size();i++)
        {
            Player p = new Player();
            p.initWithConfig(playerDatas.get(i));
            p.reborn();
            p.enterSpot(_citySpot);
            _players.add(p);
        }
    }

    //简便起见，有多少个玩家，就分配多少个场景
    protected void initSpot()
    {
        _citySpot = new CitySpot("勇者大陆");
        _shopSpot = new ShopSpot("甜心商店");
        _spots = new ArrayList<>(_players.size());
        for(int i=0;i<_players.size();i++)
        {
            Spot sp = new BattleSpot("战斗场景_"+i);
            _spots.add(sp);
        }
    }

    public Boolean init()
    {
        try{
            loadConfig();
            initSpot();
            initPlayer();

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        _inited = true;
        return true;
    }



    public void start() throws InterruptedException {
        if(!_inited)
        {
            System.out.println("请先初始化，再运行");
            return;
        }

        _lastFramTime = System.currentTimeMillis();
        step(0);
        while(true)
        {
            long curTime = System.currentTimeMillis();
            long sleepTime = (long)(1000/FPS - (curTime - _lastFramTime));
            Thread.sleep(sleepTime);
            curTime = System.currentTimeMillis();
            step((curTime - _lastFramTime)/1000.0f);
            _lastFramTime = curTime;
        }
    }

    public void step(float v_dt)
    {
        //检测玩家状态，如果不在战斗场景中，则补足战斗物资，而后选择场景，进入场景
        //如果玩家在战斗场景中，则玩家释放技能。
        //这段代码应该在一个AI类中编写，相当于模拟用户输入
        for(int i=0;i<_players.size();i++)
        {
            Player player = _players.get(i);
            Spot spot = player.getSpot();
            if(spot.getType()==Spot.Type.BattleScene)
            {
                if(player.isAlive())
                {
                    BehaviourCmd behaviourCmd = player.aiAtkBehave();
                    spot.excuteCmd(behaviourCmd);
                }
                else
                {
                    //如果有复活石，则原地复活
                    //如果没有复活石，则强制回城，直接扣除金币，可扣为负数
                    if(player.hasResurrectStone())
                    {
                        player.useResurrectStone();
                    }
                    else
                    {
                        player.compulsiveReborn();
                    }
                }
            }
            else if(spot.getType()==Spot.Type.City)
            {
                //如果欠钱，则充值
                int coinsInBag = player.getCoins();
                if(coinsInBag<0)
                {
                    EconomicalBehaviourCmd ecoBavCmd = new EconomicalBehaviourCmd();
                    ecoBavCmd.setSubType(EconomicalBehaviourCmd.SubType.Charge);
                    ecoBavCmd.setPlayer(player);
                    _shopSpot.excuteCmd(ecoBavCmd);
                    continue;
                }
                //如果备战物资不够，则购买。



                //如果金币不够则充值。
                //如果备战物资充裕，则进入战斗场景。
            }
        }
        //驱动各场景
        for(int i=0;i<_spots.size();i++)
        {
            Spot spot = _spots.get(i);
            spot.step(v_dt);
        }
    }

    public Spot getMainCity()
    {
        return _citySpot;
    }

}
