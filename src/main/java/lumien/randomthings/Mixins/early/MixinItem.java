package lumien.randomthings.Mixins.early;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Handler.CoreHandler;

@Mixin(Item.class)
public abstract class MixinItem {

    /**
     * @author Alexdoru
     * @reason Change color of item stacks
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public int getColorFromItemStack(ItemStack itemStack, int n) {
        return CoreHandler.getColorFromItemStack(itemStack, n);
    }
}
