package lumien.randomthings.Potions;

import java.awt.Color;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Library.PotionIds;

public class PotionImbue extends Potion {

    static float cf = 1F / 255F;
    Color potionColor;
    ResourceLocation icon;

    public PotionImbue(String name, int potionID, int color, ResourceLocation icon) {
        super(potionID, false, color);
        this.icon = icon;
        this.setPotionName(name);
        this.potionColor = new Color(color);
    }

    @Override
    public int getLiquidColor() {
        /* Override super and return 0 to not render potion particles on the player */
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        GL11.glColor3f(cf * potionColor.getRed(), cf * potionColor.getGreen(), cf * potionColor.getBlue());
        RenderUtils.drawTexturedModalRect(x, y, 0, 166, 140, 32, 0);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(icon);

        if (this.getId() == PotionIds.IMBUE_SPECTRE) {
            RenderUtils.enableDefaultBlending();
            GL11.glColor4f(1, 1, 1, 0.8f);
            RenderUtils.drawTexturedQuad(x + 6, y + 7, 18, 18, 300);
            GL11.glDisable(GL11.GL_BLEND);
        } else {
            RenderUtils.drawTexturedQuad(x + 6, y + 7, 18, 18, 300);
        }
    }
}
