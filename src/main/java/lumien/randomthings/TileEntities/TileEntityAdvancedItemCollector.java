package lumien.randomthings.TileEntities;

import java.util.List;

import lumien.randomthings.Items.ItemFilter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;

public class TileEntityAdvancedItemCollector extends TileEntity {

    public int rangeX, rangeY, rangeZ;
    private int tickRate = 20;
    private int tickCounter = 0;

    InventoryBasic inventory;

    public TileEntityAdvancedItemCollector() {
        rangeX = 2;
        rangeY = 2;
        rangeZ = 2;

        inventory = new InventoryBasic("AdvancedItemCollector", false, 1) {

            @Override
            public boolean isItemValidForSlot(int slot, ItemStack par2ItemStack) {
                return par2ItemStack.getItem() instanceof ItemFilter
                        && par2ItemStack.getItemDamage() == ItemFilter.FilterType.ITEM.ordinal();
            }
        };
    }

    public InventoryBasic getInventory() {
        return inventory;
    }

    public void setRange(int rangeX, int rangeY, int rangeZ) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;

        if (rangeX < 0) {
            this.rangeX = 0;
        } else if (rangeX > 10) {
            this.rangeX = 10;
        }

        if (rangeY < 0) {
            this.rangeY = 0;
        } else if (rangeY > 10) {
            this.rangeY = 10;
        }

        if (rangeZ < 0) {
            this.rangeZ = 0;
        } else if (rangeZ > 10) {
            this.rangeZ = 10;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("rangeX", rangeX);
        nbt.setInteger("rangeY", rangeY);
        nbt.setInteger("rangeZ", rangeZ);

        NBTTagCompound filter = new NBTTagCompound();

        if (inventory.getStackInSlot(0) != null) {
            inventory.getStackInSlot(0).writeToNBT(filter);
            nbt.setTag("filter", filter);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        rangeX = nbt.getInteger("rangeX");
        rangeY = nbt.getInteger("rangeY");
        rangeZ = nbt.getInteger("rangeZ");

        NBTTagCompound filter = (NBTTagCompound) nbt.getTag("filter");
        if (filter != null) {
            inventory.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(filter));
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            tickCounter++;
            if (tickCounter >= tickRate) {
                tickCounter = 0;
                int targetX, targetY, targetZ;

                EnumFacing facing = BlockDispenser
                        .func_149937_b(Facing.oppositeSide[worldObj.getBlockMetadata(xCoord, yCoord, zCoord)]);

                targetX = xCoord + facing.getFrontOffsetX();
                targetY = yCoord + facing.getFrontOffsetY();
                targetZ = zCoord + facing.getFrontOffsetZ();

                Block block = worldObj.getBlock(targetX, targetY, targetZ);

                if (block != null) {
                    TileEntity te = worldObj.getTileEntity(targetX, targetY, targetZ);
                    if (te instanceof IInventory) {
                        AxisAlignedBB bounding = AxisAlignedBB.getBoundingBox(
                                xCoord - rangeX,
                                yCoord - rangeY,
                                zCoord - rangeZ,
                                xCoord + rangeX + 1,
                                yCoord + rangeY + 1,
                                zCoord + rangeZ + 1);

                        List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, bounding);

                        if (items.size() == 0) {
                            if (tickRate < 20) {
                                tickRate++;
                            }
                        }

                        for (EntityItem ei : items) {
                            if (inventory.getStackInSlot(0) == null
                                    || ItemFilter.matchesItem(inventory.getStackInSlot(0), ei.getEntityItem())) {
                                ItemStack rest = TileEntityHopper.func_145889_a(
                                        (IInventory) te,
                                        ei.getEntityItem(),
                                        Facing.oppositeSide[facing.ordinal()]);
                                if (rest == null) {
                                    if (tickRate > 2) {
                                        tickRate--;
                                    }
                                    te.markDirty();
                                    ei.setDead();
                                } else if (!rest.equals(ei.getEntityItem())) {
                                    te.markDirty();
                                    ei.setEntityItemStack(rest);
                                } else if (rest.equals(ei.getEntityItem())) {
                                    if (tickRate < 20) {
                                        tickRate++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
