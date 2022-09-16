package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lumien.randomthings.Network.IRTMessage;
import lumien.randomthings.RandomThings;

public class MessageContainerProperty implements IRTMessage {
    int index;
    int value;

    public MessageContainerProperty() {}

    public MessageContainerProperty(int index, int value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.index = buf.readInt();
        this.value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(index);
        buf.writeInt(value);
    }

    @Override
    public void onMessage(MessageContext context) {
        RandomThings.proxy.setContainerProperty(index, value);
    }
}
