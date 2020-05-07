package ps.zhifa.test.multiIotDevice.Entity;

import ps.zhifa.test.multiIotDevice.Config.Data.AttrConfigData;
import ps.zhifa.test.multiIotDevice.Config.Data.SkillConfigData;
import ps.zhifa.test.multiIotDevice.Config.SkillConfig;

public class ActiveEntity
{
    Attribute _attribute;
    Skill[] _skills;
    static final float GCD = 1;
    float _gcdDownTime;
    ActiveEntity _target;
    public void initWithAttrCfg(AttrConfigData v_cfg)
    {
        _attribute = new Attribute();
        _attribute.setAtk(v_cfg.getAtk());
        _attribute.setDef(v_cfg.getDef());
        _attribute.setMaxHp(v_cfg.getMaxHp());
        _attribute.setMaxMp(v_cfg.getMaxMp());
    }

    public void initWithSkillCfg(int[] v_skillIds)
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

    public SkillEffectEntity aiStep()
    {
        for(int i = 0; i< _skills.length; i++)
        {
            if(_skills[i].canCast(_attribute)&& _gcdDownTime <=0)
            {
                _gcdDownTime = GCD;
                return _skills[i].cast(_attribute);
            }
        }
        return null;
    }

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

    public void reborn()
    {
        _attribute.setHp(_attribute.getMaxHp());
        _attribute.setMp(_attribute.getMp());
    }
}
