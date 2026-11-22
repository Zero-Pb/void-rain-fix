package jp.zero.voidrainfix.mixin;

import jp.zero.voidrainfix.VoidRainFix;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes rain read as "not raining at all" inside the configured
 * dimensions (the JAVD void by default):
 *
 * <ul>
 *   <li>{@link Level#isRaining()} returns {@code false}</li>
 *   <li>{@link Level#getRainLevel(float)} returns {@code 0}</li>
 *   <li>{@link Level#getThunderLevel(float)} returns {@code 0}</li>
 * </ul>
 *
 * <p>The boolean check alone is enough for mods that ask "is it
 * raining?", such as GregTech's solar boilers. Other mods, such as
 * Mekanism's solar machines, scale their output by the rain and
 * thunder <em>levels</em> instead, so those levels are zeroed as
 * well. ({@code Level#isThundering()} is derived from the thunder
 * level, so it is covered too.)</p>
 *
 * <p>Only the answers of these checks change. The shared weather
 * state is never modified, so the overworld keeps its normal rain
 * cycle, and nothing about how the void dimension looks changes
 * either.</p>
 */
@Mixin(Level.class)
public abstract class LevelMixin {

    @Inject(method = "isRaining", at = @At("HEAD"), cancellable = true)
    private void voidrainfix$notRainingHere(CallbackInfoReturnable<Boolean> cir) {
        Level level = (Level) (Object) this;
        if (VoidRainFix.isRainDisabled(level.dimension())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getRainLevel", at = @At("HEAD"), cancellable = true)
    private void voidrainfix$noRainLevelHere(float partialTick, CallbackInfoReturnable<Float> cir) {
        Level level = (Level) (Object) this;
        if (VoidRainFix.isRainDisabled(level.dimension())) {
            cir.setReturnValue(0.0F);
        }
    }

    @Inject(method = "getThunderLevel", at = @At("HEAD"), cancellable = true)
    private void voidrainfix$noThunderLevelHere(float partialTick, CallbackInfoReturnable<Float> cir) {
        Level level = (Level) (Object) this;
        if (VoidRainFix.isRainDisabled(level.dimension())) {
            cir.setReturnValue(0.0F);
        }
    }
}
