package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityCreativePlayerInterface;
import lumien.randomthings.TileEntities.TileEntityPlayerInterface;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCreativePlayerInterface extends BlockContainer
{
	IIcon texture_bottom;
	IIcon texture_top;
	IIcon texture_side;

	IIcon[] icons;

	protected BlockCreativePlayerInterface()
	{
		super(Material.rock);

		this.setCreativeTab(RandomThings.creativeTab);
		this.setStepSound(soundTypeStone);
		this.setHardness(20.0F);
		this.setBlockName("creativePlayerInterface");

		this.setHardness(4.0F);

		GameRegistry.registerBlock(this, "creativePlayerInterface");
		
		icons = new IIcon[3];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		if (Settings.ANIMATED_TEXTURES)
		{
			icons[0] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_bottom");
			icons[1] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_top");
			icons[2] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_side");
		}
		else
		{
			icons[0] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_bottom_static");
			icons[1] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_top_static");
			icons[2] = par1IconRegister.registerIcon("RandomThings:playerinterface/creative/playerinterface_side_static");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
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
			par5EntityPlayer.openGui(RandomThings.instance, GuiIds.PLAYER_INTERFACE_CREATIVE, par1World, poxX, poxY, poxZ);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileEntityCreativePlayerInterface();
	}
}
