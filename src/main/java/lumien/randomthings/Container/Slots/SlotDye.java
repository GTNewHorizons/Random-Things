package lumien.randomthings.Container.Slots;

import lumien.randomthings.Library.Colors;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlotDye extends Slot {
    public SlotDye(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
        // TODO Auto-generated constructor stub
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
