package ps.zhifa.test.multiIotDevice.Entity;

import ps.zhifa.test.multiIotDevice.Config.Data.SkillConfigData;

public class Skill
{
    protected SkillConfigData _cfgData;
    protected float curCalmDownTime;
    public void step(float v_dt)
    {
        curCalmDownTime = Math.max(curCalmDownTime - v_dt,0);
    }


    public Skill(SkillConfigData v_cfgData)
    {
        _cfgData = v_cfgData;
    }

    public boolean canCast(Attribute v_casterAttr)
    {
        return curCalmDownTime <=0 && v_casterAttr.getMp() >= _cfgData.getCost();
    }

    public SkillEffectEntity cast(Attribute v_casterAttr)
    {
        SkillEffectEntity rtn = new SkillEffectEntity();
        rtn.setSkillOrgDmg(v_casterAttr.atk * _cfgData.getSkillFac() + _cfgData.getSkillBase());
        curCalmDownTime = _cfgData.getCd();
        int mana = v_casterAttr.getMp() - _cfgData.getCost();
        v_casterAttr.setMp(mana);
        return rtn;
    }
}
