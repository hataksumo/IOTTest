package ps.zhifa.test.multiIotDevice.Config.Data;

import lombok.Data;

@Data
public class ItemConfigData
{
    int id;
    String name;
    int iniNum;
    int quality;
    float[] params;
    int price;
}
