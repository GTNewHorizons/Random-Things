package lumien.randomthings.Network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public interface IRTMessage extends IMessage {

    void onMessage(MessageContext context);
}
