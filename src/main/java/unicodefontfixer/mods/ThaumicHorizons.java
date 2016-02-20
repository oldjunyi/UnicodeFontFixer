package unicodefontfixer.mods;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class ThaumicHorizons implements ModHandler {

	@Override
	public String getModID() {
		return "ThaumicHorizons";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("com.kentington.thaumichorizons.common.items.lenses.ItemLensOrderEntropy", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				GL11.glTranslatef(1, 0, 0);
				return null;
			}
			@Override
			public Double resize(String text) {
				int len = text.length();
				boolean ok = true;
				for (int i = 0; ok && i < len; i++)
					if (!Character.isDigit(text.charAt(i))) ok = false;
				return ok ? 0.5 : 1.0;
			}
		});
		adapters[0].put("com.kentington.thaumichorizons.client.gui.GuiFingers", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				GL11.glTranslated(-11, 1, 0);
				text.string = text.string.replace("Insufficient", "No enough");
				return ratio;
			}
		});
	}
	
}
