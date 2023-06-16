package lumien.randomthings.Container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import lumien.randomthings.Container.Slots.SlotFilter;
import lumien.randomthings.Library.Interfaces.IContainerWithProperties;
import lumien.randomthings.Network.Messages.MessageContainerProperty;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnderEnergyDistributor;

public class ContainerEnderEnergyDistributor extends Container implements IContainerWithProperties {

    TileEntityEnderEnergyDistributor te;
    int oldBuffer;
    int oldEnergyDistributedLastTick;
    int oldMachinesConnected;
    public int energyDistributedLastTick;
    public int buffer;
    public int machinesConnected;

    public ContainerEnderEnergyDistributor(InventoryPlayer inventoryPlayer, TileEntityEnderEnergyDistributor te) {
        this.te = te;
        this.oldBuffer = 0;
        this.buffer = 0;

        for (int i = 0; i < 8; i++) {
            addSlotToContainer(new SlotFilter(te.getInventory(), i, 17 + i * 18, 46, 3));
        }

        bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < 8) {
                if (!this.mergeItemStack(itemstack1, 8, 41, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 8, false)) {
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

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 77 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 135));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);

        EntityPlayerMP player = (EntityPlayerMP) crafting;

        PacketHandler.INSTANCE
                .sendTo(new MessageContainerProperty(0, this.te.getEnergyStored(ForgeDirection.DOWN)), player);
        PacketHandler.INSTANCE.sendTo(new MessageContainerProperty(1, this.te.getEnergyDistributedLastTick()), player);
        PacketHandler.INSTANCE.sendTo(new MessageContainerProperty(2, this.te.getMachinesConnected()), player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            EntityPlayerMP player = (EntityPlayerMP) icrafting;
            if (this.oldBuffer != this.te.getEnergyStored(ForgeDirection.DOWN)) {
                PacketHandler.INSTANCE
                        .sendTo(new MessageContainerProperty(0, this.te.getEnergyStored(ForgeDirection.DOWN)), player);
                this.oldBuffer = te.getEnergyStored(ForgeDirection.DOWN);
            }

            if (this.oldEnergyDistributedLastTick != this.te.getEnergyDistributedLastTick()) {
                PacketHandler.INSTANCE
                        .sendTo(new MessageContainerProperty(1, this.te.getEnergyDistributedLastTick()), player);
                this.oldEnergyDistributedLastTick = te.getEnergyStored(ForgeDirection.DOWN);
            }

            if (this.oldMachinesConnected != this.te.getMachinesConnected()) {
                PacketHandler.INSTANCE.sendTo(new MessageContainerProperty(2, this.te.getMachinesConnected()), player);
                this.oldMachinesConnected = te.getMachinesConnected();
            }
        }
    }

    @Override
    public void setValue(int index, int value) {
        switch (index) {
            case 0:
                this.buffer = value;
                break;
            case 1:
                this.energyDistributedLastTick = value;
                break;
            case 2:
                this.machinesConnected = value;
                break;
        }
    }
}
