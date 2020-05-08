package ps.zhifa.test.multiIotDevice.Entity.Spot;

import ps.zhifa.test.multiIotDevice.Config.Data.MonsterConfigData;
import ps.zhifa.test.multiIotDevice.Config.MonsterConfig;
import ps.zhifa.test.multiIotDevice.Entity.ActiveEntity;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BattleBehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Monster;
import ps.zhifa.test.multiIotDevice.Entity.Player;
import ps.zhifa.test.multiIotDevice.Entity.Skill.SkillEffectEntity;

import java.util.List;

public class BattleSpot extends Spot
{
    static final float REFRESH_TIME_MIN = 2;
    static final float REFRESH_TIME_MAX = 5;
    float _refreshDownTime;
    Monster _monster;
    Player _player;
    public BattleSpot(String v_name)
    {
        super(v_name);
    }

    @Override
    public Type getType() {
        return Type.BattleScene;
    }

    @Override
    public void step(float v_dt)
    {
        beforeStep();
        if(_player != null&&_monster==null)
        {
            _refreshDownTime = Math.max(_refreshDownTime - v_dt,0);
            if(_monster == null&&_refreshDownTime <= 0)
            {
                _monster = RefreshMonster();
                _monster.setTarget(_player);
                _monster.setSpot(this);
            }
        }
        if(_monster != null)
        {
            BehaviourCmd behaviourCmd =  _monster.aiAtkBehave();
            excuteCmd(behaviourCmd);
        }
        afterStep();
    }

    protected void beforeStep()
    {
        //清除死亡怪物
        if(_monster != null&&!_monster.isAlive())
        {
            _monster = null;
            monsterRefreshDownTime();
        }
    }

    protected void afterStep()
    {
        //如果玩家死亡，怪物立刻消失
        if(!_player.isAlive())
        {
            _monster = null;
            monsterRefreshDownTime();
        }
    }

    @Override
    public void excuteCmd(BehaviourCmd v_cmd)
    {
        BattleBehaviourCmd cmd = (BattleBehaviourCmd)v_cmd;
        if(cmd.getSubType()== BattleBehaviourCmd.SubType.idle)
        {
            return;
        }
        else if(cmd.getSubType()== BattleBehaviourCmd.SubType.castSkill)
        {
            ActiveEntity caster = cmd.getCaster();
            ActiveEntity target = cmd.getTarget();
            SkillEffectEntity see = caster.castSkill(cmd.getSkillLoc());
            if(see!=null&&target!=null)
            {
                target.suffer(see);
            }
        }
        else if(cmd.getSubType()== BattleBehaviourCmd.SubType.setTarget)
        {
            ActiveEntity caster = cmd.getCaster();
            ActiveEntity target = cmd.getTarget();
            caster.setTarget(target);
        }
        else if(cmd.getSubType()== BattleBehaviourCmd.SubType.clearTarget)
        {
            ActiveEntity caster = cmd.getCaster();
            caster.setTarget(null);
        }
    }

    public Monster RefreshMonster()
    {
        List<MonsterConfigData> monsterConfigDatas = MonsterConfig.get_instance().getData();
        int rd = (int)(Math.random() * monsterConfigDatas.size());
        Monster monster = new Monster();
        monster.initWithConfig(monsterConfigDatas.get(rd));
        return monster;
    }

    public void onPlayerLeaveOrDie()
    {
        if(_monster.isAlive())
        {
            _monster = null;
        }
        _player = null;
    }

    public void monsterRefreshDownTime()
    {
        _refreshDownTime = (float)(Math.random() * (REFRESH_TIME_MAX - REFRESH_TIME_MIN + 1) + REFRESH_TIME_MIN);
    }

    public Monster GetAMonsterTarget()
    {
        return _monster;
    }

    public Player GetPlayer()
    {
        return _player;
    }

    @Override
    public void onPlayerEnter(Player v_player)
    {
        monsterRefreshDownTime();
        _player = v_player;
    }
}
