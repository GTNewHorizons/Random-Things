package lumien.randomthings.Handler;

import java.util.Random;

import lumien.randomthings.Configuration.VanillaChanges;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class CoreHandler
{
	static Random rng = new Random();

	public static void handleLeaveDecay(World worldObj, int posX, int posY, int posZ, Block leave)
	{
		if (VanillaChanges.FASTER_LEAVEDECAY)
		{
			worldObj.scheduleBlockUpdate(posX, posY, posZ, Blocks.leaves, 4 + rng.nextInt(7));
		}
	}
}
