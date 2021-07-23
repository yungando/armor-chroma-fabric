package nukeduck.armorchroma;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import nukeduck.armorchroma.config.ArmorIcon;
import nukeduck.armorchroma.config.Util;

import static org.lwjgl.opengl.GL11.GL_DST_COLOR;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ZERO;

/** Renders masked icons using textures (therefore only allowing vanilla-
 * shaped icons (or at least icons shaped like the mask)) */
public class TextureMaskIconRenderer extends PartialIconRenderer {

    @Override
    public void drawLeadingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack) {
        drawMaskedIcon(matrices, x, y, icon, "leadingMask", stack);
    }

    @Override
    public void drawTrailingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack) {
        drawMaskedIcon(matrices, x, y, icon, "trailingMask", stack);
    }

    private void drawMaskedIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, String maskKey, ItemStack stack) {
        ArmorIcon mask = ArmorChroma.ICON_DATA.getSpecial(Util.getModid(stack), maskKey);
        mask.draw(matrices, ArmorChroma.GUI, x, y);
        RenderSystem.depthFunc(GL_EQUAL);
        RenderSystem.blendFunc(GL_DST_COLOR, GL_ZERO);
        icon.draw(matrices, ArmorChroma.GUI, x, y);
        RenderSystem.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.depthFunc(GL_LEQUAL);
    }

}
