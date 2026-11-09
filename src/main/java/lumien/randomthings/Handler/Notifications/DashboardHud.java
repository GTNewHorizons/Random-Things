package lumien.randomthings.Handler.Notifications;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.dashboard.api.HudBounds;
import com.gtnewhorizons.dashboard.api.HudElement;
import com.gtnewhorizons.dashboard.api.HudRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.RandomThings;

/**
 * Lets players move, resize and hide the notification card in Dashboard's HUD editor. The only class using Dashboard,
 * so only call it while Dashboard is loaded.
 */
@SideOnly(Side.CLIENT)
public final class DashboardHud {

    private static final int WIDTH = 160;
    private static final int HEIGHT = 32;

    private static final HudElement NOTIFICATION = new HudElement(
            "randomthings:notification",
            (screenWidth, screenHeight) -> new HudBounds(screenWidth - WIDTH, 0, WIDTH, HEIGHT));

    private static Notification sample;

    private DashboardHud() {}

    public static void register() {
        NOTIFICATION.setPreview(
                (screenWidth, screenHeight) -> HudRegistry.render(
                        NOTIFICATION,
                        () -> RandomThings.instance.notificationHandler
                                .drawCard(getSample(), HudRegistry.getResolution(), 0)));
        HudRegistry.register(NOTIFICATION);
    }

    /** False means the player hid the card, so skip drawing it and do not call {@link #endRender()}. */
    public static boolean beginNotification(ScaledResolution resolution) {
        return HudRegistry.beginRender(NOTIFICATION, resolution);
    }

    public static void endRender() {
        HudRegistry.endRender();
    }

    /** Made on first use, once the language and the blocks are loaded. */
    private static Notification getSample() {
        if (sample == null) {
            ItemStack icon = ModBlocks.notificationInterface != null ? new ItemStack(ModBlocks.notificationInterface)
                    : new ItemStack(Items.paper);
            sample = new Notification(
                    StatCollector.translateToLocal("tile.notificationInterface.name"),
                    "/rt notify",
                    0,
                    icon);
        }
        return sample;
    }
}
