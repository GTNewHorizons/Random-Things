package lumien.randomthings.Blocks;

import java.util.Random;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.RTConfiguration;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityPlayerInterface;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

public class BlockPlayerInterface extends BlockContainer
{
	IIcon texture_bottom;
	IIcon texture_top;
	IIcon texture_side;

	IIcon[] icons;

	protected BlockPlayerInterface()
	{
		super(Material.rock);

		this.setCreativeTab(RandomThings.creativeTab);
		this.setStepSound(soundTypeStone);
		this.setHardness(20.0F);
		this.setBlockName("playerinterface");

		this.setHardness(4.0F);

		GameRegistry.registerBlock(this, "playerinterface");
		
		icons = new IIcon[3];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		if (Settings.ANIMATED_TEXTURES)
		{
			icons[0] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_bottom");
			icons[1] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_top");
			icons[2] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_side");
		}
		else
		{
			icons[0] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_bottom_static");
			icons[1] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_top_static");
			icons[2] = par1IconRegister.registerIcon("RandomThings:playerinterface/playerinterface_side_static");
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
			par5EntityPlayer.openGui(RandomThings.instance, GuiIds.PLAYER_INTERFACE, par1World, poxX, poxY, poxZ);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int poxX, int poxY, int poxZ, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		if (par5EntityLivingBase instanceof EntityPlayerMP)
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
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileEntityPlayerInterface();
	}

}
