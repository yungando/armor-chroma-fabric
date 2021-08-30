package nukeduck.armorchroma.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import nukeduck.armorchroma.config.ArmorChromaConfig.ArmorChromaAutoConfig;

/** "Custom" config serializer used to detect config changes */
public class ArmorChromaConfigSerializer<T extends ArmorChromaAutoConfig> extends GsonConfigSerializer<T> {

    public ArmorChromaConfigSerializer(Config definition, Class<T> configClass) {
        super(definition, configClass);
    }

    // Also called when the game is launched
    @Override
    public void serialize(T config) throws SerializationException {
        super.serialize(config);
        config.onChanged();
    }
}
