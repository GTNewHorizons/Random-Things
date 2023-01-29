package lumien.randomthings.Client.GUI.Elements.Buttons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiButtonListtype extends GuiButton {

    ResourceLocation listTypeTextures = new ResourceLocation("randomthings:textures/gui/buttonListType.png");
    int listType;
    GuiContainer gc;

    public GuiButtonListtype(GuiContainer gc, int id, int posX, int posY, int type) {
        super(id, posX, posY, "");

        this.listType = type;
        this.gc = gc;

        this.width = 20;
        this.height = 20;
    }

    public void setType(int type) {
        this.listType = type;
    }

    public int getType() {
        return listType;
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
        if (this.visible) {
            p_146112_1_.getTextureManager().bindTexture(listTypeTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition
                    && p_146112_2_ < this.xPosition + this.width
                    && p_146112_3_ < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            this.drawTexturedModalRect(this.xPosition, this.yPosition, listType * 20, (k - 1) * 20, 20, 20);

            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);

            if (k == 2) {
                String toDraw = "";
                switch (listType) {
                    case 0:
                        toDraw = I18n.format("text.miscellaneous.whiteList");
                        break;
                    case 1:
                        toDraw = I18n.format("text.miscellaneous.blackList");
                        break;
                }

                ArrayList<String> strings = new ArrayList<>();
                strings.add(toDraw);

                GL11.glPushMatrix();
                this.drawHoveringText(strings, xPosition + 13, yPosition + 18, Minecraft.getMinecraft().fontRenderer);
                GL11.glPopMatrix();
            }
        }
    }

    protected void drawHoveringText(List<String> par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;

            for (String s : par1List) {
                int l = font.getStringWidth(s);
                if (l > k) {
                    k = l;
                }
            }

            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (i1 + k > gc.width) {
                i1 -= 28 + k;
            }

            if (j1 + k1 + 6 > gc.height) {
                j1 = gc.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0) {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
}
