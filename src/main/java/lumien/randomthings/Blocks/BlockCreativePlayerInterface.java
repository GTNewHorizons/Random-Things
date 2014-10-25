package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityCreativePlayerInterface;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCreativePlayerInterface extends BlockContainerBase
{
	IIcon texture_bottom;
	IIcon texture_top;
	IIcon texture_side;

	IIcon[] icons;

	public BlockCreativePlayerInterface()
	{
		super("creativePlayerInterface", Material.rock);

		this.setCreativeTab(RandomThings.creativeTab);
		this.setStepSound(soundTypeStone);

		this.setHardness(4.0F);
		icons = new IIcon[3];
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		icons[0] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_bottom");
		icons[1] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_top");
		icons[2] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_side");
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		switch (side)
		{
			case 0:
				return icons[0];
			case 1:
				return icons[1];
			default:
				return icons[2];
		}
	}

	@Override
	public boolean onBlockActivated(World par1World, int poxX, int posY, int poxZ, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (!par1World.isRemote)
		{
			par5EntityPlayer.openGui(RandomThings.instance, GuiIds.PLAYER_INTERFACE, par1World, poxX, posY, poxZ);
		}
		return true;
	}

	@Override
	protected <T extends TileEntity> Class getTileEntityClass()
	{
		return TileEntityCreativePlayerInterface.class;
	}
}
