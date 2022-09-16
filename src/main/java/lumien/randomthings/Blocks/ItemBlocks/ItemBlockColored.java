package lumien.randomthings.Blocks.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class ItemBlockColored extends ItemColored {
    public ItemBlockColored(Block block) {
        super(block, true);
    }

    @Override
    public String getUnlocalizedName(ItemStack is) {
        return super.getUnlocalizedName() + "." + ItemDye.field_150923_a[is.getItemDamage()];
    }
}
