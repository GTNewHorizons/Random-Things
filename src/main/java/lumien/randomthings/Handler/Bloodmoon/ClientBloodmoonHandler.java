package lumien.randomthings.Handler.Bloodmoon;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import lumien.randomthings.Configuration.Settings;

public class ClientBloodmoonHandler {

    public static final ClientBloodmoonHandler INSTANCE = new ClientBloodmoonHandler();
    boolean bloodMoon;

    public float lightSub;
    public float fogRemove;
    private float skyColorAdd;

    static float d = 1f / 15000f;

    private ClientBloodmoonHandler() {
        bloodMoon = false;
    }

    public void tick() {
        final World world = Minecraft.getMinecraft().theWorld;
        if (world != null) {
            if (bloodMoon) {
                if (Settings.BLOODMOON_DIM_WHITELIST.length != 0
                        && !ArrayUtils.contains(Settings.BLOODMOON_DIM_WHITELIST, world.provider.dimensionId)) {
                    bloodMoon = false;
                    return;
                }

                float angle = world.getCelestialAngle(1.0F);
                // 0.215 (dusk), 0.785 (dawn)
                float nightProgress = (angle - 0.215F) / (0.785F - 0.215F);
                nightProgress = Math.max(0.0F, Math.min(1.0F, nightProgress));
                float sinValue = (float) Math.sin(nightProgress * Math.PI);
                lightSub = sinValue * 150f;
                skyColorAdd = sinValue * 0.1f;
                fogRemove = sinValue * d * 6000f;
            } else {
                lightSub = 0f;
                skyColorAdd = 0f;
                fogRemove = 0f;
            }
        }
    }

    public static void moonColorHook() {
        if (Settings.BLOODMOON_VISUAL_REDMOON && ClientBloodmoonHandler.INSTANCE.bloodMoon) {
            GL11.glColor3f(0.8f, 0, 0);
        }
    }

    public static void skyColorHook(Vec3 color) {
        if (Settings.BLOODMOON_VISUAL_REDSKY && ClientBloodmoonHandler.INSTANCE.bloodMoon) {
            color.xCoord += INSTANCE.skyColorAdd;
        }
    }

    public void setBloodmoon(boolean bloodMoon) {
        this.bloodMoon = bloodMoon;
    }

    public boolean isBloodmoonActive() {
        return bloodMoon;
    }
}
