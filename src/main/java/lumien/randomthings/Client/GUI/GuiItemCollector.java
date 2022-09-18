package lumien.randomthings.Client.GUI;

import lumien.randomthings.Container.ContainerItemCollector;
import lumien.randomthings.Network.Messages.MessageItemCollector;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiItemCollector extends GuiContainer {
    TileEntityAdvancedItemCollector te;
    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/itemCollector.png");
    GuiButton minusX, plusX;
    GuiButton minusY, plusY;
    GuiButton minusZ, plusZ;

    GuiButton filter;

    public GuiItemCollector(InventoryPlayer inventoryPlayer, TileEntityAdvancedItemCollector te) {
        super(new ContainerItemCollector(inventoryPlayer, te));

        this.te = te;

        this.xSize = 176;
        this.ySize = 208;
    }

    @Override
    public void initGui() {
        super.initGui();

        minusX = new GuiButton(0, (width / 2) - (xSize / 2) + 25, (height / 2) - (ySize / 2) + 20, 20, 20, "-");
        plusX = new GuiButton(1, (width / 2) + (xSize / 2) - 25 - 20, (height / 2) - (ySize / 2) + 20, 20, 20, "+");

        minusY = new GuiButton(2, (width / 2) - (xSize / 2) + 25, (height / 2) - (ySize / 2) + 45, 20, 20, "-");
        plusY = new GuiButton(3, (width / 2) + (xSize / 2) - 25 - 20, (height / 2) - (ySize / 2) + 45, 20, 20, "+");

        minusZ = new GuiButton(4, (width / 2) - (xSize / 2) + 25, (height / 2) - (ySize / 2) + 70, 20, 20, "-");
        plusZ = new GuiButton(5, (width / 2) + (xSize / 2) - 25 - 20, (height / 2) - (ySize / 2) + 70, 20, 20, "+");

        this.buttonList.add(minusX);
        this.buttonList.add(plusX);

        this.buttonList.add(minusY);
        this.buttonList.add(plusY);

        this.buttonList.add(minusZ);
        this.buttonList.add(plusZ);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        MessageItemCollector packet = null;
        if (par1GuiButton == minusX) {
            te.rangeX--;
        } else if (par1GuiButton == plusX) {
            te.rangeX++;
        } else if (par1GuiButton == minusY) {
            te.rangeY--;
        } else if (par1GuiButton == plusY) {
            te.rangeY++;
        } else if (par1GuiButton == minusZ) {
            te.rangeZ--;
        } else if (par1GuiButton == plusZ) {
            te.rangeZ++;
        }

        if (te.rangeX < 0) {
            te.rangeX = 0;
        } else if (te.rangeX > 10) {
            te.rangeX = 10;
        } else if (te.rangeY < 0) {
            te.rangeY = 0;
        } else if (te.rangeY > 10) {
            te.rangeY = 10;
        } else if (te.rangeZ < 0) {
            te.rangeZ = 0;
        } else if (te.rangeZ > 10) {
            te.rangeZ = 10;
        } else {
            packet = new MessageItemCollector(te);
            PacketHandler.INSTANCE.sendToServer(packet);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.mc.renderEngine.bindTexture(background);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        fontRendererObj.drawString(I18n.format("tile.advancedItemCollector.name", new Object[0]), 8, 6, 4210752);

        String radiusX = "Radius X: " + te.rangeX;
        String radiusY = "Radius Y: " + te.rangeY;
        String radiusZ = "Radius Z: " + te.rangeZ;

        fontRendererObj.drawString(radiusX, xSize / 2 - fontRendererObj.getStringWidth(radiusX) / 2, 25, 4210752);
        fontRendererObj.drawString(radiusY, xSize / 2 - fontRendererObj.getStringWidth(radiusY) / 2, 50, 4210752);
        fontRendererObj.drawString(radiusZ, xSize / 2 - fontRendererObj.getStringWidth(radiusZ) / 2, 75, 4210752);

        fontRendererObj.drawString("Filter: ", 48, 103, 0x000000);
    }
}
