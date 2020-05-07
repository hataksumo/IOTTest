package ps.zhifa.test.multiIotDevice.service;

import com.huaweicloud.sdk.iot.device.service.Property;
import lombok.Data;

@Data
public class RpgChargeService
{
    @Property(name = "chargeTotal", writeable = false)
    int chargeTotal;
}
