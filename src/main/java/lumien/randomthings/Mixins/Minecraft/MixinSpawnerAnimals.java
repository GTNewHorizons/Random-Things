package lumien.randomthings.Mixins.Minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.RandomThingsNBTKeys;
import lumien.randomthings.Mixins.ducks.SpawnerAnimalsExt;

// priority : needs to run after hodgepodge (900) and after archaic fix (1000)
@Mixin(value = SpawnerAnimals.class, priority = 1050)
public class MixinSpawnerAnimals implements SpawnerAnimalsExt {

    @Unique
    private boolean rt$isBloodMoon;

    @Override
    public void rt$setBloodmoon(boolean isBloodMoon) {
        rt$isBloodMoon = isBloodMoon;
    }

    @ModifyExpressionValue(
            method = "findChunksForSpawning",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EnumCreatureType;getMaxNumberOfCreature()I"))
    private int rt$changeMaxAmountOfCreature(int original) {
        if (rt$isBloodMoon) {
            return original * Settings.BLOODMOON_SPAWNLIMIT_MULTIPLIER;
        }
        return original;
    }

    @Redirect(
            method = "findChunksForSpawning",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/SpawnerAnimals;canCreatureTypeSpawnAtLocation(Lnet/minecraft/entity/EnumCreatureType;Lnet/minecraft/world/World;III)Z"))
    private boolean rt$CanMobSpawn(EnumCreatureType type, World world, int x, int y, int z) {
        return (!rt$isBloodMoon || world.canBlockSeeTheSky(x, y, z))
                && SpawnerAnimals.canCreatureTypeSpawnAtLocation(type, world, x, y, z);
    }

    @ModifyConstant(method = "findChunksForSpawning", constant = @Constant(doubleValue = 24.0D))
    private double rt$changeSpawnRange(double original) {
        if (rt$isBloodMoon) {
            return Settings.BLOODMOON_SPAWNRANGE;
        }
        return original;
    }

    @ModifyArg(
            method = "findChunksForSpawning",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldServer;spawnEntityInWorld(Lnet/minecraft/entity/Entity;)Z"))
    private Entity rt$addBloodMoonTag(Entity entity) {
        if (rt$isBloodMoon && Settings.BLOODMOON_VANISH) {
            entity.getEntityData().setBoolean(RandomThingsNBTKeys.BLOODMOON_SPAWNED, true);
        }
        return entity;
    }

}
