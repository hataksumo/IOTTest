package ps.zhifa.test.multiIotDevice.Entity;

import ps.zhifa.test.multiIotDevice.Config.Data.AttrConfigData;
import ps.zhifa.test.multiIotDevice.Config.Data.SkillConfigData;
import ps.zhifa.test.multiIotDevice.Config.SkillConfig;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BattleBehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Skill.Skill;
import ps.zhifa.test.multiIotDevice.Entity.Skill.SkillEffectEntity;
import ps.zhifa.test.multiIotDevice.Entity.Spot.Spot;

public abstract class ActiveEntity
{
    protected Attribute _attribute;
    protected Skill[] _skills;
    static final float GCD = 1;
    protected float _gcdDownTime;
    protected ActiveEntity _target;
    protected Spot _spot;

    protected void initWithAttrCfg(AttrConfigData v_cfg)
    {
        _attribute = new Attribute();
        _attribute.setAtk(v_cfg.getAtk());
        _attribute.setDef(v_cfg.getDef());
        _attribute.setMaxHp(v_cfg.getMaxHp());
        _attribute.setMaxMp(v_cfg.getMaxMp());
    }

    protected void initWithSkillCfg(int[] v_skillIds)
    {
        _skills = new Skill[v_skillIds.length];
        SkillConfig skillConfig = SkillConfig.get_instance();
        for(int i=0;i<v_skillIds.length;i++)
        {
            SkillConfigData skillConfigData = skillConfig.get(i);
            _skills[i] = new Skill(skillConfigData);
        }
    }

    public void step(float v_dt)
    {
        for(int i = 0; i< _skills.length; i++)
        {
            _skills[i].step(v_dt);
        }
        _gcdDownTime = Math.max(_gcdDownTime - v_dt,0);
    }

    public void setTarget(ActiveEntity v_target)
    {
        _target = v_target;
    }

    public ActiveEntity getTarget(){return _target;}

    public SkillEffectEntity castSkill(int v_skillLoc)
    {
        if(v_skillLoc >= _skills.length)
        {
            System.out.println("v_skillLoc "+v_skillLoc+" 不合法");
            return null;
        }
        Skill skill = _skills[v_skillLoc];
        if(skill.canCast(_attribute)&&_gcdDownTime <=0)
        {
            _gcdDownTime = GCD;
            return _skills[v_skillLoc].cast(_attribute);
        }
        return null;
    }

    public BehaviourCmd aiAtkBehave()
    {
        BattleBehaviourCmd rtn = new BattleBehaviourCmd();
        rtn.setSubType(BattleBehaviourCmd.SubType.idle);
        rtn.setCaster(this);
        if(_target == null)
        {
            ActiveEntity target = findTarget();
            if(target != null)
            {
                rtn.setSubType(BattleBehaviourCmd.SubType.setTarget);
                rtn.setTarget(target);
                return rtn;
            }
            else
            {
                return rtn;
            }
        }
        else if(!_target.isAlive())
        {
            rtn.setSubType(BattleBehaviourCmd.SubType.clearTarget);
            return rtn;
        }
        for(int i = 0; i< _skills.length; i++)
        {
            if(_skills[i].canCast(_attribute)&& _gcdDownTime <=0)
            {
                rtn.setSubType(BattleBehaviourCmd.SubType.castSkill);
                rtn.setSkillLoc(i);
                rtn.setTarget(_target);
                return rtn;
            }
        }
        return rtn;
    }

    public abstract ActiveEntity findTarget();

    public boolean isAlive()
    {
        return _attribute.getHp()>0;
    }

    public boolean suffer(SkillEffectEntity v_skillEffectEntity)
    {
        float orgDmg = v_skillEffectEntity.getSkillOrgDmg();
        float realDmg = orgDmg * (100/(100+_attribute.getDef()));
        float newHp = _attribute.getHp() - realDmg;
        _attribute.setHp((int)newHp);
        return newHp > 0;
    }

    public void getHeal(SkillEffectEntity v_skillEffectEntity)
    {
        float newHp = Math.min(_attribute.getHp() + v_skillEffectEntity.getSkillOrgDmg(),_attribute.getMaxHp());
        _attribute.setHp((int)newHp);
    }


    public void reborn()
    {
        _attribute.setHp(_attribute.getMaxHp());
        _attribute.setMp(_attribute.getMp());
        //_onReborn();
    }

    public void setSpot(Spot v_spot)
    {
        _spot = v_spot;
    }

    public Spot getSpot()
    {
        return _spot;
    }

    //protected abstract void _onReborn();

}
