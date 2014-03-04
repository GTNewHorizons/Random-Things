package lumien.randomthings.Handler;

import java.util.Random;

import lumien.randomthings.Configuration.VanillaChanges;
import lumien.randomthings.Transformer.RTClassTransformer;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class CoreHandler
{
	static Random rng = new Random();

	public static void handleLeaveDecay(World worldObj, int posX, int posY, int posZ, Block leave)
	{
		if (VanillaChanges.FASTER_LEAVEDECAY)
		{
			worldObj.scheduleBlockUpdate(posX, posY, posZ, leave, 2 + rng.nextInt(5));
		}
	}
	
	public static boolean isIndirectlyPowered(World worldObj,int posX,int posY,int posZ)
	{
		if (RedstoneHandler.isPowered(worldObj.provider.dimensionId, posX, posY, posZ))
		{
			return true;
		}
		return worldObj.getIndirectPowerLevelTo(posX, posY - 1, posZ, 0) > 0 ? true : (worldObj.getIndirectPowerLevelTo(posX, posY + 1, posZ, 1) > 0 ? true : (worldObj.getIndirectPowerLevelTo(posX, posY, posZ - 1, 2) > 0 ? true : (worldObj.getIndirectPowerLevelTo(posX, posY, posZ + 1, 3) > 0 ? true : (worldObj.getIndirectPowerLevelTo(posX - 1, posY, posZ, 4) > 0 ? true : worldObj.getIndirectPowerLevelTo(posX + 1, posY, posZ, 5) > 0))));
	}
}
