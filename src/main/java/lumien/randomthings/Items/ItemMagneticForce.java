package lumien.randomthings.Items;

import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.RandomThings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMagneticForce extends ItemBase {

    double r = 0;

    public ItemMagneticForce() {
        super("magneticForce");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote) {
            par3EntityPlayer.openGui(
                    RandomThings.instance,
                    GuiIds.MAGNETIC_FORCE,
                    par2World,
                    (int) par3EntityPlayer.posX,
                    (int) par3EntityPlayer.posY,
                    (int) par3EntityPlayer.posZ);
        }
        return par1ItemStack;
    }
}
