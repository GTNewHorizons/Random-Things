package lumien.randomthings.Items;

import java.util.Random;

import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCreativeGrower extends ItemCreative
{
	int TICKS = 300;
	
	public ItemCreativeGrower()
	{
		super("creativeGrower");
	}
	
	@Override
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
