package lumien.randomthings.Blocks.EnergyDistributors;

import lumien.randomthings.Blocks.BlockContainerBase;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnergyDistributor;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnergyDistributor extends BlockContainerBase {

    @SideOnly(Side.CLIENT)
    IIcon front;

    @SideOnly(Side.CLIENT)
    IIcon sides;

    public BlockEnergyDistributor() {
        super("energyDistributor", Material.rock);

        this.blockHardness = 3.0F;
    }

    @Override
    protected <T extends TileEntity> Class getTileEntityClass() {
        return TileEntityEnergyDistributor.class;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        front = ir.registerIcon("RandomThings:distributorBasic_Out");
        sides = ir.registerIcon("RandomThings:distributorBasic_In");
    }

    @Override
    public void onBlockPlacedBy(World worldObj, int posX, int posY, int posZ, EntityLivingBase entityLiving,
            ItemStack is) {
        int l = BlockPistonBase.determineOrientation(worldObj, posX, posY, posZ, entityLiving);
        TileEntityEnergyDistributor te = (TileEntityEnergyDistributor) worldObj.getTileEntity(posX, posY, posZ);
        te.facing = ForgeDirection.getOrientation(l);
    }

    @Override
    public boolean onBlockActivated(World worldObj, int posX, int posY, int posZ, EntityPlayer player, int par6,
            float par7, float par8, float par9) {
        if (!worldObj.isRemote) {
            player.openGui(RandomThings.instance, GuiIds.ENERGY_DISTRIBUTOR, worldObj, posX, posY, posZ);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess ba, int posX, int posY, int posZ, int side) {
        TileEntityEnergyDistributor te = (TileEntityEnergyDistributor) ba.getTileEntity(posX, posY, posZ);
        if (te.facing.ordinal() == side) {
            return front;
        } else {
            return sides;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 3) {
            return front;
        } else {
            return sides;
        }
    }
}
