package lumien.randomthings.Client.GUI;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Container.ContainerLiquidRouter;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.TileEntities.TileEntityFluidRouter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class GuiFluidRouter extends GuiContainer
{
	final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/liquidRouter.png");
	final ResourceLocation tankOutline = new ResourceLocation("randomthings:textures/gui/tankOutline.png");
	
	TileEntityFluidRouter te;
	
	GuiButton buttonMode;

	public GuiFluidRouter(InventoryPlayer inventoryPlayer, TileEntityFluidRouter te)
	{
		super(new ContainerLiquidRouter(inventoryPlayer,te));
		this.te = te;
		this.xSize = 176;
		this.ySize = 208;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
	}
	
	protected void actionPerformed(GuiButton pressedButton)
	{

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
		super.drawGuiContainerForegroundLayer(param1, param2);
		fontRendererObj.drawString(I18n.format("tile.fluidRouter.name", new Object[0]), 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("item.filterBlock.name", new Object[0])+":",8,22,4210752);
	}
	
	@Override
	public void updateScreen()
    {
        super.updateScreen();
    }

}
