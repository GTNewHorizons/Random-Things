package lumien.randomthings.Blocks.EnergyDistributors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Blocks.BlockContainerBase;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnderEnergyDistributor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnderEnergyDistributor extends BlockContainerBase {
    @SideOnly(Side.CLIENT)
    IIcon top;

    @SideOnly(Side.CLIENT)
    IIcon sides;

    public BlockEnderEnergyDistributor() {
        super("enderEnergyDistributor", Material.rock);

        this.blockHardness = 3.0F;
    }

    @Override
    protected <T extends TileEntity> Class getTileEntityClass() {
        return TileEntityEnderEnergyDistributor.class;
    }

    @Override
    public void breakBlock(
            World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        TileEntityEnderEnergyDistributor distributor =
                (TileEntityEnderEnergyDistributor) p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

        if (distributor != null) {
            distributor.dropItems();
            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        top = ir.registerIcon("RandomThings:distributorEnder");
        sides = ir.registerIcon("RandomThings:distributorEnder_In");
    }

    @Override
    public boolean onBlockActivated(
            World worldObj,
            int posX,
            int posY,
            int posZ,
            EntityPlayer player,
            int par6,
            float par7,
            float par8,
            float par9) {
        if (!worldObj.isRemote) {
            player.openGui(RandomThings.instance, GuiIds.ENDER_ENERGY_DISTRIBUTOR, worldObj, posX, posY, posZ);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess ba, int posX, int posY, int posZ, int side) {
        TileEntityEnderEnergyDistributor te = (TileEntityEnderEnergyDistributor) ba.getTileEntity(posX, posY, posZ);
        if (side == 1) {
            return top;
        } else {
            return sides;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1) {
            return top;
        } else {
            return sides;
        }
    }
}
