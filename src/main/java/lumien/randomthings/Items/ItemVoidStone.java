package lumien.randomthings.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.RandomThings;

public class ItemVoidStone extends ItemBase {

    public ItemVoidStone() {
        super("voidStone");
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote) {
            par3EntityPlayer.openGui(
                    RandomThings.instance,
                    GuiIds.VOID_STONE,
                    par2World,
                    (int) par3EntityPlayer.posX,
                    (int) par3EntityPlayer.posY,
                    (int) par3EntityPlayer.posZ);
        }

        return par1ItemStack;
    }
}
