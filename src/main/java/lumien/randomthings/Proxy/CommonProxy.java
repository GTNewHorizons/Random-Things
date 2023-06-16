package lumien.randomthings.Proxy;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import lumien.randomthings.Entity.EntitySoul;
import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;

public class CommonProxy {

    public int itemCollectorRenderType;
    private static final MinecraftServer server = MinecraftServer.getServer();

    public boolean canBeCollidedWith(EntitySoul soul) {
        return false;
    }

    public boolean isBloodmoon() {
        return ServerBloodmoonHandler.INSTANCE.isBloodmoonActive();
    }

    public void setContainerProperty(int index, int value) {}

    public void registerRenderers() {}

    public void spawnPfeilParticle(double x, double y, double z, double motionX, double motionY, double motionZ) {}

    public void spawnColoredDust(double x, double y, double z, double motionX, double motionY, double motionZ,
            float red, float green, float blue) {}

    public void registerTickHandler() {}

    public ArrayList<String> getUsernameList() {
        ArrayList<String> players = new ArrayList<>();
        for (int i = 0; i < server.getConfigurationManager().playerEntityList.size(); ++i) {
            players.add(
                    ((EntityPlayerMP) server.getConfigurationManager().playerEntityList.get(i)).getCommandSenderName());
        }

        return players;
    }

    public void postInit() {}
}
