package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerOnlineDetector extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
