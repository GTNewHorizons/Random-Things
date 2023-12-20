package lumien.randomthings.Client.Renderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Client.RenderUtils;

public class RenderBlut {

    static final ResourceLocation blut_vene = new ResourceLocation("randomthings:textures/blut/blut_vene.png");
    static final ResourceLocation blut_arterie = new ResourceLocation("randomthings:textures/blut/blut_arterie.png");
    static Minecraft mc = Minecraft.getMinecraft();

    public static double counter = 0;

    public static void renderBlut(RenderPlayerEvent.Post event) {
        glPushMatrix();
        {
            mc.entityRenderer.disableLightmap(0);
            ModelBiped mainModel = event.renderer.modelBipedMain;
            ModelRenderer body = mainModel.bipedBody;

            float rotation = RenderUtils.interpolateRotation(
                    event.entityPlayer.prevRenderYawOffset,
                    event.entityPlayer.renderYawOffset,
                    event.partialRenderTick);
            glRotatef(180.0F - rotation, 0.0F, 1.0F, 0.0F);

            glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

            ModelBox bodyBox = (ModelBox) body.cubeList.get(0);
            glTranslated(0, 0, (bodyBox.posZ2 - bodyBox.posZ1) / 32);

            glTranslated(0, -0.585, 0);

            double planeWidth = (bodyBox.posX2 - bodyBox.posX1) / 32;
            double planeHeight = (bodyBox.posY2 - bodyBox.posY1) / 32;

            Minecraft.getMinecraft().renderEngine.bindTexture(blut_arterie);

            double brightness = Math.abs(Math.sin(counter) * 0.5 + 0.5);
            GL11.glColor3d(brightness, brightness, brightness);

            glDisable(GL_LIGHTING);
            glBegin(GL_QUADS);
            {
                glTexCoord2d(0.0, 0.0);
                glVertex3d(-planeWidth, -planeHeight, 0);

                glTexCoord2d(1.0, 0.0);
                glVertex3d(planeWidth, -planeHeight, 0);

                glTexCoord2d(1.0, 1.0);
                glVertex3d(planeWidth, planeHeight, 0);

                glTexCoord2d(0.0, 1.0);
                glVertex3d(-planeWidth, planeHeight, 0);
            }
            glEnd();
            glEnable(GL_LIGHTING);

            glDisable(GL_BLEND);

            mc.entityRenderer.enableLightmap(0);
        }
        glPopMatrix();
    }
}
