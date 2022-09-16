package lumien.randomthings.Client.Renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Entity.EntityBloodmoonCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderBloodmoonCircle extends Render {
    ResourceLocation texture = new ResourceLocation("RandomThings:textures/entitys/bloodmoonCircle.png");

    float ageMod = 1F / 150F;

    private void doRender(EntityBloodmoonCircle circle, double posX, double posY, double posZ, float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        RenderUtils.enableDefaultBlending();
        Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
        GL11.glTranslatef((float) posX + 0.5f, (float) posY + 0.001f, (float) posZ + 0.5f);

        this.bindEntityTexture(circle);

        GL11.glTranslatef(-0.5f, 0, -0.5f);

        if (circle.age < 100) {
            GL11.glScalef(
                    circle.age * 0.03f + partialTickTime * 0.03f, 1, circle.age * 0.03f + partialTickTime * 0.03f);
        } else {
            GL11.glScalef(3.01F, 1, 3.01F);
        }

        // GL11.glRotatef(circle.age * 10 + partialTickTime * 10, 0f, 1, 0f);
        GL11.glTranslatef(0.5f, 0, 0.5f);
        GL11.glRotatef(90, 1, 0, 0);

        int i = 1;
        float f2 = 1;
        float f3 = 0;
        float f4 = 0;
        float f5 = 1;

        float f6 = 1.0F;
        float f7 = 1F;
        float f8 = 1F;

        if (circle.age > 150) {
            int dif = circle.age - 150;
            if (circle.age < 300) {
                GL11.glColor3f(1, 1 - dif * ageMod, 1 - dif * ageMod);
            } else {
                GL11.glColor3f(1, 0, 0);
            }
        }

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
        tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
        tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
        tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);

        tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
        tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
        tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
        tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(
            Entity p_76986_1_,
            double p_76986_2_,
            double p_76986_4_,
            double p_76986_6_,
            float p_76986_8_,
            float p_76986_9_) {
        doRender((EntityBloodmoonCircle) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return texture;
    }
}
