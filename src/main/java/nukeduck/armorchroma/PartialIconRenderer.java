package nukeduck.armorchroma;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import nukeduck.armorchroma.config.ArmorIcon;

/** Class that renders leading and trailing icons */
public abstract class PartialIconRenderer {

    public abstract void drawLeadingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack);

    public abstract void drawTrailingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack);

}
