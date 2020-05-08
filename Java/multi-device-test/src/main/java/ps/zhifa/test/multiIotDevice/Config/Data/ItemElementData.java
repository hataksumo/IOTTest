package ps.zhifa.test.multiIotDevice.Config.Data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemElementData
{
    int itemId;
    int cnt;
    public static List<ItemElementData> formSingleList(int v_itemId, int v_cnt)
    {
        List<ItemElementData> rtn = new ArrayList<>();
        ItemElementData cost = new ItemElementData();
        cost.setItemId(v_itemId);
        cost.setCnt(v_cnt);
        rtn.add(cost);
        return rtn;
    }

}
