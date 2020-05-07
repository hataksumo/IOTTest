package ps.zhifa.test.multiIotDevice.service;

import com.huaweicloud.sdk.iot.device.service.AbstractService;
import com.huaweicloud.sdk.iot.device.service.Property;
import lombok.Data;

@Data
public class RpgDemageService extends AbstractService
{
    @Property(name = "hp", writeable = false)
    int hp;
    @Property(name = "maxHp", writeable = false)
    int maxHp;
    @Property(name = "def", writeable = true)
    int def;
    @Property(name = "rebornTimes", writeable = false)
    int rebornTimes;
}
