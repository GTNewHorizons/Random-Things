package lumien.randomthings.Client.GUI.Elements;

import java.util.ArrayList;

import lumien.randomthings.Client.GUI.GuiMagneticForce;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.client.GuiScrollingList;

public class GuiSlotPlayerList extends GuiScrollingList
{
	private ArrayList<String> players;
	private GuiMagneticForce parent;

	public GuiSlotPlayerList(GuiMagneticForce parent, Minecraft client, int width, int height, int posX, int posY, ArrayList<String> playerList)
	{
		super(client, width, height, posY, posY + height, posX, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);

		this.players = playerList;
		this.parent = parent;
	}

	@Override
	protected int getSize()
	{
		return players.size();
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick)
	{
		parent.pressedPlayer(players.get(index));
	}

	@Override
	protected boolean isSelected(int index)
	{
		return false;
	}

	@Override
	protected void drawBackground()
	{

	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5)
	{
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int factor = scaledResolution.getScaleFactor();

		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(left * factor, Minecraft.getMinecraft().displayHeight - top * factor - listHeight * factor, listWidth * factor, listHeight * factor);
		String player = players.get(var1);
		int color = 0xFFFFFF;

		Rectangle slotRect = new Rectangle(this.left + 3, var3, fontRenderer.getStringWidth(player), fontRenderer.FONT_HEIGHT);

		if (slotRect.contains(mouseX, mouseY))
		{
			color = 0xFFD700;
		}

		fontRenderer.drawString(player, this.left + 3, var3, color);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

}
