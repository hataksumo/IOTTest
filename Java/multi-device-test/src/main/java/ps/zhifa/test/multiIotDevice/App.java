package ps.zhifa.test.multiIotDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ps.zhifa.test.multiIotDevice.Config.*;
import ps.zhifa.test.multiIotDevice.Config.Data.PlayerConfigData;
import ps.zhifa.test.multiIotDevice.Entity.*;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Drop.DropUtil;
import ps.zhifa.test.multiIotDevice.Entity.Spot.BattleSpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.CitySpot;
import ps.zhifa.test.multiIotDevice.Entity.Spot.Spot;
import ps.zhifa.test.multiIotDevice.common.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class App
{
    static final int FPS = 60;

    List<Player> _players;
    List<Spot> _battleSpots;
    CitySpot _citySpot;
    DropUtil _dropUtil;
    long _lastFramTime;
    boolean _inited;
    private int _playerNum;

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
        _playerNum = PlayerConfig.get_instance().getData().size();
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
        _players = new ArrayList<>(_playerNum);
        for(int i=0;i<playerDatas.size();i++)
        {
            Player p = new Player();
            p.initWithConfig(playerDatas.get(i),this);
            p.reborn();
            p.enterSpot(_citySpot);
            _players.add(p);
        }
    }

    //简便起见，有多少个玩家，就分配多少个场景
    protected void initSpot()
    {
        _citySpot = new CitySpot("勇者大陆");
        _battleSpots = new ArrayList<>(_playerNum);
        for(int i=0;i<_playerNum;i++)
        {
            Spot sp = new BattleSpot("战斗场景_"+i);
            _battleSpots.add(sp);
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
            if(sleepTime > 0)
            {
                Thread.sleep(sleepTime);
            }
            curTime = System.currentTimeMillis();
            float dt = (curTime - _lastFramTime)/1000.0f;
            _lastFramTime = curTime;
            step(dt);
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
                    System.out.println(String.format("玩家%s 死亡，剩余金币 %d",player.get_name(),player.getCoins()));
                    if(player.hasResurrectStone())
                    {
                        System.out.println("吃复活石复活");
                        player.useResurrectStone();
                    }
                    else
                    {
                        System.out.println("强制买活");
                        player.compulsiveReborn();
                    }
                }
            }
            else if(spot.getType()==Spot.Type.City)
            {
                //如果欠钱，则充值
                while (player.getCoins()<0)
                {
                    player.charge();
                }
                //如果备战物资不够，则购买。
                //如果金币不够则充值。
                player.prepareBattle();
                //如果备战物资充裕，则进入战斗场景。
                player.enterSpot(_battleSpots.get(i));
            }
        }
        //驱动各场景
        for(int i = 0; i< _battleSpots.size(); i++)
        {
            Spot spot = _battleSpots.get(i);
            spot.step(v_dt);
        }
    }

    public Spot getMainCity()
    {
        return _citySpot;
    }

}
