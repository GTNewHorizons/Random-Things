package lumien.randomthings.Items;

import java.util.Random;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.ClientTickHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCreativeGrower extends ItemCreative
{
	int TICKS = 300;
	
	public ItemCreativeGrower()
	{
		this.setUnlocalizedName("creativeGrower");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:creativeGrower");
		
		GameRegistry.registerItem(this, "creativeGrower");
	}
	
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		Random rng = new Random();
		for (int i=0;i<TICKS;i++)
		{
			if (!par3World.isAirBlock(par4, par5, par6))
			{
				Block b = par3World.getBlock(par4, par5, par6);
				b.updateTick(par3World, par4, par5, par6,rng);
			}
		}
		return true;
	}
}
