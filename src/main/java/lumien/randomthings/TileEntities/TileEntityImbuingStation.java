package lumien.randomthings.TileEntities;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Handler.ImbuingStation.ImbuingRecipeHandler;
import lumien.randomthings.Library.InventoryUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityImbuingStation extends TileEntity implements IInventory {

    InventoryBasic inventory;
    public int imbuingProgress;

    ItemStack currentOutput;

    public TileEntityImbuingStation() {
        inventory = new InventoryBasic("Imbuing Station", false, 5);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("imbuingProgress", imbuingProgress);
        InventoryUtils.writeInventoryToNBT(inventory, nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.imbuingProgress = nbt.getInteger("imbuingProgress");
        InventoryUtils.readInventoryFromNBT(inventory, nbt);
        this.currentOutput = ImbuingRecipeHandler.getRecipeOutput(inventory);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            ItemStack validOutput = ImbuingRecipeHandler.getRecipeOutput(inventory);
            if (validOutput != currentOutput && canHandleOutput(validOutput)) {
                this.imbuingProgress = 0;
                currentOutput = validOutput;
            }

            if (this.currentOutput != null) {
                this.imbuingProgress++;
                if (this.imbuingProgress >= Settings.IMBUING_LENGTH) {
                    imbuingProgress = 0;
                    imbue();
                }
            } else {
                this.imbuingProgress = 0;
            }
        }
    }

    private boolean canHandleOutput(ItemStack validOutput) {
        if (validOutput == null || inventory.getStackInSlot(4) == null) {
            return true;
        } else {
            ItemStack currentInOutput = inventory.getStackInSlot(4);

            if (!InventoryUtils.areItemStackContentEqual(currentInOutput, validOutput)) {
                return false;
            } else {
                return currentInOutput.stackSize + validOutput.stackSize <= currentInOutput.getMaxStackSize();
            }
        }
    }

    private void imbue() {
        // Set Output
        if (this.inventory.getStackInSlot(4) == null) {
            this.inventory.setInventorySlotContents(4, currentOutput.copy());
        } else {
            this.inventory.decrStackSize(4, -currentOutput.stackSize);
        }

        // Decrease Ingredients
        for (int slot = 0; slot < this.inventory.getSizeInventory() - 1; slot++) {
            this.inventory.decrStackSize(slot, 1);
        }
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
