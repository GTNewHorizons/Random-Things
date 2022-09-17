package lumien.randomthings.Client;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    static Gui gui = new Gui();

    public static void fillAreaWithIcon(IIcon icon, int x, int y, int width, int height) {
        int scale = computeGuiScale();

        glScissor(
                0, Minecraft.getMinecraft().displayHeight - (y + height) * scale, (width + x) * scale, height * scale);
        glEnable(GL_SCISSOR_TEST);

        int cols = MathHelper.ceiling_float_int(width / 16F);
        int rows = MathHelper.ceiling_float_int(height / 16F);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                gui.drawTexturedModelRectFromIcon(x + col * 16, y + row * 16, icon, 16, 16);
            }
        }

        glDisable(GL_SCISSOR_TEST);
    }

    public static void drawTexturedModalRect(
            int p_73729_1_,
            int p_73729_2_,
            int p_73729_3_,
            int p_73729_4_,
            int p_73729_5_,
            int p_73729_6_,
            int zLevel) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(
                p_73729_1_ + 0, p_73729_2_ + p_73729_6_, zLevel, (p_73729_3_ + 0) * f, (p_73729_4_ + p_73729_6_) * f1);
        tessellator.addVertexWithUV(
                p_73729_1_ + p_73729_5_,
                p_73729_2_ + p_73729_6_,
                zLevel,
                (p_73729_3_ + p_73729_5_) * f,
                (p_73729_4_ + p_73729_6_) * f1);
        tessellator.addVertexWithUV(
                p_73729_1_ + p_73729_5_, p_73729_2_ + 0, zLevel, (p_73729_3_ + p_73729_5_) * f, (p_73729_4_ + 0) * f1);
        tessellator.addVertexWithUV(
                p_73729_1_ + 0, p_73729_2_ + 0, zLevel, (p_73729_3_ + 0) * f, (p_73729_4_ + 0) * f1);
        tessellator.draw();
    }

    public static void drawTexturedQuad(int x, int y, int width, int height, double zLevel) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0, 1);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1, 0);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
    }

    public static float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_) {
        float f3;

        for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F) {}

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
        }

        return p_77034_1_ + p_77034_3_ * f3;
    }

    public static Color getAverageIconColor(IIcon icon) {
        TextureAtlasSprite sprite = (TextureAtlasSprite) icon;
        int x = sprite.getOriginX();
        int y = sprite.getOriginY();

        int width = sprite.getIconWidth();
        int height = sprite.getIconHeight();

        int textureMapID = Minecraft.getMinecraft().getTextureMapBlocks().getGlTextureId();
        GL11.glBindTexture(GL_TEXTURE_2D, textureMapID);

        int textureWidth = (int) GL11.glGetTexLevelParameterf(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);
        int textureHeight = (int) GL11.glGetTexLevelParameterf(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT);

        IntBuffer ib = BufferUtils.createIntBuffer(textureWidth * textureHeight * 4);
        GL11.glGetTexImage(GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_INT, ib);
        GL11.glBindTexture(GL_TEXTURE_2D, 0);

        int startPos = y * width + x;
        ib.rewind();
        for (int i = 0; i < ib.capacity(); i++) {
            int color = ib.get();
            Color c = new Color(color);
            if (c.getRed() == 105 && c.getGreen() == 105 && c.getBlue() == 106) {
                System.out.println("Found at " + i);
            }
        }

        return null;
    }

    public static int computeGuiScale() {
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;

        int k = mc.gameSettings.guiScale;

        if (k == 0) {
            k = 1000;
        }

        while (scaleFactor < k
                && mc.displayWidth / (scaleFactor + 1) >= 320
                && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        return scaleFactor;
    }

    public static void drawFluidStack(FluidTankInfo tankInfo, int x, int y, int width, int fullHeight) {
        TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;

        FluidStack fluidStack = tankInfo.fluid;

        if (fluidStack != null) {
            Fluid fluid = fluidStack.getFluid();
            IIcon fluidIcon = fluid.getStillIcon();
            int fluidHeight =
                    MathHelper.ceiling_float_int((fluidStack.amount / (float) tankInfo.capacity) * fullHeight);

            GL11.glDisable(GL_BLEND);
            glColor3f(1, 1, 1);
            renderEngine.bindTexture(renderEngine.getResourceLocation(fluid.getSpriteNumber()));
            fillAreaWithIcon(fluidIcon, x, y + fullHeight - fluidHeight, width, fluidHeight);
        }
    }

    public static void drawCube(float posX, float posY, float posZ, float size) {
        drawCube(posX, posY, posZ, size, 1, 1, 1, 0.5f);
    }

    public static void drawCube(
            float posX, float posY, float posZ, float size, float red, float green, float blue, float alpha) {
        Minecraft.getMinecraft().entityRenderer.disableLightmap(0);

        Tessellator t = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, posZ);

        t.startDrawingQuads();
        t.setColorRGBA_F(red, green, blue, alpha);

        t.addVertex(0F, 0F, 0F); // P1
        t.addVertex(0F, size, 0F); // P2
        t.addVertex(size, size, 0F); // P3
        t.addVertex(size, 0F, 0F); // P4

        t.addVertex(size, size, 0F); // P1
        t.addVertex(size, size, size); // P2
        t.addVertex(size, 0F, size); // P3
        t.addVertex(size, 0F, 0F); // P4

        t.addVertex(size, size, size); // P1
        t.addVertex(0F, size, size); // P1
        t.addVertex(0F, 0F, size); // P1
        t.addVertex(size, 0F, size); // P1

        t.addVertex(0F, size, size); // P1
        t.addVertex(0F, size, 0F); // P1
        t.addVertex(0F, 0F, 0F); // P1
        t.addVertex(0F, 0F, size); // P1

        t.addVertex(0F, 0F, 0F); // P1
        t.addVertex(size, 0F, 0F); // P1
        t.addVertex(size, 0F, size); // P1
        t.addVertex(0F, 0F, size); // P1

        t.addVertex(0F, size, 0F); // P1
        t.addVertex(0F, size, size); // P2
        t.addVertex(size, size, size); // P3
        t.addVertex(size, size, 0F); // P4

        t.draw();

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
    }

    public static void enableDefaultBlending() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
