package ps.zhifa.test.multiIotDevice.Config;

import com.alibaba.fastjson.JSONArray;
import ps.zhifa.test.multiIotDevice.Config.Data.SkillConfigData;

import java.util.HashMap;
import java.util.Map;

public class SkillConfig
{
    static SkillConfig _instance;
    Map<Integer,SkillConfigData> _datas;
    public static SkillConfig get_instance()
    {
        if(_instance == null)
        {
            _instance = new SkillConfig();
        }
        return _instance;
    }
    public void init(JSONArray v_cfgData)
    {
        _datas = new HashMap<>(v_cfgData.size());
        for(int i=0;i<v_cfgData.size();i++)
        {
            SkillConfigData data =  v_cfgData.getObject(i,SkillConfigData.class);
            _datas.put(data.getId(),data);
        }
    }

    public SkillConfigData get(Integer v_id)
    {
        SkillConfigData rtn = _datas.get(v_id);
        if(rtn == null)
        {
            System.out.println("找不到技能Id: "+v_id);
            return null;
        }
        return rtn;
    }

}
