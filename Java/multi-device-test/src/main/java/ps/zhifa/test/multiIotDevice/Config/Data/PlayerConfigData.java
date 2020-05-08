package ps.zhifa.test.multiIotDevice.Config.Data;

import lombok.Data;

@Data
public class PlayerConfigData
{
    String id;
    DeviceConfigData device;
    AttrConfigData attr;
    int[] skills;
    int rebornBuyHpBottleNum;
    int rebornBuyResurrectStone;
}
