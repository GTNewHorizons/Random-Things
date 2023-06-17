package lumien.randomthings.Client.Renderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lumien.randomthings.Entity.EntityHealingOrb;

public class RenderHealingOrb extends Render {

    private static final ResourceLocation experienceOrbTextures = new ResourceLocation(
            "RandomThings:textures/entitys/healingOrb.png");

    public RenderHealingOrb() {
        this.shadowSize = 0F;
        this.shadowOpaque = 0F;
    }

    public void doRender(EntityHealingOrb entityHealingOrb, double par2, double par4, double par6, float par8,
            float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        this.bindEntityTexture(entityHealingOrb);
        int i = 1;
        float f2 = (i % 4 * 16) / 64.0F;
        float f3 = (i % 4 * 16 + 16) / 64.0F;
        float f4 = (i / 4f * 16) / 64.0F;
        float f5 = (i / 4f * 16 + 16) / 64.0F;
        float f6 = 1.0F;
        float f7 = 1F;
        float f8 = 1F;
        int j = entityHealingOrb.getBrightnessForRender(par9);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k, l);
        // GL11.glEnable(GL11.GL_BLEND);
        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f9 = 0.3F;
        GL11.glScalef(f9, f9, f9);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
        tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
        tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
        tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityHealingOrb par1EntityXPOrb) {
        return experienceOrbTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityHealingOrb) par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityHealingOrb) par1Entity, par2, par4, par6, par8, par9);
    }
}
