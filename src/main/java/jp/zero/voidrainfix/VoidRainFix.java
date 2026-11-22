package jp.zero.voidrainfix;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Void Rain Fix.
 *
 * <p>Minecraft stores the weather in one shared world state, so
 * {@link Level#isRaining()} returns the same answer in every dimension.
 * GregTech's solar boilers read that flag, which is why they cool down in
 * the (visually always sunny) JAVD void dimension whenever it rains in the
 * overworld.</p>
 *
 * <p>This mod does NOT touch the weather itself. A small mixin makes
 * {@code isRaining()} answer {@code false} only inside the configured
 * dimensions (the JAVD void by default), so solar boilers keep working
 * there while the overworld keeps its normal rain cycle.</p>
 */
@Mod(VoidRainFix.MOD_ID)
public class VoidRainFix {
    public static final String MOD_ID = "voidrainfix";
    private static final Logger LOGGER = LogManager.getLogger();

    /** Dimensions used until the config file has been read. */
    private static final Set<String> DEFAULT_DIMENSIONS = Set.of("javd:void");

    private static volatile Set<String> rainDisabledDimensions = DEFAULT_DIMENSIONS;

    public VoidRainFix() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VoidRainFixConfig.SPEC);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(VoidRainFix::onConfigLoaded);
        modBus.addListener(VoidRainFix::onConfigReloaded);
    }

    private static void onConfigLoaded(final ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == VoidRainFixConfig.SPEC) {
            refreshDimensions();
        }
    }

    private static void onConfigReloaded(final ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == VoidRainFixConfig.SPEC) {
            refreshDimensions();
        }
    }

    private static void refreshDimensions() {
        rainDisabledDimensions = Set.copyOf(VoidRainFixConfig.DIMENSIONS.get());
        LOGGER.info("Void Rain Fix: rain reads as \"not raining\" in dimensions {}", rainDisabledDimensions);
    }

    /** Called from the Level mixin on every isRaining() check. */
    public static boolean isRainDisabled(ResourceKey<Level> dimension) {
        return rainDisabledDimensions.contains(dimension.location().toString());
    }
}
