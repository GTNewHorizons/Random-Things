package lumien.randomthings.Container;

import lumien.randomthings.TileEntities.TileEntityPlayerInterface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPlayerInterface extends Container {

    TileEntityPlayerInterface te;

    public ContainerPlayerInterface(TileEntityPlayerInterface te) {
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return te.isUseableByPlayer(entityplayer);
    }
}
