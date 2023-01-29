package lumien.randomthings.Blocks.Spectre;

import java.util.List;

import lumien.randomthings.Blocks.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpectreGlass extends BlockBase {

    public BlockSpectreGlass() {
        super("spectreGlass", Material.ground);

        this.setStepSound(soundTypeGlass);
        this.setHardness(0.3f);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public int getRenderColor(int p_149741_1_) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        return 16777215;
    }

    @Override
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_,
            AxisAlignedBB p_149743_5_, List p_149743_6_, Entity entity) {
        AxisAlignedBB axisalignedbb1 = this
                .getCollisionBoundingBoxFromPool(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);

        if (axisalignedbb1 != null && p_149743_5_.intersectsWith(axisalignedbb1) && (entity instanceof EntityPlayer)) {
            p_149743_6_.add(axisalignedbb1);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess ba, int x, int y, int z, int side) {
        Block block = ba.getBlock(x, y, z);
        ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[side];

        if (block == this) {
            return false;
        } else return ba.isAirBlock(x, y, z) || !block.isOpaqueCube();
    }
}
