package lumien.randomthings.Blocks;

import java.awt.Color;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityImbuingStation;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockImbuingStation extends BlockContainer
{
	IIcon iconSide, iconTop;

	protected BlockImbuingStation()
	{
		super(Material.rock);

		this.setBlockName("imbuingStation");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockTextureName("RandomThings:imbuingStation");

		GameRegistry.registerBlock(this, "imbuingStation");
	}

	@Override
	public TileEntity createNewTileEntity(World worldObj, int metadata)
	{
		return new TileEntityImbuingStation();
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		if (side == 1)
		{
			return iconTop;
		}
		else
		{
			return iconSide;
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister ir)
	{
		iconSide = ir.registerIcon("RandomThings:imbuingStation/imbuingStationSide");
		iconTop = ir.registerIcon("RandomThings:imbuingStation/imbuingStationTop");
	}
}
