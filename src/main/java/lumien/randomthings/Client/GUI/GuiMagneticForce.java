package lumien.randomthings.Client.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.GUI.Elements.GuiSlotPlayerList;
import lumien.randomthings.Container.ContainerMagneticForce;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Network.Messages.MessageAnswerTeleport;
import lumien.randomthings.Network.Messages.MessageRequestTeleport;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiMagneticForce extends GuiContainer
{
	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/magneticForce.png");
	GuiSlotPlayerList playerList;
	ArrayList<String> players;

	boolean selected;

	int counter;
	
	MessageAnswerTeleport.STATUS status;

	public GuiMagneticForce()
	{
		super(new ContainerMagneticForce());

		this.xSize = 122;
		this.ySize = 135;
		counter = 0;
		
		status = null;

		selected = false;
	}

	public void pressedPlayer(String player)
	{
		if (!selected)
		{
			selected = true;
			
			MessageRequestTeleport request = new MessageRequestTeleport();
			request.setUsername(player);
			PacketHandler.INSTANCE.sendToServer(request);
		}
	}
	
	public void setStatus(MessageAnswerTeleport.STATUS status)
	{
		this.status = status;
		selected = false;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		int guiX = (width / 2 - xSize / 2);
		int guiY = (height / 2 - ySize / 2);

		players = RandomThings.proxy.getUsernameList();
		players.remove(mc.thePlayer.getCommandSenderName());
		playerList = new GuiSlotPlayerList(this, this.mc, 100, 110, guiX + xSize / 2 - 100 / 2, guiY - 10 + ySize / 2 - 100 / 2 + 10, players);
	}

	public void updateScreen()
	{
		super.updateScreen();
		counter++;

		if (counter % 5 == 0)
		{
			players.removeAll(new ArrayList<String>(players));
			players.addAll(RandomThings.proxy.getUsernameList());
			players.remove(mc.thePlayer.getCommandSenderName());
		}
	}

	public int getGuiLeft()
	{
		return guiLeft;
	}

	public int getGuiTop()
	{
		return guiTop;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		if (status!=null)
		{
			String toDraw = "";
			switch (status)
			{
				case INVALID_USERNAME:
					toDraw = I18n.format("text.magneticForce.invalidUsername", new Object[0]);
					break;
				case NOT_ONLINE:
					toDraw = I18n.format("text.magneticForce.notOnline", new Object[0]);
					break;
				case NO_RIGHT:
					toDraw = I18n.format("text.magneticForce.noRights", new Object[0]);
					break;
				case SAME_PLAYER:
					toDraw = I18n.format("text.magneticForce.notYourself", new Object[0]);
					break;
				default:
					break;
			}
			this.drawCenteredString(fontRendererObj, toDraw, xSize/2, 5, 0xFFFFFF);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	public void drawScreen(int p_571_1_, int p_571_2_, float p_571_3_)
	{
		super.drawScreen(p_571_1_, p_571_2_, p_571_3_);
		GL11.glDisable(GL11.GL_LIGHTING);
		playerList.drawScreen(p_571_1_, p_571_2_, p_571_3_);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

}
