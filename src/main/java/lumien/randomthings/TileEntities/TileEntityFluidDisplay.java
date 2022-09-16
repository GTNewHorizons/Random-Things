package lumien.randomthings.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFluidDisplay extends TileEntity {
    String fluidName = "";
    boolean flowing = false;

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

    public void toggleFlowing() {
        flowing = !flowing;
        this.markDirty();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean flowing() {
        return flowing;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    public String getFluidName() {
        return fluidName;
    }

    public void setFluidName(String fluidName) {
        this.fluidName = fluidName;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);

        this.fluidName = par1NBTTagCompound.getString("fluidName");
        if (this.fluidName.equals("empty")) {
            this.fluidName = "";
        }
        this.flowing = par1NBTTagCompound.getBoolean("flowing");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        if (this.fluidName.equals("")) {
            this.fluidName = "empty";
        }

        par1NBTTagCompound.setString("fluidName", fluidName);
        par1NBTTagCompound.setBoolean("flowing", flowing);
    }
}
