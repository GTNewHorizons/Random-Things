package lumien.randomthings.Client.Renderer;

import lumien.randomthings.Items.ItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderRTItem implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        RenderItem ri = new RenderItem();
        RenderBlocks rb = (RenderBlocks) data[0];
        ri.renderItemIntoGUI(
                Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), item, 0, 0, true);
        if (((ItemBase) item.getItem()).showDamage(item)) {
            double health = item.getItem().getDurabilityForDisplay(item);
            int j1 = (int) Math.round(13.0D - health * 13.0D);
            int k = (int) Math.round(255.0D - health * 255.0D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            Tessellator tessellator = Tessellator.instance;

            int color = ((ItemBase) item.getItem()).getDurabilityColor(item, health);
            int l = color;
            int i1 = (255 - k) / 4 << 16 | 16128;
            this.renderQuad(tessellator, 0 + 2, 0 + 13, 13, 2, 0);
            this.renderQuad(tessellator, 0 + 2, 0 + 13, 12, 1, i1);
            this.renderQuad(tessellator, 0 + 2, 0 + 13, j1, 1, l);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void renderQuad(Tessellator par1Tessellator, int par2, int par3, int par4, int par5, int par6) {
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorOpaque_I(par6);
        par1Tessellator.addVertex(par2 + 0, par3 + 0, 0.0D);
        par1Tessellator.addVertex(par2 + 0, par3 + par5, 0.0D);
        par1Tessellator.addVertex(par2 + par4, par3 + par5, 0.0D);
        par1Tessellator.addVertex(par2 + par4, par3 + 0, 0.0D);
        par1Tessellator.draw();
    }
}
