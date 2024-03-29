package lumien.randomthings.Container.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SlotDyeable extends Slot {

    public SlotDyeable(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isItemValid(ItemStack par1ItemStack) {
        if (par1ItemStack.getItem() instanceof ItemBlock) {
            return false;
        }
        return par1ItemStack.getItem() != null;
    }
}
