package lumien.randomthings.Items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Library.Colors;
import lumien.randomthings.RandomThings;

public class ItemSpectreArmor extends ItemArmor {

    public ItemSpectreArmor(int armortype) {
        super(ModItems.spectreArmorMaterial, 3, armortype);

        this.setCreativeTab(RandomThings.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par3List, boolean par4) {
        if (player != null) {
            ItemStack helmet = player.getCurrentArmor(0);
            ItemStack chestplate = player.getCurrentArmor(1);
            ItemStack leggings = player.getCurrentArmor(2);
            ItemStack boots = player.getCurrentArmor(3);

            if (helmet != null && chestplate != null && leggings != null && boots != null) {
                if (helmet.getItem() instanceof ItemSpectreArmor && chestplate.getItem() instanceof ItemSpectreArmor
                        && leggings.getItem() instanceof ItemSpectreArmor
                        && boots.getItem() instanceof ItemSpectreArmor) {
                    if (par1ItemStack == helmet || par1ItemStack == chestplate
                            || par1ItemStack == leggings
                            || par1ItemStack == boots) {
                        par3List.add(Colors.DARK_AQUA + I18n.format("text.miscellaneous.setBonus"));
                        par3List.add(Colors.AQUA + "- Damage done to enemies creates orbs");
                        par3List.add(Colors.AQUA + "that heal the player with the lowest health nearby.");
                        par3List.add(Colors.AQUA + "- Semi Transparent Character Model");
                    }
                }
            }
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        return String.format(
                "RandomThings:textures/models/armor/%s_layer_%d%s.png",
                "spectre",
                (slot == 2 ? 2 : 1),
                type == null ? "" : String.format("_%s", type));
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        return 16777215;
    }
}
