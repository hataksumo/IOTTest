package ps.zhifa.test.multiIotDevice.Config;

import com.alibaba.fastjson.JSONArray;
import ps.zhifa.test.multiIotDevice.Config.Data.PlayerConfigData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerConfig
{
    static PlayerConfig _instance;
    public static PlayerConfig get_instance()
    {
        if(_instance == null)
        {
            _instance = new PlayerConfig();
        }
        return _instance;
    }

    List<PlayerConfigData> data;
    Map<String,PlayerConfigData> hashData;

    public void init(JSONArray v_arr)
    {
        data = new ArrayList<>(v_arr.size());
        hashData = new HashMap<>(v_arr.size());
        for(int i=0;i<v_arr.size();i++)
        {
            PlayerConfigData theData = v_arr.getObject(i,PlayerConfigData.class);
            data.add(theData);
            hashData.put(theData.getId(),theData);
        }
    }

    public List<PlayerConfigData> getData()
    {
        return data;
    }

    public PlayerConfigData get(String v_id)
    {
        PlayerConfigData rtn = hashData.get(v_id);
        if(rtn == null)
        {
            System.out.println("没有找到名为"+v_id+"的玩家");
        }
        return rtn;
    }
}
