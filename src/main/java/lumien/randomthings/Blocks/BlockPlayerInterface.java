package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityPlayerInterface;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPlayerInterface extends BlockContainerBase
{
	IIcon texture_bottom;
	IIcon texture_top;
	IIcon texture_side;

	IIcon[] icons;

	protected BlockPlayerInterface()
	{
		super("playerinterface", Material.rock);

		this.setStepSound(soundTypeStone);
		this.setHardness(4.0F);
		icons = new IIcon[3];
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		icons[0] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_bottom");
		icons[1] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_top");
		icons[2] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_side");
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
	public boolean onBlockActivated(World par1World, int poxX, int poxY, int poxZ, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (!par1World.isRemote)
		{
			par5EntityPlayer.openGui(RandomThings.instance, GuiIds.PLAYER_INTERFACE, par1World, poxX, poxY, poxZ);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int poxX, int poxY, int poxZ, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		if (par5EntityLivingBase != null && par5EntityLivingBase instanceof EntityPlayerMP && par1World.getTileEntity(poxX, poxY, poxZ) != null)
		{
			EntityPlayerMP player = (EntityPlayerMP) par5EntityLivingBase;
			((TileEntityPlayerInterface) par1World.getTileEntity(poxX, poxY, poxZ)).setPlayerName(player.getCommandSenderName());
		}
		else
		{
			if (!par1World.isRemote)
			{
				par1World.setBlockToAir(poxX, poxY, poxZ);
			}
		}

	}

	@Override
	protected <T extends TileEntity> Class getTileEntityClass()
	{
		return TileEntityPlayerInterface.class;
	}

}
