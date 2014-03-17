package lumien.randomthings.Client.GUI;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Container.ContainerOnlineDetector;
import lumien.randomthings.Network.Packets.PacketOnlineDetector;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiOnlineDetector extends GuiContainer
{
	TileEntityOnlineDetector te;

	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/onlineDetector.png");

	GuiButton saveButton;
	GuiTextField usernameInput;

	public GuiOnlineDetector(TileEntityOnlineDetector te)
	{
		super(new ContainerOnlineDetector());
		
		this.te = te;
		this.ySize = 80;
		this.xSize = 160;
	}
	
	@Override
	protected void actionPerformed(GuiButton buttonPressed)
	{
		if (buttonPressed==saveButton && !usernameInput.getText().equals(""))
		{
			PacketOnlineDetector packet = new PacketOnlineDetector(usernameInput.getText(),te.xCoord,te.yCoord,te.zCoord,te.getWorldObj().provider.dimensionId);
			RandomThings.packetPipeline.sendToServer(packet);
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		saveButton = new GuiButton(0, (width / 2 - 50 / 2 + 50), (height / 2 + 10), 50, 20, "Save");
		this.buttonList.add(saveButton);
		Keyboard.enableRepeatEvents(true);

		usernameInput = new GuiTextField(this.fontRendererObj, (width / 2 - xSize / 2) + 5, (height / 2 + 10), 90, 20);
		usernameInput.setFocused(false);
		usernameInput.setCanLoseFocus(true);
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.usernameInput.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int pressedKey)
	{
		if (pressedKey == 1 || (!this.usernameInput.isFocused() && pressedKey == this.mc.gameSettings.keyBindInventory.getKeyCode()))
		{
			this.mc.thePlayer.closeScreen();
		}
		this.usernameInput.textboxKeyTyped(par1, pressedKey);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		usernameInput.drawTextBox();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2)
	{
		fontRendererObj.drawString("Online Detector", 8, 6, 4210752);
		String teUsername = te.getPlayerName();
		String toDraw = "";
		if (teUsername.equals(""))
		{
			toDraw = "Detecting nobody";
		}
		else
		{
			toDraw = "Detecting \"" + teUsername + "\"";
		}
		fontRendererObj.drawString(toDraw, (xSize / 2) - (fontRendererObj.getStringWidth(toDraw) / 2), 20, 4210752);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		this.usernameInput.updateCursorCounter();
	}

}
