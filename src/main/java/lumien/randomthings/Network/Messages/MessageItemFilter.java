package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lumien.randomthings.Items.ItemFilter;
import lumien.randomthings.Network.IRTMessage;
import net.minecraft.item.ItemStack;

public class MessageItemFilter implements IRTMessage {
    public enum ACTION {
        OREDICT,
        METADATA,
        LISTTYPE,
        NBT;
    }

    ACTION action;

    public MessageItemFilter(ACTION action) {
        this.action = action;
    }

    public MessageItemFilter() {}

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(action.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        action = ACTION.values()[buffer.readInt()];
    }

    @Override
    public void onMessage(MessageContext ctx) {
        String toToggle = "";
        ItemStack filter = ctx.getServerHandler().playerEntity.getCurrentEquippedItem();
        if (filter != null && filter.getItem() instanceof ItemFilter) {
            switch (action) {
                case OREDICT:
                    toToggle = "oreDict";
                    filter.stackTagCompound.setBoolean(toToggle, !filter.stackTagCompound.getBoolean(toToggle));
                    break;
                case LISTTYPE:
                    int oldType = filter.stackTagCompound.getInteger("listType");
                    filter.stackTagCompound.setInteger("listType", oldType == 0 ? 1 : 0);
                    break;
                case METADATA:
                    break;
                case NBT:
                    break;
                default:
                    break;
            }
        }
    }
}
