package lumien.randomthings.Blocks;

import lumien.randomthings.TileEntities.TileEntityAdvancedFluidDisplay;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockAdvancedFluidDisplay extends BlockContainerBase
{
	public BlockAdvancedFluidDisplay()
	{
		super("advancedFluidDisplay", Material.glass);
		
		this.setHardness(0.7F);
	}

	@Override
	protected <T extends TileEntity> Class getTileEntityClass()
	{
		return TileEntityAdvancedFluidDisplay.class;
	}

}
