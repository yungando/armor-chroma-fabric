package nukeduck.armorchroma.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import nukeduck.armorchroma.ArmorChroma;
import nukeduck.armorchroma.FixMaskIconRenderer;
import nukeduck.armorchroma.PartialIconRenderer;
import nukeduck.armorchroma.TextureMaskIconRenderer;

public class ArmorChromaConfig {

    // Default config (when Autoconfig is not installed)
    public boolean isEnabled() { return true; }
    public boolean renderGlint() { return true; }
    public boolean renderBackground() { return true; }
    public boolean compressBar() { return false; }
    public boolean useTexturedPartialIconMask() { return false; }

    public void onChanged() {
        ArmorChroma.partialIconRenderer = createPartialIconRenderer();
        System.out.print("onchanged\n");
    }

    private PartialIconRenderer createPartialIconRenderer() {
        return useTexturedPartialIconMask()
                ? new TextureMaskIconRenderer()
                : new FixMaskIconRenderer();
    }

    /** Config class used when AutoConfig is installed */
    @SuppressWarnings("FieldMayBeFinal")
    @Config(name = ArmorChroma.MODID)
    public static class ArmorChromaAutoConfig extends ArmorChromaConfig implements ConfigData {

        private boolean enabled = super.isEnabled();
        @Tooltip private boolean renderGlint = super.renderGlint();
        @Tooltip private boolean renderBackground = super.renderBackground();
        @Tooltip private boolean compressBar = super.compressBar();
        @Tooltip(count = 2) private boolean useTexturedPartialIconMask = super.useTexturedPartialIconMask();

        @Override public boolean isEnabled() { return enabled; }
        @Override public boolean renderGlint() { return renderGlint; }
        @Override public boolean renderBackground() { return renderBackground; }
        @Override public boolean compressBar() { return compressBar; }
        @Override public boolean useTexturedPartialIconMask() { return useTexturedPartialIconMask; }

    }

}
