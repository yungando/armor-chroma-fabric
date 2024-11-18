package nukeduck.armorchroma;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentModels;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MaterialHelper {

    private static final Identifier TURTLE_MATERIAL = Identifier.of("turtle");

    @Nullable
    public static Identifier getMaterial(Item item) {
        EquippableComponent equippableComponent = item.getComponents().get(DataComponentTypes.EQUIPPABLE);

        if (equippableComponent != null) {
            // Armor materials don't have IDs anymore since 1.21.2, and models
            // are the closest thing to materials (with some exceptions such
            // as turtle scutes)
            Optional<Identifier> model = equippableComponent.model();

            if (model.isPresent()) {
                Identifier id = model.get();

                if (id.equals(EquipmentModels.TURTLE_SCUTE)) {
                    return TURTLE_MATERIAL;
                } else {
                    return id;
                }
            }
        }

        return null;
    }

}
