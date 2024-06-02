package nukeduck.armorchroma.config;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IconTable {
    public final Map<String, Integer> materials = new HashMap<>();
    public final Map<String, Integer> items = new HashMap<>();
    public final Map<String, Integer> special = new HashMap<>();

    public void putAll(IconTable other) {
        materials.putAll(other.materials);
        items.putAll(other.items);
        special.putAll(other.special);
    }

    public Integer getIconIndex(ItemStack stack) {
        if (stack == null) return null;

        Integer i = null;
        Item item = stack.getItem();

        if (item instanceof ArmorItem armorItem) {
            Optional<RegistryKey<ArmorMaterial>> materialKey = armorItem.getMaterial().getKey();
            if (materialKey.isPresent()) {
                i = Util.getGlob(materials, materialKey.get().getValue().getPath());
            }
        }
        if (i == null) {
            i = Util.getGlob(items, Registries.ITEM.getId(item).getPath());
        }
        return i;
    }

    public Integer getSpecialIndex(String key) {
        return special.get(key);
    }
}
