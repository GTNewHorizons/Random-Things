package lumien.randomthings.TileEntities.EnergyDistributors;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import java.util.HashSet;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.Interfaces.IValidator;
import lumien.randomthings.Library.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityEnergyDistributor extends TileEntity implements IEnergyReceiver {
    protected HashSet<TileEntity> receiverCache;

    public ForgeDirection facing;

    EnergyStorage buffer;

    int energyDistributedLastTick;
    int machinesConnected;

    IValidator validator = new IValidator() {
        @Override
        public boolean matches(Object o) {
            return o instanceof TileEntity & !(o instanceof TileEntityEnergyDistributor)
                    && o instanceof IEnergyReceiver;
        }
    };

    public TileEntityEnergyDistributor() {
        receiverCache = new HashSet<TileEntity>();
        facing = ForgeDirection.UP;
        buffer = new EnergyStorage(Settings.ENERGY_DISTRIBUTOR_BUFFERSIZE);
        energyDistributedLastTick = 0;
        machinesConnected = 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("facing", facing.ordinal());
        buffer.writeToNBT(nbt);
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
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.facing = ForgeDirection.getOrientation(nbt.getInteger("facing"));
        this.buffer.readFromNBT(nbt);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!worldObj.isRemote) {
            if (worldObj.getTotalWorldTime() % 100 == 0) {
                fillReceiverCache();
            }

            int limit = Settings.ENERGY_DISTRIBUTOR_PERTICK;
            energyDistributedLastTick = 0;
            for (TileEntity te : receiverCache) {
                if (!te.isInvalid()) {
                    IEnergyReceiver er = (IEnergyReceiver) te;
                    int consumed = er.receiveEnergy(
                            ForgeDirection.UP,
                            Math.min(limit, Math.min(buffer.getEnergyStored(), Settings.ENERGY_DISTRIBUTOR_PERMACHINE)),
                            false);
                    limit -= consumed;
                    buffer.setEnergyStored(buffer.getEnergyStored() - consumed);
                    energyDistributedLastTick += consumed;
                    if (buffer.getEnergyStored() == 0 || limit == 0) {
                        break;
                    }
                }
            }
        }
    }

    protected void fillReceiverCache() {
        receiverCache = WorldUtils.getConnectedTEs(
                worldObj, xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ, validator);
        machinesConnected = receiverCache.size();
        while (receiverCache.size() > Settings.ENERGY_DISTRIBUTOR_MAXMACHINES) {
            receiverCache.remove(receiverCache.toArray()[0]);
        }
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return !(from == facing);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return buffer.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return buffer.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return buffer.getMaxEnergyStored();
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    public int getEnergyDistributedLastTick() {
        return energyDistributedLastTick;
    }

    public int getMachinesConnected() {
        return machinesConnected;
    }
}
