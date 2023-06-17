package lumien.randomthings.Client.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Container.ContainerDropFilter;

public class GuiDropFilter extends GuiContainer {

    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/dropFilter.png");
    ItemStack itemDropFilter;

    public GuiDropFilter(EntityPlayer player, IInventory inventoryPlayer, IInventory inventoryDropFilter) {
        super(new ContainerDropFilter(inventoryPlayer, inventoryDropFilter));

        this.itemDropFilter = player.getCurrentEquippedItem();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        if (this.itemDropFilter.getItemDamage() == 0) {
            fontRendererObj.drawString(I18n.format("item.dropFilter.name"), 8, 6, 4210752);
        } else {
            fontRendererObj.drawString(I18n.format("item.dropFilterVoiding.name"), 8, 6, 4210752);
        }
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
