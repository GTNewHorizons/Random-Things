package lumien.randomthings.Items;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemSpectreArmor extends ItemArmor
{

	public ItemSpectreArmor(int armortype)
	{
		super(ModItems.spectreArmorMaterial, 3, armortype);

		this.setCreativeTab(RandomThings.creativeTab);
	}
	
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	return String.format("RandomThings:textures/models/armor/%s_layer_%d%s.png",
                "spectre", (slot == 2 ? 2 : 1), type == null ? "" : String.format("_%s", type));
    }
    
	
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		return 16777215;
	}
}
