package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldServer.class)
public interface WorldServerAccessor {

    @Accessor
    SpawnerAnimals getAnimalSpawner();
}
