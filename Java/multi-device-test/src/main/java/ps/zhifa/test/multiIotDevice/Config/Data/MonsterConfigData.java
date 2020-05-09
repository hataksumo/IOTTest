package ps.zhifa.test.multiIotDevice.Config.Data;

import lombok.Data;

@Data
public class MonsterConfigData
{
    int id;
    String name;
    AttrConfigData attr;
    int[] skills;
    String[] drops;
}
