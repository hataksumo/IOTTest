package ps.zhifa.test.multiIotDevice.service;

import com.huaweicloud.sdk.iot.device.client.requests.CommandRsp;
import com.huaweicloud.sdk.iot.device.service.AbstractService;
import com.huaweicloud.sdk.iot.device.service.DeviceCommand;
import com.huaweicloud.sdk.iot.device.service.Property;
import lombok.Data;
import ps.zhifa.test.multiIotDevice.Entity.Player;

import java.util.Map;

public class RpgDemageService extends AbstractService
{
    Player _player;
    public void setPlayer(Player v_player)
    {
        _player = v_player;
    }
    @Property(name = "name", writeable = false)
    String name;
    @Property(name = "hp", writeable = false)
    int hp;
    @Property(name = "maxHp", writeable = false)
    int maxHp;
    @Property(name = "def", writeable = true)
    int def;
    @Property(name = "atk", writeable = true)
    int atk;
    @Property(name = "rebornTimes", writeable = false)
    int rebornTimes;
    @Property(name = "sufferDemage", writeable = false)
    int sufferDemage;
    @Property(name = "eatHpBottle", writeable = false)
    int eatHpBottle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getRebornTimes() {
        return rebornTimes;
    }

    public void setRebornTimes(int rebornTimes) {
        this.rebornTimes = rebornTimes;
    }

    public int getSufferDemage() {
        return sufferDemage;
    }

    public void setSufferDemage(int sufferDemage) {
        this.sufferDemage = sufferDemage;
    }

    public int getEatHpBottle() {
        return eatHpBottle;
    }

    public void setEatHpBottle(int eatHpBottle) {
        this.eatHpBottle = eatHpBottle;
    }

    @DeviceCommand(name = "eatHpBottle")
    public CommandRsp alarm(Map<String, Object> paras) {
        _player.eatBottle();
        return new CommandRsp(0);
    }
}
