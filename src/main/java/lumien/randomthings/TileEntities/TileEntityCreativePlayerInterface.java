package lumien.randomthings.TileEntities;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.SimpleComponent;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
public class TileEntityCreativePlayerInterface extends TileEntityPlayerInterface implements SimpleComponent
{

	@Override
	public String getComponentName()
	{
		return "playerinterface";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPlayerName(Context context, Arguments args)
	{
		return new Object[] { this.getPlayerName() };
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setPlayerName(Context context, Arguments args)
	{
		this.setPlayerName(args.checkString(0));
		return new Object[] {};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] isCurrentlyConnected(Context context, Arguments args)
	{
		return new Object[] { this.playerEntity != null };
	}
}
