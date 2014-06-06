package lumien.randomthings.Handler;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.VanillaChanges;
import lumien.randomthings.TileEntities.TileEntityWirelessLever;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class CoreHandler
{
	static Random rng = new Random();

	public static void handleLeaveDecay(World worldObj, int posX, int posY, int posZ, Block block)
	{
		if (VanillaChanges.FASTER_LEAVEDECAY)
		{
			worldObj.scheduleBlockUpdate(posX, posY, posZ, block, 4 + rng.nextInt(7));
			return;
		}
		
	}
	
	public static boolean isIndirectlyGettingPowered(World worldObj,int posX,int posY,int posZ)
	{
		if (ConfigBlocks.wirelessLever)
		{
			return TileEntityWirelessLever.isPowered(worldObj, posX, posY, posZ);
		}
		else
		{
			return false;
		}
	}
	
	public static void moonColorHook()
	{
		
	}
}
