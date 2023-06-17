package lumien.randomthings.Client.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Container.ContainerVoidStone;

public class GuiVoidStone extends GuiContainer {

    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/voidstone.png");

    public GuiVoidStone(InventoryPlayer playerInventory) {
        super(new ContainerVoidStone(playerInventory));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(I18n.format("item.voidStone.name"), 8, 6, 12500670);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        this.mc.renderEngine.bindTexture(background);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
