package lumien.randomthings;

import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import lumien.randomthings.Mixins.Mixins;

@LateMixin
public class RandomThingsLateMixins implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.RandomThings.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        return Mixins.getLateMixins(loadedMods);
    }

}
