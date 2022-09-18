package lumien.randomthings.TileEntities;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Library.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

@Optional.InterfaceList(
        value = {
            @Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
            @Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
        })
public class TileEntityOnlineDetector extends TileEntity implements SimpleComponent, IPeripheral {
    String username;

    int tickRate = 20;
    int tickCounter = 0;

    public TileEntityOnlineDetector() {
        username = "";
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (worldObj.getTotalWorldTime() % 20L == 0L) {
                int playerOnline = WorldUtils.isPlayerOnline(username) ? 1 : 0;
                int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

                if (playerOnline != metadata) {
                    this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, playerOnline, 3);

                    WorldUtils.notifyStrong(worldObj, xCoord, yCoord, zCoord, ModBlocks.onlineDetector);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("username", username);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        username = nbt.getString("username");
    }

    public void setUsername(String username) {
        this.username = username;
        this.markDirty();
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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

    public String getPlayerName() {
        return username;
    }

    @Override
    public String getComponentName() {
        return "onlinedetector";
    }

    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] isPlayerOnline(Context context, Arguments args) {
        return new Object[] {WorldUtils.isPlayerOnline(args.checkString(0))};
    }

    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] getPlayerList(Context context, Arguments args) {
        List playerEntityList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        String[] playerNameList = new String[playerEntityList.size()];
        for (int i = 0; i < playerNameList.length; i++) {
            playerNameList[i] = ((EntityPlayer) playerEntityList.get(i)).getCommandSenderName();
        }

        return new Object[] {playerNameList};
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String getType() {
        return "onlinedetector";
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String[] getMethodNames() {
        return new String[] {"isPlayerOnline", "getPlayerList"};
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) {
        switch (method) {
            case 0:
                if (arguments.length < 1) {
                    return null;
                } else {
                    return new Object[] {WorldUtils.isPlayerOnline(arguments[0] + "")};
                }
            case 1:
                return new Object[] {generatePlayerMap()};
        }
        return null;
    }

    private Map<Integer, String> generatePlayerMap() {
        List playerEntityList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        String[] playerNameList = new String[playerEntityList.size()];
        for (int i = 0; i < playerNameList.length; i++) {
            playerNameList[i] = ((EntityPlayer) playerEntityList.get(i)).getCommandSenderName();
        }

        HashMap<Integer, String> map = new HashMap<>();

        for (int index = 0; index < playerNameList.length; index++) {
            map.put(index, playerNameList[index]);
        }

        return map;
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void attach(IComputerAccess computer) {}

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void detach(IComputerAccess computer) {}

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public boolean equals(IPeripheral other) {
        return false;
    }
}
