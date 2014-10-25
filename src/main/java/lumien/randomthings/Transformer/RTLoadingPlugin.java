package lumien.randomthings.Transformer;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.SortingIndex(1001)
public class RTLoadingPlugin implements IFMLLoadingPlugin
{
	public static boolean IN_MCP = false;

	@Override
	public String[] getASMTransformerClass()
	{
		// This will return the name of the class
		// "mod.culegooner.EDClassTransformer"
		return new String[] { RTClassTransformer.class.getName() };
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return RTCallHook.class.getName();
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		IN_MCP = !(Boolean) data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
