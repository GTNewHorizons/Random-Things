package lumien.randomthings.Blocks;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOnlineDetector extends BlockContainer
{
	IIcon[] icons;
	Random rand = new Random();

	protected BlockOnlineDetector()
	{
		super(Material.rock);

		this.setBlockName("onlineDetector");
		this.setCreativeTab(RandomThings.creativeTab);
		this.blockHardness = 2.0F;

		icons = new IIcon[2];

		GameRegistry.registerBlock(this, "onlineDetector");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir)
	{
		icons[0] = ir.registerIcon("RandomThings:onlineDetector/offline");
		icons[1] = ir.registerIcon("RandomThings:onlineDetector/online");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
		byte b0 = 4;

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < b0; ++k1)
                {
                    double d0 = (double)x + ((double)i1 + 0.5D) / (double)b0;
                    double d1 = (double)y + ((double)j1 + 0.5D) / (double)b0;
                    double d2 = (double)z + ((double)k1 + 0.5D) / (double)b0;
                    
                    EntityDiggingFX particle = (new EntityDiggingFX(world, d0, d1, d2, d0 - (double)x - 0.5D, d1 - (double)y - 0.5D, d2 - (double)z - 0.5D, this, 0)).applyColourMultiplier(x, y, z);
                    particle.setParticleIcon(getIcon(world,x,y,z,0));
                    effectRenderer.addEffect(particle);
                }
            }
        }
        
        return true;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		float f = 0.1F;
		double d0 = (double) target.blockX + this.rand.nextDouble() * (this.getBlockBoundsMaxX() - this.getBlockBoundsMinX() - (double) (f * 2.0F)) + (double) f + this.getBlockBoundsMinX();
		double d1 = (double) target.blockY + this.rand.nextDouble() * (this.getBlockBoundsMaxY() - this.getBlockBoundsMinY() - (double) (f * 2.0F)) + (double) f + this.getBlockBoundsMinY();
		double d2 = (double) target.blockZ + this.rand.nextDouble() * (this.getBlockBoundsMaxZ() - this.getBlockBoundsMinZ() - (double) (f * 2.0F)) + (double) f + this.getBlockBoundsMinZ();

		if (target.sideHit == 0)
		{
			d1 = (double) target.blockY + this.getBlockBoundsMinY() - (double) f;
		}

		if (target.sideHit == 1)
		{
			d1 = (double) target.blockY + this.getBlockBoundsMaxY() + (double) f;
		}

		if (target.sideHit == 2)
		{
			d2 = (double) target.blockZ + this.getBlockBoundsMinZ() - (double) f;
		}

		if (target.sideHit == 3)
		{
			d2 = (double) target.blockZ + this.getBlockBoundsMaxZ() + (double) f;
		}

		if (target.sideHit == 4)
		{
			d0 = (double) target.blockX + this.getBlockBoundsMinX() - (double) f;
		}

		if (target.sideHit == 5)
		{
			d0 = (double) target.blockX + this.getBlockBoundsMaxX() + (double) f;
		}

		EntityDiggingFX particle = (EntityDiggingFX) new EntityDiggingFX(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, this, worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ)).applyColourMultiplier(target.blockX, target.blockY, target.blockZ).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F);

		particle.setParticleIcon(getIcon(worldObj, target.blockX, target.blockY, target.blockZ, 0));

		effectRenderer.addEffect(particle);

		return true;
	}

	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_)
	{
		return 15728704;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess ba, int posX, int posY, int posZ, int p_149673_5_)
	{
		TileEntityOnlineDetector te = (TileEntityOnlineDetector) ba.getTileEntity(posX, posY, posZ);
		if (te.isActive())
		{
			return icons[1];
		}
		else
		{
			return icons[0];
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return icons[0];
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityOnlineDetector();
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldObj, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (!worldObj.isRemote)
		{
			player.openGui(RandomThings.instance, GuiIds.ONLINE_DETECTOR, worldObj, posX, posY, posZ);
		}
		return true;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess blockAccess, int posX, int posY, int posZ, int metadata)
	{
		TileEntityOnlineDetector te = (TileEntityOnlineDetector) blockAccess.getTileEntity(posX, posY, posZ);
		return te.isActive() ? 15 : 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int posX, int posY, int posZ, int metadata)
	{
		return isProvidingStrongPower(blockAccess, posX, posY, posZ, metadata);
	}

}
