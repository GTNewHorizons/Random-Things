package lumien.randomthings.Blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Items.ItemGinto;
import lumien.randomthings.TileEntities.TileEntitySpiritRod;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockSpiritRod extends BlockContainerBase
{
	IIcon[] icons;

	public BlockSpiritRod()
	{
		super("spiritRod", Material.iron);
	}

	@Override
	protected <T extends TileEntity> Class getTileEntityClass()
	{
		return TileEntitySpiritRod.class;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return icons[0];
    }

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[2];
		this.icons[0] = ir.registerIcon("RandomThings:spiritRodInactive");
		this.icons[1] = ir.registerIcon("RandomThings:spiritRodActive");
	}

	@Override
	public boolean onBlockActivated(World worldObj, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		ItemStack currentlyEquipped = player.getCurrentEquippedItem();
		if (currentlyEquipped != null && currentlyEquipped.getItem() instanceof ItemGinto && currentlyEquipped.getItemDamage() == 1)
		{
			if (!worldObj.isRemote)
			{
				//worldObj.setBlockMetadataWithNotify(posX, posY, posZ, 1, 3);
			}
			return true;
		}

		return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
	{
		return null;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return 2;
	}

	public boolean canPlaceBlockAt(World worldObj, int posX, int posY, int posZ)
	{
		return World.doesBlockHaveSolidTopSurface(worldObj, posX, posY - 1, posZ);
	}

	public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
	{
		float f = 0.1F;

		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);

		return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
	}
}
