package lumien.randomthings.Container;

import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerMagneticForce extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ConfigItems.magneticForce && player.getCurrentEquippedItem() != null
                && player.getCurrentEquippedItem().getItem() == ModItems.magneticForce;
    }
}
