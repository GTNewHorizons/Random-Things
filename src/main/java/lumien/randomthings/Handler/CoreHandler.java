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
}
