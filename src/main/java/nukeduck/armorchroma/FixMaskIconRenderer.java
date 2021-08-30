package nukeduck.armorchroma;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import nukeduck.armorchroma.config.ArmorIcon;

import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;

/** Renders masked icons by tweaking the UV region (?) and drawing a line
 * (therefore allowing any icon shape but preventing changing the mask) */
public class FixMaskIconRenderer implements PartialIconRenderer {

    @Override
    public void drawLeadingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack) {
        icon.draw(matrices, ArmorChroma.GUI, x + 4, y, 4, 5);
        drawCenterLine(matrices, x, y); // Both half-icons draw the vertical
        //      line in case one is taller than the other
    }

    @Override
    public void drawTrailingIcon(MatrixStack matrices, int x, int y, ArmorIcon icon, ItemStack stack) {
        icon.draw(matrices, ArmorChroma.GUI, x, y, 0, 5);
        drawCenterLine(matrices, x, y);
    }

    private void drawCenterLine(MatrixStack matrixStack, int x, int y) {
        RenderSystem.depthFunc(GL_EQUAL);
        DrawableHelper.fill(matrixStack, x + 4, y, x + 5, y + ArmorIcon.ICON_SIZE, 0xff000000);
        RenderSystem.enableBlend();
        RenderSystem.depthFunc(GL_LEQUAL);
    }

}
