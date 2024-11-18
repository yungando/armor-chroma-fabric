package nukeduck.armorchroma;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;
import net.minecraft.util.Util;

import java.util.function.Function;

public class ArmorChromaRenderLayers {

    private static final RenderPhase.Transparency MASKED_ICON_TRANSPARENCY = new RenderPhase.Transparency(
            "armor_chroma_masked_icon_transparency",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SrcFactor.DST_COLOR, GlStateManager.DstFactor.ZERO);
            },
            () -> {
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            }
    );

    private static final Function<Identifier, RenderLayer> MASKED_ICON = Util.memoize(
            texture -> RenderLayer.of(
                    "armor_chroma_masked_icon",
                    VertexFormats.POSITION_TEXTURE_COLOR,
                    VertexFormat.DrawMode.QUADS,
                    RenderLayer.CUTOUT_BUFFER_SIZE,
                    RenderLayer.MultiPhaseParameters.builder()
                            .texture(new RenderPhase.Texture(texture, TriState.FALSE, false))
                            .program(RenderPhase.POSITION_TEXTURE_COLOR_PROGRAM)
                            .transparency(MASKED_ICON_TRANSPARENCY)
                            .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                            .build(false)
            )
    );

    public static RenderLayer getMaskedIcon(Identifier texture) {
        return MASKED_ICON.apply(texture);
    }
}
