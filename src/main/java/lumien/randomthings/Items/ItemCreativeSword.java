package lumien.randomthings.Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemCreativeSword extends ItemCreative {
    int DAMAGE = 100000;

    public ItemCreativeSword() {
        super("creativeSword");
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityLiving) {
            ((EntityLiving) entity).attackEntityFrom(DamageSource.causePlayerDamage(player), DAMAGE);
        }
        return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        return true;
    }
}
