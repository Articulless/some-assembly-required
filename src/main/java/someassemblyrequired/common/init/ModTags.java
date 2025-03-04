package someassemblyrequired.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.versions.forge.ForgeVersion;
import someassemblyrequired.SomeAssemblyRequired;
import someassemblyrequired.common.util.Util;

public class ModTags {

    // forge item tags
    public static final TagKey<Item> BREAD_SLICES = itemTag(ForgeVersion.MOD_ID, "bread_slices");
    public static final TagKey<Item> BREAD_SLICES_WHEAT = itemTag(ForgeVersion.MOD_ID, "bread_slices/wheat");

    // mod item tags
    public static final TagKey<Item> SANDWICH_BREAD = itemTag("sandwich_bread");
    public static final TagKey<Item> BURGER_BUNS = itemTag("burger_buns");

    // mod block tags
    public static final TagKey<Block> SANDWICHING_STATIONS = blockTag("sandwiching_stations");

    private static TagKey<Item> itemTag(String path) {
        return itemTag(SomeAssemblyRequired.MODID, path);
    }

    private static TagKey<Item> itemTag(String modId, String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(modId, path));
    }

    private static TagKey<Block> blockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, Util.id(path));
    }
}
