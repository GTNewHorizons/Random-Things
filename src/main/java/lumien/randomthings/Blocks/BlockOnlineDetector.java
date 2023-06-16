package lumien.randomthings.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;

public class BlockOnlineDetector extends BlockContainerBase {

    IIcon[] icons;
    Random rand = new Random();

    protected BlockOnlineDetector() {
        super("onlineDetector", Material.rock);

        this.blockHardness = 2.0F;
        icons = new IIcon[2];
    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {
        icons[0] = ir.registerIcon("RandomThings:onlineDetector/offline");
        icons[1] = ir.registerIcon("RandomThings:onlineDetector/online");
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        byte b0 = 4;

        for (int i1 = 0; i1 < b0; ++i1) {
            for (int j1 = 0; j1 < b0; ++j1) {
                for (int k1 = 0; k1 < b0; ++k1) {
                    double d0 = x + (i1 + 0.5D) / b0;
                    double d1 = y + (j1 + 0.5D) / b0;
                    double d2 = z + (k1 + 0.5D) / b0;

                    EntityDiggingFX particle = (new EntityDiggingFX(
                            world,
                            d0,
                            d1,
                            d2,
                            d0 - x - 0.5D,
                            d1 - y - 0.5D,
                            d2 - z - 0.5D,
                            this,
                            0)).applyColourMultiplier(x, y, z);
                    particle.setParticleIcon(getIcon(world, x, y, z, 0));
                    effectRenderer.addEffect(particle);
                }
            }
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        float f = 0.1F;
        double d0 = target.blockX
                + this.rand.nextDouble() * (this.getBlockBoundsMaxX() - this.getBlockBoundsMinX() - f * 2.0F)
                + f
                + this.getBlockBoundsMinX();
        double d1 = target.blockY
                + this.rand.nextDouble() * (this.getBlockBoundsMaxY() - this.getBlockBoundsMinY() - f * 2.0F)
                + f
                + this.getBlockBoundsMinY();
        double d2 = target.blockZ
                + this.rand.nextDouble() * (this.getBlockBoundsMaxZ() - this.getBlockBoundsMinZ() - f * 2.0F)
                + f
                + this.getBlockBoundsMinZ();

        if (target.sideHit == 0) {
            d1 = target.blockY + this.getBlockBoundsMinY() - f;
        }

        if (target.sideHit == 1) {
            d1 = target.blockY + this.getBlockBoundsMaxY() + f;
        }

        if (target.sideHit == 2) {
            d2 = target.blockZ + this.getBlockBoundsMinZ() - f;
        }

        if (target.sideHit == 3) {
            d2 = target.blockZ + this.getBlockBoundsMaxZ() + f;
        }

        if (target.sideHit == 4) {
            d0 = target.blockX + this.getBlockBoundsMinX() - f;
        }

        if (target.sideHit == 5) {
            d0 = target.blockX + this.getBlockBoundsMaxX() + f;
        }

        EntityDiggingFX particle = (EntityDiggingFX) new EntityDiggingFX(
                worldObj,
                d0,
                d1,
                d2,
                0.0D,
                0.0D,
                0.0D,
                this,
                worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ))
                        .applyColourMultiplier(target.blockX, target.blockY, target.blockZ).multiplyVelocity(0.2F)
                        .multipleParticleScaleBy(0.6F);

        particle.setParticleIcon(getIcon(worldObj, target.blockX, target.blockY, target.blockZ, 0));

        effectRenderer.addEffect(particle);

        return true;
    }

    @Override
    public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
        return 15728704;
    }

    @Override
    public IIcon getIcon(IBlockAccess ba, int posX, int posY, int posZ, int side) {
        int metadata = ba.getBlockMetadata(posX, posY, posZ);
        if (metadata == 1) {
            return icons[1];
        } else {
            return icons[0];
        }
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return icons[0];
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldObj, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_,
            float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!worldObj.isRemote) {
            player.openGui(RandomThings.instance, GuiIds.ONLINE_DETECTOR, worldObj, posX, posY, posZ);
        }
        return true;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess blockAccess, int posX, int posY, int posZ, int side) {
        int metadata = blockAccess.getBlockMetadata(posX, posY, posZ);
        return metadata == 1 ? 15 : 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess blockAccess, int posX, int posY, int posZ, int side) {
        int metadata = blockAccess.getBlockMetadata(posX, posY, posZ);
        return metadata == 1 ? 15 : 0;
    }

    @Override
    protected <T extends TileEntity> Class getTileEntityClass() {
        return TileEntityOnlineDetector.class;
    }
}
