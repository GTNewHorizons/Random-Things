package lumien.randomthings.Network.Messages;

import lumien.randomthings.Client.GUI.GuiMagneticForce;
import lumien.randomthings.Network.IRTMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MessageAnswerTeleport implements IRTMessage
{
	public enum STATUS
	{
		OKAY, NOT_ONLINE, SAME_PLAYER, NO_RIGHT, INVALID_USERNAME;
	}

	private STATUS status;

	@Override
	@SideOnly(Side.CLIENT)
	public void onMessage(MessageContext ctx)
	{
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;

		if (mc.thePlayer != null && currentScreen != null && currentScreen instanceof GuiMagneticForce)
		{
			if (status == STATUS.OKAY)
			{
				mc.thePlayer.closeScreen();
			}
			else
			{
				((GuiMagneticForce) currentScreen).setStatus(status);
			}
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int statusID = buf.readInt();
		if (statusID >= 0 && statusID < STATUS.values().length)
		{
			status = STATUS.values()[statusID];
		}
		else
		{
			status = STATUS.NO_RIGHT;
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(status.ordinal());
	}

	public void setStatus(STATUS status)
	{
		this.status = status;
	}

}
