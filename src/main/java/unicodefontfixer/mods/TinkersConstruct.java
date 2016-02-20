package unicodefontfixer.mods;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import unicodefontfixer.CallerClassTracer;
import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class TinkersConstruct implements ModHandler {

	@Override
	public String getModID() {
		return "TConstruct";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("tconstruct.client.AmmoItemRenderer", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				super.adjust(text);
				GL11.glTranslated(0, -1, 0);
				return null;
			}
			@Override
			public Double resize(String text) {
				return (double)(GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX));
			}
		});
	}
	
}
