package ps.zhifa.test.multiIotDevice.Entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ps.zhifa.test.multiIotDevice.Config.Data.DropConfigData;
import ps.zhifa.test.multiIotDevice.Config.Data.DropGroupConfigData;
import ps.zhifa.test.multiIotDevice.Config.DropConfig;
import ps.zhifa.test.multiIotDevice.common.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class DropUtil
{
    static DropUtil _instance;

    public static DropUtil get_instance()
    {
        if(_instance == null)
        {
            _instance = new DropUtil();
        }
        return _instance;
    }

    int getNum(int v_min,int v_max)
    {
        return (int)(Math.random()*(v_max-v_min+1) + v_min);
    }

    public List<DropItem> getRandom(String v_key)
    {
        List<DropItem> rtn = new ArrayList<>();
        DropGroupConfigData group = DropConfig.get_instance().get(v_key);
        List<DropConfigData> drops = group.getData();
        for(int i=0;i<drops.size();i++)
        {
            DropConfigData theData = drops.get(i);
            int num = getNum(theData.getNumMin(),theData.getNumMax());
            DropItem di = new DropItem();
            di.setItemId(theData.getItemId());
            di.setNum(num);
            rtn.add(di);
        }
        return rtn;
    }
}
