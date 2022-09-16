package lumien.randomthings.Core;

import lumien.randomthings.Items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RTCreativeTab extends CreativeTabs {

    public RTCreativeTab() {
        super("Random Things");
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(ModItems.ingredients, 0);
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.ingredients;
    }
}
