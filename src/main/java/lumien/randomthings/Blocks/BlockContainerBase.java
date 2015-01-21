package lumien.randomthings.Blocks;

import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class BlockContainerBase extends BlockContainer
{
	String blockName;

	protected BlockContainerBase(String blockName, Material material)
	{
		super(material);

		this.blockName = blockName;

		this.setCreativeTab(RandomThings.creativeTab);
		this.setBlockName(blockName);
		this.setBlockTextureName("RandomThings:" + blockName);

		GameRegistry.registerBlock(this, blockName);
	}

	@Override
	public final TileEntity createNewTileEntity(World var1, int var2)
	{
		try
		{
			return (TileEntity) getTileEntityClass().newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldObj, int posX, int posY, int posZ, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		return super.onBlockActivated(worldObj, posX, posY, posZ, entityPlayer, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
	}
	
	@Override
	public void onBlockPlacedBy(World worldObj, int posX, int posY, int posZ, EntityLivingBase entityLiving, ItemStack is)
	{
		super.onBlockPlacedBy(worldObj, posX, posY, posZ, entityLiving, is);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		super.registerBlockIcons(ir);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return super.getIcon(side, meta);
	}

	protected abstract <T extends TileEntity> Class getTileEntityClass();
}
