package lumien.randomthings.Blocks;

import java.util.Random;

import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.RandomThings;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAdvancedItemCollector extends BlockContainerBase {

    Random rng;

    protected BlockAdvancedItemCollector() {
        super("advancedItemCollector", Material.rock);
        this.blockHardness = 1.5F;

        this.setBlockTextureName("RandomThings:itemCollector/advancedItemCollector");
        this.setBlockBounds(0.35F, 0, 0.35F, 0.65F, 0.3F, 0.65F);
        this.rng = new Random();
    }

    @Override
    public void breakBlock(World worldObj, int posX, int posY, int posZ, Block block, int metadata) {
        TileEntityAdvancedItemCollector tileEntityAdvancedItemCollector = (TileEntityAdvancedItemCollector) worldObj
                .getTileEntity(posX, posY, posZ);

        if (tileEntityAdvancedItemCollector != null) {
            IInventory inventory = tileEntityAdvancedItemCollector.getInventory();
            for (int i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
                ItemStack itemstack = inventory.getStackInSlot(i1);

                if (itemstack != null) {
                    float f = this.rng.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rng.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.rng.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; worldObj
                            .spawnEntityInWorld(entityitem)) {
                        int j1 = this.rng.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(
                                worldObj,
                                posX + f,
                                posY + f1,
                                posZ + f2,
                                new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.rng.nextGaussian() * f3;
                        entityitem.motionY = (float) this.rng.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.rng.nextGaussian() * f3;

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem()
                                    .setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            worldObj.func_147453_f(posX, posY, posZ, block);
        }

        super.breakBlock(worldObj, posX, posY, posZ, block, metadata);
    }

    @Override
    public boolean onBlockActivated(World worldObj, int poxX, int poxY, int poxZ, EntityPlayer par5EntityPlayer,
            int par6, float par7, float par8, float par9) {
        if (!worldObj.isRemote) {
            par5EntityPlayer.openGui(RandomThings.instance, GuiIds.ADVANCED_ITEMCOLLECTOR, worldObj, poxX, poxY, poxZ);
        }
        return true;
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
        return TileEntityAdvancedItemCollector.class;
    }
}
