package lumien.randomthings.Handler;

import java.util.HashMap;

import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Network.Messages.MessageBloodMoon;

public class BloodMoonHandler
{
	public static BloodMoonHandler INSTANCE = new BloodMoonHandler();

	HashMap<Integer, Boolean> bloodMoons;

	public BloodMoonHandler()
	{
		bloodMoons = new HashMap<Integer, Boolean>();
	}

	public boolean hasBloodMoon(int dimensionID)
	{
		if (!bloodMoons.containsKey(dimensionID))
		{
			return false;
		}
		else
		{
			return bloodMoons.get(dimensionID);
		}
	}

	public void setBloodMoon(int dimensionID, boolean bloodmoon, boolean isRemote)
	{
		bloodMoons.put(dimensionID, bloodmoon);
		if (!isRemote)
		{
			PacketHandler.INSTANCE.sendToDimension(new MessageBloodMoon().setBloodmoon(bloodmoon).setDimensionID(dimensionID), dimensionID);
		}
	}
}
