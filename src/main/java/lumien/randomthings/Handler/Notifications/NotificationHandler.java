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

import lumien.randomthings.Library.ClientUtil;

public class NotificationHandler {

    int displayCounter;

    final float teiler = 32F / 20F;

    Gui guiInstance = new Gui();
    Minecraft mc = Minecraft.getMinecraft();
    RenderItem itemRenderer;

    ResourceLocation notificationBackground = new ResourceLocation(
            "textures/gui/achievement/achievement_background.png");

    ConcurrentLinkedQueue<Notification> queuedNotifications;
    Notification currentNotification;

    public NotificationHandler() {
        queuedNotifications = new ConcurrentLinkedQueue<>();
        currentNotification = null;

        displayCounter = 0;
        itemRenderer = new RenderItem();
    }

    public void drawNotificationOverlay(float partialTickTime) {
        if (currentNotification != null) {
            ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

            int drawY;
            if (displayCounter < 20) {
                drawY = (int) (teiler * (displayCounter + partialTickTime - 20F));
            } else if (displayCounter > currentNotification.duration + 20) {
                float dif = currentNotification.duration + 40 - displayCounter - partialTickTime;
                drawY = (int) (0f - (teiler * (20f - dif)));
            } else {
                drawY = 0;
            }

            mc.getTextureManager().bindTexture(notificationBackground);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            // Same projection as vanilla's GuiAchievement, so the card sits flush with the screen edge
            int width = scaledresolution.getScaledWidth();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0, width, scaledresolution.getScaledHeight(), 0, 1000, 3000);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            int cardX = width - 160;
            guiInstance.drawTexturedModalRect(cardX, drawY, 96, 202, 160, 32);

            // Text and icon layout matches vanilla's GuiAchievement popup
            FontRenderer f = mc.fontRenderer;
            f.drawString(currentNotification.title, cardX + 30, drawY + 7, 0xFAFAFA, false);
            f.drawString(currentNotification.description, cardX + 30, drawY + 18, 0xAAAAAA, false);

            // Same item lighting setup and cleanup as the vanilla hotbar
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();
            itemRenderer.renderItemAndEffectIntoGUI(
                    f,
                    mc.getTextureManager(),
                    currentNotification.icon,
                    cardX + 8,
                    drawY + 8);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
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
