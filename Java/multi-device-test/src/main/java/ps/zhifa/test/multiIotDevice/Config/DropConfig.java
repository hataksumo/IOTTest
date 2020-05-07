package ps.zhifa.test.multiIotDevice.Config;

import com.alibaba.fastjson.JSONObject;
import ps.zhifa.test.multiIotDevice.Config.Data.DropGroupConfigData;

import java.util.HashMap;
import java.util.Map;

public class DropConfig
{
    protected Map<String, DropGroupConfigData> _data;
    static DropConfig _instance;
    public static DropConfig get_instance()
    {
        if(_instance == null)
        {
            _instance = new DropConfig();
        }
        return _instance;
    }
    public void init(JSONObject v_cfg)
    {
        _data = new HashMap<>(v_cfg.size());
        for(String key : v_cfg.keySet())
        {
            DropGroupConfigData groupData = v_cfg.getObject(key, DropGroupConfigData.class);
            _data.put(key,groupData);
        }
    }

    public DropGroupConfigData get(String v_key)
    {
        return _data.get(v_key);
    }
}
