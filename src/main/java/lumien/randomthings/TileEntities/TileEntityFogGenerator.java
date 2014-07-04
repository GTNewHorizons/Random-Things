package lumien.randomthings.TileEntities;

import java.util.ArrayDeque;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.tileentity.TileEntity;

public class TileEntityFogGenerator extends TileEntity
{
	public static ArrayDeque<TileEntityFogGenerator> loadedFogGenerators = new ArrayDeque<TileEntityFogGenerator>();
	
	public TileEntityFogGenerator()
	{
		loadedFogGenerators.add(this);
	}

	public void invalidate()
	{
		super.invalidate();

		if (worldObj.isRemote)
			loadedFogGenerators.remove(this);
	}
}
