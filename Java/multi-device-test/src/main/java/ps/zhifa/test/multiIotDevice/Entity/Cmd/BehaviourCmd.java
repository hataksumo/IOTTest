package ps.zhifa.test.multiIotDevice.Entity.Cmd;


public abstract class BehaviourCmd
{
    public enum Type
    {
        BattleCmd,
        EconomyCmd
    }
    public abstract Type getType();
}