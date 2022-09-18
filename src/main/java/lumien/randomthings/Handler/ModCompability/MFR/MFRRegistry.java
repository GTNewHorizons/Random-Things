package lumien.randomthings.Handler.ModCompability.MFR;

import java.lang.reflect.Method;

public class MFRRegistry {
    public static void registerSafariNetBlacklist(Class<?> blacklistedEntity) {
        try {
            Class<?> registry = Class.forName("powercrystals.minefactoryreloaded.MFRRegistry");
            Method reg = registry.getMethod("registerSafariNetBlacklist", Class.class);
            reg.invoke(registry, blacklistedEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
