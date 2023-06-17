package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import lumien.randomthings.Container.Slots.SlotLocked;

abstract public class ContainerItem extends Container {

    abstract public int getPlayerInventoryAndHotbarOffset();

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

    protected boolean isSlotBlocked(int slotId, int clickedButton, int mode, EntityPlayer player) {
        if (slotId >= 0 && this.getSlot(slotId) != null && (this.getSlot(slotId).getStack() == player.getHeldItem())) {
            return true;
        }

        // keybind for moving from hotbar slot to hovered slot
        if (mode == 2 && clickedButton >= 0 && clickedButton < 9) {
            int hotbarIndex = this.getPlayerInventoryAndHotbarOffset() + (9 * 3) + clickedButton;
            Slot hotbarSlot = this.getSlot(hotbarIndex);

            return hotbarSlot instanceof SlotLocked;
        }

        return false;
    }

    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player) {
        if (isSlotBlocked(slotId, clickedButton, mode, player)) {
            return null;
        } else {
            return super.slotClick(slotId, clickedButton, mode, player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
