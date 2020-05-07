package ps.zhifa.test.multiIotDevice.service;

import com.huaweicloud.sdk.iot.device.service.Property;
import lombok.Data;

@Data
public class RpgDropService
{
    @Property(name = "itemName", writeable = false)
    String itemName;
    @Property(name = "cnt", writeable = false)
    int cnt;
    @Property(name = "quality", writeable = false)
    int quality;
}
