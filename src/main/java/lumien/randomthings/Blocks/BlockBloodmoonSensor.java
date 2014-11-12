package lumien.randomthings.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockBloodmoonSensor extends BlockDaylightDetector
{
	public BlockBloodmoonSensor()
	{
		this.setCreativeTab(RandomThings.creativeTab);
		
		this.setBlockName("bloodMoonSensor");
		
		GameRegistry.registerBlock(this,"bloodMoonSensor");
		this.setBlockTextureName("RandomThings:bloodMoonSensor");
		
		this.setHardness(0.7f);
	}

	@Override
	public void func_149957_e(World worldObj, int posX, int posY, int posZ)
	{
		int metadata = worldObj.getBlockMetadata(posX, posY, posZ);
		
		if (ServerBloodmoonHandler.INSTANCE.isBloodmoonActive() && metadata==0)
		{
			worldObj.setBlockMetadataWithNotify(posX, posY, posZ, 15, 3);
		}
		else if (!ServerBloodmoonHandler.INSTANCE.isBloodmoonActive() && metadata == 15)
		{
			worldObj.setBlockMetadataWithNotify(posX, posY, posZ, 0, 3);
		}
	}
}
