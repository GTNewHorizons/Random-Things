package lumien.randomthings.Blocks;

import java.util.List;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Blocks.ItemBlocks.ItemBlockColored;
import lumien.randomthings.Library.Colors;
import lumien.randomthings.Library.WorldUtils;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSpectreBlock extends BlockBase
{
	public BlockSpectreBlock()
	{
		super("spectreBlock",Material.rock,ItemBlockColored.class);

		this.lightValue = 15;
		this.setBlockUnbreakable().setStepSound(soundTypeGlass);
		this.setResistance(6000000.0F);
	}
	
	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
    {
		return false;
    }

	@Override
	public boolean onBlockActivated(World worldObj, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (entityplayer.getCurrentEquippedItem() != null)
		{
			int dye = Colors.getDye(entityplayer.getCurrentEquippedItem());
			if (dye != -1)
			{
				if (!worldObj.isRemote)
				{
					WorldUtils.setConnectedBlocksTo(worldObj, i, j, k, this, dye, this, worldObj.getBlockMetadata(i, j, k));
				}

				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int damage)
	{
		return ItemDye.field_150922_c[damage];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < ItemDye.field_150922_c.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int colorMultiplier(IBlockAccess ba, int posX, int posY, int posZ)
	{
		return getRenderColor(ba.getBlockMetadata(posX, posY, posZ));
	}

	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int damageDropped(int p_149692_1_)
    {
        return p_149692_1_;
    }

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess ba, int x, int y, int z, int side)
	{
		Block block = ba.getBlock(x, y, z);
		ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[side];
		int myMetadata = ba.getBlockMetadata(x - fd.offsetX, y - fd.offsetY, z - fd.offsetZ);

		if (block == this && ba.getBlockMetadata(x, y, z) == myMetadata)
		{
			return false;
		}
		else if (ba.isAirBlock(x, y, z) || !block.isOpaqueCube())
		{
			return true;
		}
		return false;
	}
}
