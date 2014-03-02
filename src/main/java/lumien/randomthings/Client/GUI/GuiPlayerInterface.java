package lumien.randomthings.Client.GUI;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import lumien.randomthings.Container.ContainerPlayerInterface;
import lumien.randomthings.TileEntities.TileEntityPlayerInterface;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPlayerInterface extends GuiContainer
{
	TileEntityPlayerInterface te;

	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/playerInterface.png");
	final ResourceLocation background_creative = new ResourceLocation("randomthings:textures/gui/playerInterface_creative.png");

	public GuiPlayerInterface(TileEntityPlayerInterface te)
	{
		super(new ContainerPlayerInterface(te));
		this.te = te;

		this.xSize = 157;
		this.ySize = 60;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
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
	protected void drawGuiContainerForegroundLayer(int param1, int param2)
	{
		fontRendererObj.drawString("Player Interface", 8, 6, 4210752);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
	}
}
