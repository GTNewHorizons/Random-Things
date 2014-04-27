package lumien.randomthings.Transformer;

import java.io.File;
import java.util.Map;

import lumien.randomthings.Configuration.VanillaChanges;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.relauncher.IFMLCallHook;

public class RTCallHook implements IFMLCallHook
{
	public static Configuration config;

	@Override
	public Void call() throws Exception
	{	
		File f = new File("config/RandomThings.cfg");
		config = new Configuration(f);
		config.load();
		VanillaChanges.FASTER_LEAVEDECAY = config.get("VanillaChanges", "FasterLeaveDecay", true ,"Leaves will decay much faster when no longer connected to a log").getBoolean(true);
		config.save();
		
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		// TODO Auto-generated method stub

	}

}
