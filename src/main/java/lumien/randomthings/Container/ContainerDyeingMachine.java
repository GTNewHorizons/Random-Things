package lumien.randomthings.Container;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Container.Slots.SlotDye;
import lumien.randomthings.Container.Slots.SlotDyeCrafting;
import lumien.randomthings.Container.Slots.SlotDyeable;
import lumien.randomthings.Library.Colors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ContainerDyeingMachine extends Container {
    IInventory ingredients = new InventoryCrafting(this, 2, 1);
    IInventory result = new InventoryCraftResult();

    private final int posX;
    private final int posY;
    private final int posZ;
    private final World worldObj;

    public ContainerDyeingMachine(InventoryPlayer playerInventory, World worldObj, int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.worldObj = worldObj;

        this.addSlotToContainer(new SlotDyeable(ingredients, 0, 27, 22));
        this.addSlotToContainer(new SlotDye(ingredients, 1, 76, 22));
        this.addSlotToContainer(new SlotDyeCrafting(playerInventory.player, ingredients, result, 2, 134, 22));
        bindPlayerInventory(playerInventory);
    }

    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        ItemStack toDye = par1IInventory.getStackInSlot(0);
        ItemStack dye = par1IInventory.getStackInSlot(1);

        if (toDye != null && dye != null) {
            int dyeColor = Colors.getDyeColor(dye);
            ItemStack copy = toDye.copy();

            if (copy.stackTagCompound == null) {
                copy.stackTagCompound = new NBTTagCompound();
            }
            copy.stackSize = 1;
            copy.stackTagCompound.setInteger("customRTColor", dyeColor);
            this.result.setInventorySlotContents(0, copy);
        } else if (toDye != null && dye == null) {
            ItemStack copy = toDye.copy();
            copy.stackSize = 1;
            if (copy.stackTagCompound != null) {
                if (copy.stackTagCompound.hasKey("customRTColor")) {
                    copy.stackTagCompound.removeTag("customRTColor");
                }

                if (copy.stackTagCompound.hasNoTags()) {
                    copy.stackTagCompound = null;
                }
                this.result.setInventorySlotContents(0, copy);
            } else if (copy.stackTagCompound == null) {
                this.result.setInventorySlotContents(0, null);
            }
        } else {
            this.result.setInventorySlotContents(0, null);
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote) {
            for (int i = 0; i < 2; ++i) {
                ItemStack itemstack = this.ingredients.getStackInSlotOnClosing(i);

                if (itemstack != null) {
                    par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 59 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 117));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlock(this.posX, this.posY, this.posZ) == ModBlocks.dyeingMachine
                && par1EntityPlayer.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < 3) {
                if (!this.mergeItemStack(itemstack1, 3, 37, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
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

                if (itemstack1 != null
                        && itemstack1.getItem() == par1ItemStack.getItem()
                        && (!par1ItemStack.getHasSubtypes()
                                || par1ItemStack.getItemDamage() == itemstack1.getItemDamage())
                        && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1)
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
}
