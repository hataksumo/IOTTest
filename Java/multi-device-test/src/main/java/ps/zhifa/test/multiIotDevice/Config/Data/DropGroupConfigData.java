package ps.zhifa.test.multiIotDevice.Config.Data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DropGroupConfigData
{
    int type;
    int weight;
    List<DropConfigData> data;
    public List<DropConfigData> random()
    {
        List<DropConfigData> rtn = new ArrayList<>();
        if(type == 1)
        {
            int rd = (int)(Math.random() * weight);
            for(int i=0;i<data.size();i++)
            {
                DropConfigData dropElement = data.get(i);
                if(rd<dropElement.getWeight())
                {
                    rtn.add(dropElement);
                    break;
                }
                rd -= dropElement.getWeight();
            }
        }else
        {
            int rd = (int)(Math.random() * weight);
            for(int i=0;i<data.size();i++)
            {
                DropConfigData dropElement = data.get(i);
                if(rd<dropElement.getWeight())
                {
                    rtn.add(dropElement);
                }
            }
        }
        return rtn;
    }
}
