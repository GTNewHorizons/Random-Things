package lumien.randomthings.Container.Slots;

import lumien.randomthings.Items.ItemFilter;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFilter extends Slot {

    int type;

    public SlotFilter(IInventory par1iInventory, int par2, int par3, int par4, int type) {
        super(par1iInventory, par2, par3, par4);

        this.type = type;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() instanceof ItemFilter && par1ItemStack.getItemDamage() == type;
    }
}
