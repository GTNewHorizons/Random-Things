package lumien.randomthings.Client.GUI;

import lumien.randomthings.Container.ContainerSoundRecorder;
import lumien.randomthings.RandomThings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSoundRecorder extends GuiContainer {
    final ResourceLocation background = new ResourceLocation("randomthings:textures/gui/soundRecorder.png");

    public GuiSoundRecorder() {
        super(new ContainerSoundRecorder());

        this.xSize = 181;
        this.ySize = 130;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        fontRendererObj.drawString(I18n.format("item.soundRecorder.name", new Object[0]), 5, 6, 400000);

        for (int i = 0; i < RandomThings.instance.soundRecorderHandler.playedSounds.size(); i++) {
            String s = RandomThings.instance.soundRecorderHandler.playedSounds.get(i);
            if (!s.equals("")) {
                fontRendererObj.drawString(s, 5, 6 + fontRendererObj.FONT_HEIGHT * (i + 1), 4210752);
            }
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
}
