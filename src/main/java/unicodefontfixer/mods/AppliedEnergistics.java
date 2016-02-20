package unicodefontfixer.mods;

import java.nio.FloatBuffer;
import java.util.HashMap;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;
import unicodefontfixer.UnicodeFontFixer;

public class AppliedEnergistics implements ModHandler {
	
	@Override
	public String getModID() {
		return "appliedenergistics2";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("appeng.client.render.AppEngRenderItem", new RenderingAdapter() {
			public Double ratio = 1.0;
			@Override
			public Double adjust(RenderingText text) {
				ratio = super.adjust(text);
				GL11.glTranslatef((ratio < 0.8 ? 2 : 1), 0, 0);
				return null;
			}
			@Override
			public Double resize(String text) {
				return ratio;
			}
		});
		adapters[0].put("appeng.client.gui.implementations.GuiNetworkStatus", new RenderingAdapter());
		RenderingAdapter crafter = new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				if (text.string.startsWith(StatCollector.translateToLocal("gui.appliedenergistics2.FromStorage"))) {
					GL11.glTranslatef(-20, -1, 0);
				} else if (text.string.startsWith(StatCollector.translateToLocal("gui.appliedenergistics2.Missing"))) {
					GL11.glTranslatef(-20, +1, 0);
				} else {
					GL11.glTranslatef(-20, +3, 0);
				}
				text.string = text.string.replace(": ", ":");
				return ratio;
			}
		};
		adapters[0].put("appeng.client.gui.implementations.GuiCraftConfirm", crafter);
		adapters[0].put("appeng.client.gui.implementations.GuiCraftingCPU", crafter);
	}
	
}
