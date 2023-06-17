package lumien.randomthings.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import lumien.randomthings.TileEntities.TileEntityAdvancedFluidDisplay;

public class BlockAdvancedFluidDisplay extends BlockContainerBase {

    public BlockAdvancedFluidDisplay() {
        super("advancedFluidDisplay", Material.glass);

        this.setHardness(0.7F);
    }

    @Override
    protected <T extends TileEntity> Class getTileEntityClass() {
        return TileEntityAdvancedFluidDisplay.class;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("RandomThings:advancedFluidDisplay");
    }

    @Override
    public IIcon getIcon(IBlockAccess par1IBlockAccess, int posX, int posY, int posZ, int side) {
        TileEntityAdvancedFluidDisplay te = (TileEntityAdvancedFluidDisplay) par1IBlockAccess
                .getTileEntity(posX, posY, posZ);
        if (te == null || te.getFluid(side) == null
                || te.getFluid(side).equals("")
                || FluidRegistry.getFluid(te.getFluid(side)) == null) {
            return blockIcon;
        } else {
            if (te.isFlowing(side)) {
                return FluidRegistry.getFluid(te.getFluid(side)).getFlowingIcon();
            } else {
                return FluidRegistry.getFluid(te.getFluid(side)).getIcon();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer entityplayer, int side,
            float par7, float par8, float par9) {
        TileEntityAdvancedFluidDisplay te = (TileEntityAdvancedFluidDisplay) world.getTileEntity(posX, posY, posZ);
        ItemStack currentItem = entityplayer.getCurrentEquippedItem();

        if (currentItem != null) {
            FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(currentItem);
            if (liquid != null) {
                if (!world.isRemote) {
                    te.setFluid(side, liquid.getFluid().getName());
                    te.markDirty();
                    world.markBlockForUpdate(posX, posY, posZ);
                }
                return true;
            }
        } else {
            if (!world.isRemote) {
                te.setFlowing(side, !te.isFlowing(side));
                te.markDirty();
                world.markBlockForUpdate(posX, posY, posZ);
            }
            return true;
        }
        return false;
    }
}
