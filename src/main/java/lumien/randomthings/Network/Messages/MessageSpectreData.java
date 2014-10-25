package lumien.randomthings.Network.Messages;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Client.GUI.GuiOpSpectreKey;
import lumien.randomthings.Network.IRTMessage;

public class MessageSpectreData implements IRTMessage
{
	ArrayList<String> playerList;

	public MessageSpectreData(ArrayList<String> playerList)
	{
		this.playerList = playerList;
	}

	public MessageSpectreData()
	{
		this.playerList = new ArrayList<String>();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int length = buf.readInt();
		for (int i = 0; i < length; i++)
		{
			playerList.add(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(playerList.size());

		for (String player : playerList)
		{
			ByteBufUtils.writeUTF8String(buf, player);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onMessage(MessageContext context)
	{
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;

		if (mc.thePlayer != null && currentScreen != null && currentScreen instanceof GuiOpSpectreKey)
		{
			((GuiOpSpectreKey) currentScreen).players.addAll(playerList);
		}
	}
}
