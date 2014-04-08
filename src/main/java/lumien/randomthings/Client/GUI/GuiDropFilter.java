package lumien.randomthings.Client.GUI;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Container.ContainerDropFilter;
import lumien.randomthings.Container.ContainerVoidStone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDropFilter extends GuiContainer
{
	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/dropFilter.png");

	public GuiDropFilter(IInventory inventoryPlayer, IInventory inventoryDropFilter)
	{
		super(new ContainerDropFilter(inventoryPlayer, inventoryDropFilter));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItemDamage() == 0)
		{
			fontRendererObj.drawString(I18n.format("item.dropFilter.name", new Object[0]), 8, 6, 4210752);
		}
		else
		{
			fontRendererObj.drawString(I18n.format("item.dropFilterVoiding.name", new Object[0]), 8, 6, 4210752);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		this.mc.renderEngine.bindTexture(background);
		// this.mc.renderEngine.bindTexture("/gui/demo_bg.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
