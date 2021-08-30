package nukeduck.armorchroma;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import nukeduck.armorchroma.config.ArmorIcon;

/** Interface with methods for rendering leading and trailing icons */
public interface PartialIconRenderer {

    void drawLeadingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack);

    void drawTrailingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack);

}
