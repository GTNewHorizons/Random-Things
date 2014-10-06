package lumien.randomthings.Client.GUI;

import java.util.ArrayList;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.GUI.Elements.GuiSlotPlayerList;
import lumien.randomthings.Container.ContainerMagneticForce;
import lumien.randomthings.Container.ContainerOpSpectreKey;
import lumien.randomthings.Library.Interfaces.IPlayerListGUI;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Network.Messages.MessageAnswerTeleport;
import lumien.randomthings.Network.Messages.MessageRequestTeleport;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ResourceLocation;

public class GuiOpSpectreKey extends GuiContainer implements IPlayerListGUI
{
	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/opSpectreKey.png");
	GuiSlotPlayerList playerList;
	public ArrayList<String> players;

	boolean selected;

	public GuiOpSpectreKey()
	{
		super(new ContainerOpSpectreKey());

		this.xSize = 122;
		this.ySize = 135;
		
		players = new ArrayList<String>();

		selected = false;
	}

	public void pressedPlayer(String player)
	{
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/rt spectre "+player);
		Minecraft.getMinecraft().thePlayer.closeScreen();
	}

	@Override
	public void initGui()
	{
		super.initGui();

		int guiX = (width / 2 - xSize / 2);
		int guiY = (height / 2 - ySize / 2);

		playerList = new GuiSlotPlayerList(this, this.mc, 100, 110, guiX + xSize / 2 - 100 / 2, guiY - 10 + ySize / 2 - 100 / 2 + 10, players);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
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
