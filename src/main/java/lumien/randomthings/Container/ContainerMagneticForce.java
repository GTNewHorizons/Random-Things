package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Items.ModItems;

public class ContainerMagneticForce extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ConfigItems.magneticForce && player.getCurrentEquippedItem() != null
                && player.getCurrentEquippedItem().getItem() == ModItems.magneticForce;
    }
}
