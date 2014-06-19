package lumien.randomthings.Handler;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Configuration.ConfigBlocks;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Configuration.VanillaChanges;
import lumien.randomthings.TileEntities.TileEntityWirelessLever;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class CoreHandler
{
	static Random rng = new Random();
	static Minecraft mc = Minecraft.getMinecraft();

	public static void handleLeaveDecay(World worldObj, int posX, int posY, int posZ, Block block)
	{
		if (VanillaChanges.FASTER_LEAVEDECAY)
		{
			worldObj.scheduleBlockUpdate(posX, posY, posZ, block, 4 + rng.nextInt(7));
			return;
		}
	}

	public static boolean isIndirectlyGettingPowered(World worldObj, int posX, int posY, int posZ)
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

	public static void moonColorHook(float partialTickTime)
	{
		if (Settings.BLOOD_MOON)
		{
			if (BloodMoonHandler.INSTANCE.hasBloodMoon(mc.theWorld.provider.dimensionId))
			{
				GL11.glColor3f(1, 0, 0);
			}
		}
	}

	public static void starColorHook(float partialTickTime)
	{
		if (BloodMoonHandler.INSTANCE.hasBloodMoon(mc.theWorld.provider.dimensionId))
		{
			GL11.glColor3f(1, mc.theWorld.getStarBrightness(partialTickTime), mc.theWorld.getStarBrightness(partialTickTime));
		}
	}
}
