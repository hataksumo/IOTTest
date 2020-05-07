package ps.zhifa.test.multiIotDevice.Entity;

import com.huaweicloud.sdk.iot.device.IoTDevice;
import ps.zhifa.test.multiIotDevice.Config.Data.AttrConfigData;
import ps.zhifa.test.multiIotDevice.Config.Data.DeviceConfigData;
import ps.zhifa.test.multiIotDevice.Config.Data.ItemConfigData;
import ps.zhifa.test.multiIotDevice.Config.Data.PlayerConfigData;
import ps.zhifa.test.multiIotDevice.Config.ItemConfig;
import ps.zhifa.test.multiIotDevice.common.Global;
import ps.zhifa.test.multiIotDevice.service.RpgChargeService;
import ps.zhifa.test.multiIotDevice.service.RpgDemageService;
import ps.zhifa.test.multiIotDevice.service.RpgDropService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends ActiveEntity
{
    IoTDevice _device;
    RpgDemageService _demageService;
    RpgChargeService _chargeService;
    RpgDropService _rpgService;
    Map<Integer,Integer> _bag;
    public Player()
    {
        _demageService = new RpgDemageService();
        _chargeService = new RpgChargeService();
        _rpgService = new RpgDropService();
        _attribute = new Attribute();
    }

    public void initWithConfig(PlayerConfigData v_cfg)
    {
        DeviceConfigData deviceConfigData = v_cfg.getDevice();
        AttrConfigData attrConfigData = v_cfg.getAttr();
        _device = new IoTDevice(Global.IOT_SERVER_address,deviceConfigData.getId(),deviceConfigData.getSecret());
        initWithAttrCfg(attrConfigData);
        initWithSkillCfg(v_cfg.getSkills());
    }

    public void initDevice(String v_url,String v_deviceId,String v_passwd)
    {
        _device = new IoTDevice(v_url,v_deviceId,v_passwd);
    }

    public void initBag()
    {
        _bag = new HashMap<>();
        List<ItemConfigData> itemConfigs = ItemConfig.get_instance().getData();
        for(int i=0;i<itemConfigs.size();i++)
        {
            ItemConfigData itemConfigData = itemConfigs.get(i);
            _bag.put(itemConfigData.getId(),itemConfigData.getIniNum());
        }
    }


}
