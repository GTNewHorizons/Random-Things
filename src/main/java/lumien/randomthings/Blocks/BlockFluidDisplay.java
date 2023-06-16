package lumien.randomthings.Blocks;

import net.minecraft.block.BlockContainer;
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

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityFluidDisplay;

public class BlockFluidDisplay extends BlockContainer {

    public BlockFluidDisplay() {
        super(Material.glass);
        this.setBlockName("fluidDisplay");
        this.setCreativeTab(RandomThings.creativeTab);
        this.setHardness(0.7F);

        GameRegistry.registerBlock(this, "fluidDisplay");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityFluidDisplay();
    }

    @Override
    public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        TileEntityFluidDisplay te = (TileEntityFluidDisplay) par1IBlockAccess.getTileEntity(par2, par3, par4);
        if (te == null || te.getFluidName().equals("") || FluidRegistry.getFluid(te.getFluidName()) == null) {
            return blockIcon;
        } else {
            if (!te.flowing()) {
                return FluidRegistry.getFluid(te.getFluidName()).getIcon();
            } else {
                return FluidRegistry.getFluid(te.getFluidName()).getFlowingIcon();
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("RandomThings:fluidDisplay");
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7,
            float par8, float par9) {
        TileEntityFluidDisplay te = (TileEntityFluidDisplay) world.getTileEntity(i, j, k);
        ItemStack currentItem = entityplayer.getCurrentEquippedItem();

        if (currentItem != null) {
            FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(currentItem);
            if (liquid != null) {
                if (!world.isRemote) {
                    te.setFluidName(liquid.getFluid().getName());
                    te.markDirty();
                    world.markBlockForUpdate(i, j, k);
                }
                return true;
            }
        } else {
            if (!world.isRemote) {
                te.toggleFlowing();
            }
            return true;
        }
        return false;
    }
}
