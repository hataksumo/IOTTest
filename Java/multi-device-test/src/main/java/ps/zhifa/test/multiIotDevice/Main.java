package ps.zhifa.test.multiIotDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huaweicloud.sdk.iot.device.IoTDevice;
import ps.zhifa.test.multiIotDevice.common.FileUtils;
import ps.zhifa.test.multiIotDevice.service.RpgDemageService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String args[]){
        App app = new App();
        if(app.init())
        {
            try {
                app.start();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

}
