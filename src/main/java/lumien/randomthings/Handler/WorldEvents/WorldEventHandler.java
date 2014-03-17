package lumien.randomthings.Handler.WorldEvents;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.world.World;

public class WorldEventHandler
{
	HashMap<World,ArrayList<WorldEvent>> worldEvents;
	
	public WorldEventHandler()
	{
		worldEvents = new HashMap<World,ArrayList<WorldEvent>>();
	}
}
