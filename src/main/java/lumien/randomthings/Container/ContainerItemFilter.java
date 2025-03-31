package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import lumien.randomthings.Items.ItemFilter;

public class ContainerItemFilter extends ContainerItem {

    IInventory filterInventory;
    ItemStack filter;

    public ContainerItemFilter(ItemStack filter, IInventory playerInventory, IInventory filterInventory) {
        this.filterInventory = filterInventory;
        filterInventory.openInventory();
        for (int col = 0; col < 9; ++col) {
            addSlotToContainer(new Slot(filterInventory, col, 8 + col * 18, 18));
        }
        bindPlayerInventory((InventoryPlayer) playerInventory);
    }

    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player) {
        if (slotId < 9 && slotId >= 0) {
            if (player.inventory.getItemStack() != null) {
                ItemStack holdItem = player.inventory.getItemStack().copy();
                holdItem.stackSize = 0;
                filterInventory.setInventorySlotContents(slotId, holdItem);
            } else {
                filterInventory.setInventorySlotContents(slotId, null);
            }
            return null;
        } else {
            return super.slotClick(slotId, clickedButton, mode, player);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }

    @Override
    public boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4) {
        return false;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return entityplayer.getCurrentEquippedItem() != null
                && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemFilter
                && ItemFilter.getFilterType(entityplayer.getCurrentEquippedItem().getItemDamage())
                        == ItemFilter.FilterType.ITEM;
    }

    @Override
    public int getPlayerInventoryAndHotbarOffset() {
        return 9;
    }
}
