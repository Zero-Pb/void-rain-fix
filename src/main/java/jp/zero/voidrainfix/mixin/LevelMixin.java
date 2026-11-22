package jp.zero.voidrainfix.mixin;

import jp.zero.voidrainfix.VoidRainFix;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes {@link Level#isRaining()} return {@code false} inside the
 * configured dimensions (the JAVD void by default).
 *
 * <p>Only the answer of this one check changes. The shared weather state
 * is never modified, so the overworld keeps its normal rain cycle, and
 * nothing about how the void dimension looks changes either.</p>
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
}
