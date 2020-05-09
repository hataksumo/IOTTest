package ps.zhifa.test.multiIotDevice.Entity.Drop;

import ps.zhifa.test.multiIotDevice.Config.Data.DropConfigData;
import ps.zhifa.test.multiIotDevice.Config.Data.DropGroupConfigData;
import ps.zhifa.test.multiIotDevice.common.ItemElementData;
import ps.zhifa.test.multiIotDevice.Config.DropConfig;

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

    public List<ItemElementData> getRandom(String v_key)
    {
        List<ItemElementData> rtn = new ArrayList<>();
        DropGroupConfigData group = DropConfig.get_instance().get(v_key);
        List<DropConfigData> drops = group.random();
        if(drops == null)
        {
            System.out.println("掉落组 "+v_key+" 没有找到数据");
            return null;
        }
        for(int i=0;i<drops.size();i++)
        {
            DropConfigData theData = drops.get(i);
            int num = getNum(theData.getNumMin(),theData.getNumMax());
            ItemElementData di = new ItemElementData();
            di.setItemId(theData.getItemId());
            di.setCnt(num);
            rtn.add(di);
        }
        return rtn;
    }
}
