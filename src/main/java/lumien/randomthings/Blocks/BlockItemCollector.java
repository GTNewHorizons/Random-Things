package lumien.randomthings.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityItemCollector;

public class BlockItemCollector extends BlockContainerBase {

    protected BlockItemCollector() {
        super("itemCollector", Material.rock);
        this.blockHardness = 1.5F;

        this.setCreativeTab(RandomThings.creativeTab);
        this.setBlockTextureName("RandomThings:itemCollector/itemCollector");
        this.setBlockBounds(0.35F, 0, 0.35F, 0.65F, 0.3F, 0.65F);
    }

    @Override
    public void onNeighborBlockChange(World worldObj, int posX, int posY, int posZ, Block block) {
        EnumFacing facing = BlockDispenser
                .func_149937_b(Facing.oppositeSide[worldObj.getBlockMetadata(posX, posY, posZ)]);

        int targetX = posX + facing.getFrontOffsetX();
        int targetY = posY + facing.getFrontOffsetY();
        int targetZ = posZ + facing.getFrontOffsetZ();

        if (worldObj.isAirBlock(targetX, targetY, targetZ)) {
            this.dropBlockAsItem(worldObj, posX, posY, posZ, worldObj.getBlockMetadata(posX, posY, posZ), 0);
            worldObj.setBlockToAir(posX, posY, posZ);
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldObj, int posX, int posY, int posZ, int side) {
        EnumFacing facing = BlockDispenser.func_149937_b(Facing.oppositeSide[side]);

        int targetX = posX + facing.getFrontOffsetX();
        int targetY = posY + facing.getFrontOffsetY();
        int targetZ = posZ + facing.getFrontOffsetZ();

        return !worldObj.isAirBlock(targetX, targetY, targetZ);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
            int p_149668_4_) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int onBlockPlaced(World p_149660_1_, int posX, int posY, int posZ, int side, float hitX, float hitY,
            float hitZ, int metadata) {
        return side;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int posX, int posY, int posZ) {
        int metadata = blockAccess.getBlockMetadata(posX, posY, posZ);

        switch (metadata) {
            case 0:
                this.setBlockBounds(0.35F, 0.7F, 0.35F, 0.65F, 1F, 0.65F);
                break;
            case 1:
                this.setBlockBounds(0.35F, 0, 0.35F, 0.65F, 0.3F, 0.65F);
                break;
            case 2:
                this.setBlockBounds(0.35F, 0.35F, 0.7F, 0.65F, 0.65F, 1F);
                break;
            case 3:
                this.setBlockBounds(0.35F, 0.35F, 0F, 0.65F, 0.65F, 0.30F);
                break;
            case 4:
                this.setBlockBounds(0.7F, 0.35F, 0.35F, 1F, 0.65F, 0.65F);
                break;
            case 5:
                this.setBlockBounds(0F, 0.35F, 0.35F, 0.30F, 0.65F, 0.65F);
                break;
        }
    }

    @Override
    protected <T extends TileEntity> Class getTileEntityClass() {
        return TileEntityItemCollector.class;
    }
}
