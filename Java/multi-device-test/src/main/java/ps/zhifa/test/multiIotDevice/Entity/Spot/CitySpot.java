package ps.zhifa.test.multiIotDevice.Entity.Spot;

import ps.zhifa.test.multiIotDevice.Entity.Cmd.BehaviourCmd;
import ps.zhifa.test.multiIotDevice.Entity.Player;

public class CitySpot extends Spot{
    public CitySpot(String v_name) {
        super(v_name);
    }

    @Override
    public Type getType() {
        return Type.City;
    }

    @Override
    public void onPlayerEnter(Player v_player) {

    }

    @Override
    public void onPlayerLeave() {

    }

    @Override
    public void step(float v_dt) {

    }

    @Override
    public void excuteCmd(BehaviourCmd v_cmd)
    {

    }
}
