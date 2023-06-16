package lumien.randomthings.Container.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import lumien.randomthings.Library.Colors;

public class SlotDye extends Slot {

    public SlotDye(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        int[] oreDictIds = OreDictionary.getOreIDs(par1ItemStack);
        for (int i : oreDictIds) {
            for (String s : Colors.oreDictDyes) {
                int dyeID = OreDictionary.getOreID(s);
                if (dyeID == i) {
                    return true;
                }
            }
        }
        return false;
    }
}
