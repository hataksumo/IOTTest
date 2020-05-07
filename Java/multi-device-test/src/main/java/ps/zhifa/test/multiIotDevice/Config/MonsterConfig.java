package ps.zhifa.test.multiIotDevice.Config;

import com.alibaba.fastjson.JSONArray;
import ps.zhifa.test.multiIotDevice.Config.Data.MonsterConfigData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterConfig
{
    static MonsterConfig _instance;
    public static MonsterConfig get_instance()
    {
        if(_instance == null)
        {
            _instance = new MonsterConfig();
        }
        return _instance;
    }
    List<MonsterConfigData> data;
    Map<Integer,MonsterConfigData> hashData;
    public void init(JSONArray v_arr)
    {
        data = new ArrayList<>(v_arr.size());
        hashData = new HashMap<>(v_arr.size());
        for(int i=0;i<v_arr.size();i++)
        {
            MonsterConfigData theMonsterData = v_arr.getObject(i,MonsterConfigData.class);
            data.add(theMonsterData);
            hashData.put(theMonsterData.getId(),theMonsterData);
        }
    }

    public List<MonsterConfigData> getData()
    {
        return data;
    }

    public MonsterConfigData get(int v_id)
    {
        MonsterConfigData rtn = hashData.get(v_id);
        if(rtn == null)
        {
            System.out.println("没有找到Id为"+v_id+"的怪物");
        }
        return rtn;
    }
}
