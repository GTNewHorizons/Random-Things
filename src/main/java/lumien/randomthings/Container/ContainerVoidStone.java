package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import lumien.randomthings.Container.Slots.SlotVoid;

public class ContainerVoidStone extends ContainerItem {

    public ContainerVoidStone(InventoryPlayer playerInventory) {
        this.addSlotToContainer(new SlotVoid(80, 18));
        bindPlayerInventory(playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        Slot slot = (Slot) this.inventorySlots.get(par2);
        if (slot != null) {
            slot.putStack(null);
        }
        return null;
    }

    @Override
    public int getPlayerInventoryAndHotbarOffset() {
        return 1;
    }
}
