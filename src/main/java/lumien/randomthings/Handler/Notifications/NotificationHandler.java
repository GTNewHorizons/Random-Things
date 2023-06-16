package lumien.randomthings.Handler.Notifications;

import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Library.ClientUtil;

public class NotificationHandler {

    int tickCounter;
    int tickRate;
    int displayCounter;

    final float teiler = 28F / 20F;

    Gui guiInstance = new Gui();
    Minecraft mc = Minecraft.getMinecraft();
    RenderItem itemRenderer;

    ResourceLocation notificationBackground = new ResourceLocation(
            "randomthings:textures/gui/notificationBackground.png");

    ConcurrentLinkedQueue<Notification> queuedNotifications;
    Notification currentNotification;

    int drawY;

    public NotificationHandler() {
        queuedNotifications = new ConcurrentLinkedQueue<>();
        currentNotification = null;

        tickCounter = 0;
        tickRate = 20;
        displayCounter = 0;
        itemRenderer = new RenderItem();
    }

    public void drawNotificationOverlay(float partialTickTime) {
        if (currentNotification != null) {
            ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int i = scaledresolution.getScaleFactor();

            if (displayCounter < 20) {
                drawY = (int) (teiler * (displayCounter + partialTickTime - 20F));
            } else if (displayCounter > currentNotification.duration + 20) {
                float dif = currentNotification.duration + 40 - displayCounter - partialTickTime;
                drawY = (int) (0f - (teiler * (20f - dif)));
            } else {
                drawY = 0;
            }

            this.mc.renderEngine.bindTexture(notificationBackground);
            RenderUtils.enableDefaultBlending();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            guiInstance.drawTexturedModalRect(scaledresolution.getScaledWidth() - 160, drawY, 0, 0, 160, 32);

            FontRenderer f = Minecraft.getMinecraft().fontRenderer;
            f.drawString(
                    currentNotification.title,
                    scaledresolution.getScaledWidth() - 160 + 28,
                    drawY + 5,
                    16448250,
                    true);
            f.drawString(
                    currentNotification.description,
                    scaledresolution.getScaledWidth() - 160 + 28,
                    drawY + 4 + f.FONT_HEIGHT + 2,
                    11184810,
                    true);

            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            itemRenderer.renderItemAndEffectIntoGUI(
                    f,
                    Minecraft.getMinecraft().getTextureManager(),
                    currentNotification.icon,
                    scaledresolution.getScaledWidth() - 160 + 6,
                    drawY + 6);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    public void update() {
        if (currentNotification == null) {
            if (!queuedNotifications.isEmpty()) {
                this.currentNotification = queuedNotifications.poll();
                displayCounter = 0;
                ClientUtil.broadcastEffect("notification", 2, 1);
            }
        } else {
            displayCounter++;
            if (displayCounter == currentNotification.duration + 40) {
                currentNotification = null;
            }
        }
    }

    public void addNotification(Notification n) {
        queuedNotifications.add(n);
    }
}
