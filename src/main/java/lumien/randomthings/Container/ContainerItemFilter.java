package lumien.randomthings.Container;

import lumien.randomthings.Container.Slots.SlotLocked;
import lumien.randomthings.Items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerItemFilter extends Container {
    IInventory filterInventory;
    ItemStack filter;

    public ContainerItemFilter(ItemStack filter, IInventory playerInventory, IInventory filterInventory) {
        this.filterInventory = filterInventory;
        filterInventory.openInventory();

        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < 9; ++col) {
                addSlotToContainer(new Slot(filterInventory, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        bindPlayerInventory((InventoryPlayer) playerInventory);
    }

    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
        if (par1 < 9 && par1 >= 0) {
            if (par4EntityPlayer.inventory.getItemStack() != null) {
                ItemStack holdItem = par4EntityPlayer.inventory.getItemStack().copy();
                holdItem.stackSize = 1;
                filterInventory.setInventorySlotContents(par1, holdItem);
            } else {
                filterInventory.setInventorySlotContents(par1, null);
            }
            return null;
        } else {
            return super.slotClick(par1, par2, par3, par4EntityPlayer);
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

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return entityplayer.getCurrentEquippedItem() != null
                && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemFilter
                && ItemFilter.getFilterType(
                                entityplayer.getCurrentEquippedItem().getItemDamage())
                        == ItemFilter.FilterType.ITEM;
    }
}
