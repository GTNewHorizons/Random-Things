package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import lumien.randomthings.Container.Slots.SlotFilter;

public class ContainerDropFilter extends ContainerItem {

    IInventory dropFilterInventory;

    public ContainerDropFilter(IInventory playerInventory, IInventory dropFilterInventory) {
        this.dropFilterInventory = dropFilterInventory;
        dropFilterInventory.openInventory();

        addSlotToContainer(new SlotFilter(dropFilterInventory, 0, 80, 18, 1));

        bindPlayerInventory((InventoryPlayer) playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < 1) {
                if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4) {
        boolean flag1 = false;
        int k = par2;

        if (par4) {
            k = par3 - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (par1ItemStack.isStackable()) {
            while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2)) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == par1ItemStack.getItem()
                        && (!par1ItemStack.getHasSubtypes()
                                || par1ItemStack.getItemDamage() == itemstack1.getItemDamage())
                        && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1)
                        && 1 > itemstack1.stackSize
                        && slot.isItemValid(par1ItemStack)) {
                    int l = itemstack1.stackSize + par1ItemStack.stackSize;

                    if (l <= par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (par4) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (par1ItemStack.stackSize > 0) {
            if (par4) {
                k = par3 - 1;
            } else {
                k = par2;
            }

            while (!par4 && k < par3 || par4 && k >= par2) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null && slot.isItemValid(par1ItemStack)) {
                    if (1 < par1ItemStack.stackSize) {
                        ItemStack copy = par1ItemStack.copy();
                        copy.stackSize = 1;
                        slot.putStack(copy);

                        par1ItemStack.stackSize -= 1;
                    } else {
                        slot.putStack(par1ItemStack.copy());
                        slot.onSlotChanged();
                        par1ItemStack.stackSize = 0;
                    }
                    flag1 = true;
                    break;
                }

                if (par4) {
                    --k;
                } else {
                    ++k;
                }
            }
        }
        return flag1;
    }

    @Override
    public int getPlayerInventoryAndHotbarOffset() {
        return 1;
    }
}
