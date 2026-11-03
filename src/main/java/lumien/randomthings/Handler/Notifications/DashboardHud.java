package lumien.randomthings.Handler.Notifications;

import net.minecraft.client.gui.ScaledResolution;

import com.gtnewhorizons.dashboard.api.HudElement;
import com.gtnewhorizons.dashboard.api.HudRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Moves and hides the notification card with the achievement popup in Dashboard's HUD editor, since the card looks like
 * that popup and shows at the same spot. The only class using Dashboard, so only call it while Dashboard is loaded.
 */
@SideOnly(Side.CLIENT)
public final class DashboardHud {

    private static final String ACHIEVEMENT = "minecraft:achievement";

    private static boolean moved;

    private DashboardHud() {}

    /**
     * False means the player hid the achievement popup, so skip drawing the card. Without that element, the card is
     * drawn at its usual spot.
     */
    public static boolean beginNotification(ScaledResolution resolution) {
        HudElement achievement = HudRegistry.getElement(ACHIEVEMENT);
        if (achievement == null) {
            return true;
        }
        moved = HudRegistry.beginRender(achievement, resolution);
        return moved;
    }

    public static void endNotification() {
        if (moved) {
            moved = false;
            HudRegistry.endRender();
        }
    }
}
