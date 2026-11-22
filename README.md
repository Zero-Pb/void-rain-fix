# Void Rain Fix

A tiny utility mod for **Minecraft 1.20.1 (Forge)** that makes rain read as "not raining" inside configured dimensions.

## Why

In vanilla, weather is a single world-wide state shared by every dimension. Some mods read that global state directly — for example, GregTech's solar boilers check `Level#isRaining()` to decide whether they can see the sun. As a result, solar boilers placed in a void dimension (such as JAVD's void) cool down every time it rains in the overworld, even though the void itself is always clear.

Void Rain Fix changes that one check: inside the configured dimensions, `Level#isRaining()` returns `false`. The weather itself is never touched — the overworld keeps its normal rain cycle, and nothing about how any dimension looks changes.

Made for use alongside GregTech (e.g. in the Monifactory modpack), where solar boilers in a void base would otherwise stall during overworld rain.

## How it works

A single Mixin on `Level#isRaining()` returns `false` when the current dimension is in the config list. No JAVD (or any other mod) code is used or required — the dimension is matched by its ID string only.

## Config

`config/voidrainfix-common.toml`

```toml
rainDisabledDimensions = ["javd:void"]
```

Add or remove dimension IDs (e.g. `"modid:dimension_name"`) to choose where the rain flag reads as false.

## Building

Requires JDK 17.

```
./gradlew build
```

The built jar appears in `build/libs/voidrainfix-0.1.0.jar`. Drop it into your `mods` folder.

## Notes

- This mod does not stop actual rain anywhere. If you want fully separate per-dimension weather simulation, see [Dimensional Weather](https://modrinth.com/mod/dimensional-weather) instead.
- No dependencies beyond Forge.

## License

MIT

---

## 日本語

オーバーワールドで雨が降るたびに、void 次元の GregTech ソーラーボイラーが冷えてしまう問題を直す小さな MOD です。天気そのものには触らず、設定した次元の中だけ `isRaining()` の判定を偽に変えます。対象次元は設定ファイルで変更でき、デフォルトは `javd:void` です。
