package lumien.randomthings.Items;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGinto extends Item
{
	IIcon[] icons;
	public ItemGinto()
	{
		this.setCreativeTab(RandomThings.creativeTab);
		this.setUnlocalizedName("ginto");
		this.setHasSubtypes(true);
		
		GameRegistry.registerItem(this, "ginto");
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List itemList)
    {
        itemList.add(new ItemStack(item, 1, 0));
        itemList.add(new ItemStack(item, 1, 1));
    }
    
    @Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		return 16777215;
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
		icons = new IIcon[2];
		
		icons[0] = ir.registerIcon("RandomThings:ginto_empty");
		icons[1] = ir.registerIcon("RandomThings:ginto_filled");
    }
	
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
    	if (damage<2)
    	{
    		return icons[damage];
    	}
    	else
    	{
    		return icons[0];
    	}
    }
}
