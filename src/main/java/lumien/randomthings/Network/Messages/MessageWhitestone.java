package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import lumien.randomthings.Client.Particle.ParticleWhitestone;
import lumien.randomthings.Network.IRTMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MessageWhitestone implements IRTMessage {
    int playerID;

    public MessageWhitestone() {}

    public MessageWhitestone(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(playerID);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        playerID = buffer.readInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onMessage(MessageContext ctx) {
        Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(playerID);
        if (entity instanceof EntityPlayer) {
            for (int i = 0; i < 10; i++) {
                Minecraft.getMinecraft()
                        .effectRenderer
                        .addEffect(new ParticleWhitestone(
                                (EntityPlayer) entity, 0, 0, 0, Math.random() * 2 - 1, 0, Math.random() * 2 - 1));
            }
        }
    }
}
