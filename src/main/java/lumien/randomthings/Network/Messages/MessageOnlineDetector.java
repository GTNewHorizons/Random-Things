package lumien.randomthings.Network.Messages;

import lumien.randomthings.Network.IRTMessage;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MessageOnlineDetector implements IRTMessage {

    String username;
    int posX, posY, posZ, dimensionID;

    public MessageOnlineDetector() {}

    public MessageOnlineDetector(String username, int posX, int posY, int posZ, int dimensionID) {
        this.username = username;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.dimensionID = dimensionID;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, username);

        buffer.writeInt(posX);
        buffer.writeInt(posY);
        buffer.writeInt(posZ);

        buffer.writeInt(dimensionID);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        username = ByteBufUtils.readUTF8String(buffer);

        posX = buffer.readInt();
        posY = buffer.readInt();
        posZ = buffer.readInt();

        dimensionID = buffer.readInt();
    }

    @Override
    public void onMessage(MessageContext ctx) {
        World worldObj = DimensionManager.getWorld(dimensionID);
        if (worldObj.getTileEntity(posX, posY, posZ) instanceof TileEntityOnlineDetector) {
            TileEntityOnlineDetector od = (TileEntityOnlineDetector) worldObj.getTileEntity(posX, posY, posZ);
            od.setUsername(username);
        }
    }
}
