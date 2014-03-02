package lumien.randomthings.Handler;

import lumien.randomthings.Blocks.BlockFertilizedDirt;
import lumien.randomthings.Blocks.ModBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class RTEventHandler
{
	@SubscribeEvent
	public void useHoe(UseHoeEvent event)
	{
		if (event.world.getBlock(event.x, event.y, event.z) == ModBlocks.fertilizedDirt)
		{
			event.setResult(Result.ALLOW);
			event.world.setBlock(event.x, event.y, event.z, ModBlocks.fertilizedDirtTilled);
			event.world.playSoundEffect((double)((float)event.x + 0.5F), (double)((float)event.y + 0.5F), (double)((float)event.z + 0.5F), ModBlocks.fertilizedDirtTilled.stepSound.getStepResourcePath(), (ModBlocks.fertilizedDirtTilled.stepSound.getVolume() + 1.0F) / 2.0F, ModBlocks.fertilizedDirtTilled.stepSound.getPitch() * 0.8F);
		}
	}
}
