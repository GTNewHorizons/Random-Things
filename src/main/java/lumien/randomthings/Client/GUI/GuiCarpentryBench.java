package lumien.randomthings.Client.GUI;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Container.ContainerCarpentryBench;
import lumien.randomthings.Items.ItemCarpentryPattern;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiCarpentryBench extends GuiContainer
{
	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/carpentryBench.png");

	final ResourceLocation overlay_workbench = new ResourceLocation("randomthings:textures/gui/carpentryBench_overlay_workbench.png");

	public GuiCarpentryBench(InventoryPlayer playerInventory, World worldObj, int posX, int posY, int posZ)
	{
		super(new ContainerCarpentryBench(playerInventory, worldObj, posX, posY, posZ));

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		this.mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		
		ItemStack pattern = this.inventorySlots.getSlot(0).getStack();
		if (pattern != null)
		{
			if (pattern.getItemDamage() < ItemCarpentryPattern.PatternType.values().length)
			{
				ResourceLocation overlayToDraw = null;
				switch (ItemCarpentryPattern.PatternType.values()[pattern.getItemDamage()])
				{
					case CHEST:
						break;
					case WORKBENCH:
						this.drawTexturedModalRect(x + 47, y + 16, 202, 0, 54, 54);
						break;
					default:
						break;
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{

	}
}
