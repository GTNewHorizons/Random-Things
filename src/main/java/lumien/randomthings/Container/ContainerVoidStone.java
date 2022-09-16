package lumien.randomthings.Container;

import lumien.randomthings.Container.Slots.SlotLocked;
import lumien.randomthings.Container.Slots.SlotVoid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerVoidStone extends Container {
    public ContainerVoidStone(InventoryPlayer playerInventory) {
        this.addSlotToContainer(new SlotVoid(80, 18));
        bindPlayerInventory(playerInventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        Slot slot = (Slot) this.inventorySlots.get(par2);
        if (slot != null) {
            slot.putStack(null);
        }
        return null;
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            if (inventoryPlayer.getStackInSlot(i) == inventoryPlayer.player.getCurrentEquippedItem()) {
                addSlotToContainer(new SlotLocked(inventoryPlayer, i, 8 + i * 18, 109));
            } else {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 109));
            }
        }
    }
}
