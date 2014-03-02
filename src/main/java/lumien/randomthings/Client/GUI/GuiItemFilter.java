package lumien.randomthings.Client.GUI;

import lumien.randomthings.Container.ContainerItemFilter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiItemFilter extends GuiContainer
{
	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/itemFilter.png");

	public GuiItemFilter(IInventory inventoryPlayer, IInventory inventoryFilter)
	{
		super(new ContainerItemFilter(inventoryPlayer, inventoryFilter));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.bindTexture(background);
		// this.mc.renderEngine.bindTexture("/gui/demo_bg.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRendererObj.drawString("Item Filter", 8, 6, 4210752);
	}
}
