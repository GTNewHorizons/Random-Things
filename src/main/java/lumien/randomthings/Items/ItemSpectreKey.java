package lumien.randomthings.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.RandomThings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpectreKey extends ItemBase {

    public ItemSpectreKey() {
        super("spectreKey");
        this.setFull3D();
        this.setMaxStackSize(1);
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        RenderUtils.enableDefaultBlending();

        return 16777215;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (player.worldObj.isRemote && count < 60) {
            EntityFX particle;
            float t = 1F / 255F;
            if (Minecraft.getMinecraft().thePlayer == player) {
                for (int i = 0; i < (60 - count); i++) {
                    particle = new EntitySmokeFX(
                            player.worldObj,
                            player.posX + Math.random() * 2 - 1,
                            player.posY - 1 + Math.random(),
                            player.posZ + Math.random() * 2 - 1,
                            0,
                            0,
                            0);

                    particle.setRBGColorF(t * 152F, t * 245F, t * 255F);
                    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                }
            } else {
                for (int i = 0; i < (60 - count); i++) {
                    particle = new EntitySmokeFX(
                            player.worldObj,
                            player.posX + Math.random() * 2 - 1,
                            player.posY + 0.5 + Math.random(),
                            player.posZ + Math.random() * 2 - 1,
                            0,
                            0,
                            0);
                    particle.setRBGColorF(t * 152F, t * 245F, t * 255F);
                    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                }
            }
        }
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote) {
            if (par2World.provider.dimensionId != Settings.SPECTRE_DIMENSON_ID) {
                RandomThings.instance.spectreHandler.teleportPlayerToSpectreWorld((EntityPlayerMP) par3EntityPlayer);
            } else {
                RandomThings.instance.spectreHandler.teleportPlayerOutOfSpectreWorld((EntityPlayerMP) par3EntityPlayer);
            }
        }
        return par1ItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return Minecraft.getMinecraft().thePlayer.worldObj.provider.dimensionId == Settings.SPECTRE_DIMENSON_ID;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 100;
    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
}
