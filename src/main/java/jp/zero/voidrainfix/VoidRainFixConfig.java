package jp.zero.voidrainfix;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

/**
 * Config for Void Rain Fix (config/voidrainfix-common.toml).
 */
public final class VoidRainFixConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSIONS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment(
                "Dimensions in which rain is always treated as \"not raining\".",
                "The shared weather state itself is NOT changed: the overworld keeps",
                "raining as usual, but inside the listed dimensions Level#isRaining()",
                "returns false. This keeps GregTech solar boilers working in the",
                "JAVD void dimension.",
                "Entries are dimension IDs, e.g. \"javd:void\".");
        DIMENSIONS = builder.defineList("rainDisabledDimensions",
                List.of("javd:void"),
                entry -> entry instanceof String);
        SPEC = builder.build();
    }

    private VoidRainFixConfig() {
    }
}
