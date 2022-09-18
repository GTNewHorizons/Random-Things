package lumien.randomthings.Handler;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Handler.Bloodmoon.ClientBloodmoonHandler;

public class LightmapHandler {

    public static int manipulateGreen(int originalValue) {
        if (ClientBloodmoonHandler.INSTANCE.isBloodmoonActive()) {
            if (Settings.BLOODMOON_VISUAL_REDLIGHT) {
                originalValue -= ClientBloodmoonHandler.INSTANCE.lightSub;
            }
            return Math.max(originalValue, 0);
        } else {
            return originalValue;
        }
    }

    public static int manipulateBlue(int originalValue) {
        if (ClientBloodmoonHandler.INSTANCE.isBloodmoonActive()) {
            if (Settings.BLOODMOON_VISUAL_REDLIGHT) {
                originalValue -= ClientBloodmoonHandler.INSTANCE.lightSub * 1.9f;
            }
            return Math.max(originalValue, 0);
        } else {
            return originalValue;
        }
    }
}
