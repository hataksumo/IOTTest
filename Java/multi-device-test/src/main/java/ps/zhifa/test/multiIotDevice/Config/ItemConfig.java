package ps.zhifa.test.multiIotDevice.Config;

import com.alibaba.fastjson.JSONArray;
import ps.zhifa.test.multiIotDevice.Config.Data.ItemConfigData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemConfig
{
    static ItemConfig _instance;
    public static ItemConfig get_instance()
    {
        if(_instance == null)
        {
            _instance = new ItemConfig();
        }
        return _instance;
    }
    List<ItemConfigData> data;
    Map<Integer, ItemConfigData> hashData;
    public void init(JSONArray v_cfg)
    {
        data = new ArrayList<>(v_cfg.size());
        hashData = new HashMap<>(v_cfg.size());
        for(int i=0;i<v_cfg.size();i++)
        {
            ItemConfigData theData = v_cfg.getObject(i,ItemConfigData.class);
            data.add(theData);
            hashData.put(theData.getId(),theData);
        }
    }
    public List<ItemConfigData> getData()
    {
        return data;
    }
    public ItemConfigData get(Integer v_id)
    {
        ItemConfigData rtn = hashData.get(v_id);
        if(rtn == null)
        {
            System.out.println("没有找到Id为"+v_id+"的道具");
        }
        return rtn;
    }
}
