package lumien.randomthings.Client.GUI;

import java.util.ArrayList;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Container.ContainerEnergyDistributor;
import lumien.randomthings.TileEntities.EnergyDistributors.TileEntityEnergyDistributor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class GuiEnergyDistributor extends GuiContainer {
    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/energyDistributor.png");
    int maxStorage;

    float div;

    public GuiEnergyDistributor(TileEntityEnergyDistributor distributor) {
        super(new ContainerEnergyDistributor(distributor));

        this.xSize = 160;
        this.ySize = 59;
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

        int stored = ((ContainerEnergyDistributor) this.inventorySlots).buffer;

        float energyWidth = div * stored;
        this.drawTexturedModalRect(x + 11, y + 21, 0, 80, (int) (Math.floor(energyWidth)), 7);
        this.drawCenteredString(
                fontRendererObj,
                stored + "/" + Settings.ENERGY_DISTRIBUTOR_BUFFERSIZE + " RF",
                x + 11 + 138 / 2,
                y + 21,
                13107200);

        if (mouseX > x && mouseX < x + 35 && mouseY > y + 33 && mouseY < y + 33 + 14) {
            ArrayList<String> toDraw = new ArrayList<String>();
            toDraw.add("Energy distributed last tick");
            this.drawHoveringText(toDraw, x, y + 35 + 30, fontRendererObj);
        } else if (mouseX > x + 103 && mouseX < x + 103 + 20 && mouseY > y + 33 && mouseY < y + 33 + 14) {
            ArrayList<String> toDraw = new ArrayList<String>();
            toDraw.add("Machines Connected");
            this.drawHoveringText(toDraw, x, y + 35 + 30, fontRendererObj);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(I18n.format("tile.energyDistributor.name", new Object[0]), 8, 6, 4210752);

        int distributed = ((ContainerEnergyDistributor) this.inventorySlots).energyDistributedLastTick;
        fontRendererObj.drawString(
                "Edlt: " + distributed + "/" + Settings.ENERGY_DISTRIBUTOR_PERTICK + " RF", 8, 35, 4210752);

        int machinesConnected = ((ContainerEnergyDistributor) this.inventorySlots).machinesConnected;
        fontRendererObj.drawString(
                "MC: " + machinesConnected + "/" + Settings.ENERGY_DISTRIBUTOR_MAXMACHINES, 103, 35, 4210752);
    }
}
