package lumien.randomthings.Client.GUI;

import java.util.ArrayList;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Container.ContainerEnderEnergyDistributor;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnderEnergyDistributor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class GuiEnderEnergyDistributor extends GuiContainer {
    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/enderEnergyDistributor.png");
    int maxStorage;

    float div;

    public GuiEnderEnergyDistributor(InventoryPlayer inventoryPlayer, TileEntityEnderEnergyDistributor distributor) {
        super(new ContainerEnderEnergyDistributor(inventoryPlayer, distributor));

        this.xSize = 176;
        this.ySize = 159;
        this.maxStorage = distributor.getMaxEnergyStored(ForgeDirection.UP);
        div = 138F / maxStorage;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
        this.mc.renderEngine.bindTexture(background);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int stored = ((ContainerEnderEnergyDistributor) this.inventorySlots).buffer;

        float energyWidth = div * stored;
        this.drawTexturedModalRect(x + 19, y + 21, 0, 159, (int) (Math.floor(energyWidth)), 7);
        this.drawCenteredString(
                fontRendererObj,
                stored + "/" + Settings.ENDER_ENERGY_DISTRIBUTOR_BUFFERSIZE + " RF",
                x + 176 / 2,
                y + 21,
                13107200);

        if (mouseX > x && mouseX < x + 35 && mouseY > y + 33 && mouseY < y + 33 + 10) {
            ArrayList<String> toDraw = new ArrayList<>();
            toDraw.add("Energy distributed last tick");
            this.drawHoveringText(toDraw, x, y + 35 + 30, fontRendererObj);
        } else if (mouseX > x + 103 && mouseX < x + 103 + 20 && mouseY > y + 30 && mouseY < y + 33 + 10) {
            ArrayList<String> toDraw = new ArrayList<>();
            toDraw.add("Machines Connected");
            this.drawHoveringText(toDraw, x, y + 35 + 30, fontRendererObj);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(I18n.format("tile.enderEnergyDistributor.name"), 14, 6, 4210752);

        int distributed = ((ContainerEnderEnergyDistributor) this.inventorySlots).energyDistributedLastTick;
        fontRendererObj.drawString(
                "Edlt: " + distributed + "/" + Settings.ENDER_ENERGY_DISTRIBUTOR_PERTICK + " RF", 16, 34, 4210752);

        int machinesConnected = ((ContainerEnderEnergyDistributor) this.inventorySlots).machinesConnected;
        fontRendererObj.drawString(
                "MC: " + machinesConnected + "/" + Settings.ENDER_ENERGY_DISTRIBUTOR_MAXMACHINES, 103, 34, 4210752);
    }
}
