package lumien.randomthings.Container.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot {

    public SlotLocked(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return false;
    }

    @Override
    public void putStack(ItemStack itemStack) {}

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {}

    @Override
    public boolean getHasStack() {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int i) {
        return null;
    }
}
