package lumien.randomthings.Container.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotDyeCrafting extends SlotCrafting {
    IInventory craftMatrix;

    public SlotDyeCrafting(
            EntityPlayer par1EntityPlayer,
            IInventory par2iInventory,
            IInventory par3iInventory,
            int par4,
            int par5,
            int par6) {
        super(par1EntityPlayer, par2iInventory, par3iInventory, par4, par5, par6);

        craftMatrix = par2iInventory;
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack) {}

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
            ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);

            if (itemstack1 != null) {
                this.craftMatrix.decrStackSize(i, 1);
            }
        }
    }
}
