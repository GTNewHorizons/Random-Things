package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.server.MinecraftServer;

import lumien.randomthings.RandomThings;

public class ContainerOpSpectreKey extends Container {

    boolean send = false;

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            if (!send) {
                send = true;
                RandomThings.instance.spectreHandler.sendData(player);
            }
            return MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
        }

        return true;
    }
}
