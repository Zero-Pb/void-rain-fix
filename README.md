# Void Rain Fix

A tiny utility mod for **Minecraft 1.20.1 (Forge)** that makes rain read as "not raining" inside configured dimensions.

## Why

In vanilla, weather is a single world-wide state shared by every dimension. Some mods read that global state directly — for example, GregTech's solar boilers check `Level#isRaining()` to decide whether they can see the sun. As a result, solar boilers placed in a void dimension (such as JAVD's void) cool down every time it rains in the overworld, even though the void itself is always clear.

Mekanism's solar machines are hit through a different route: instead of the boolean flag, they scale their output by the shared rain and thunder levels. A Solar Neutron Activator or solar generator in the void therefore loses most of its production while it rains in the overworld, even though it never stops outright.

Void Rain Fix changes those checks: inside the configured dimensions, `Level#isRaining()` returns `false` and the rain/thunder levels read as `0`. The weather itself is never touched — the overworld keeps its normal rain cycle, and nothing about how any dimension looks changes.

Made for use alongside GregTech (e.g. in the Monifactory modpack) and Mekanism, where solar machines in a void base would otherwise stall or slow down during overworld rain.

## How it works

A Mixin on `Level` makes three checks read as clear weather when the current dimension is in the config list: `Level#isRaining()` returns `false`, and `Level#getRainLevel()` / `Level#getThunderLevel()` return `0`. The boolean covers mods that ask "is it raining?" (GregTech's solar boilers), and the levels cover mods that scale solar output by rain strength instead — for example, Mekanism's Solar Neutron Activator and solar generators lose most of their output in the void during overworld rain without this. No JAVD (or any other mod) code is used or required — the dimension is matched by its ID string only.

## Config

`config/voidrainfix-common.toml`

```toml
rainDisabledDimensions = ["javd:void"]
```

Add or remove dimension IDs (e.g. `"modid:dimension_name"`) to choose where rain reads as clear.

## Building

Requires JDK 17.

```
./gradlew build
```

The built jar appears in `build/libs/voidrainfix-0.2.0.jar`. Drop it into your `mods` folder.

## Notes

- This mod does not stop actual rain anywhere. If you want fully separate per-dimension weather simulation, see [Dimensional Weather](https://modrinth.com/mod/dimensional-weather) instead.
- No dependencies beyond Forge.

## License

MIT

---

## 日本語

オーバーワールドで雨が降るたびに、void 次元の GregTech ソーラーボイラーが冷えてしまう問題を直す小さな MOD です。天気そのものには触らず、設定した次元の中だけ `isRaining()` の判定を偽に変え、雨と雷の強さも 0 として読み取らせます。雨の強さで発電量が変わる Mekanism の太陽中性子アクティベーターなどにも効きます。対象次元は設定ファイルで変更でき、デフォルトは `javd:void` です。
