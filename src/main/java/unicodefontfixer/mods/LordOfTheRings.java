package unicodefontfixer.mods;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;

public class LordOfTheRings implements ModHandler {

	@Override
	public String getModID() {
		return "lotr";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("lotr.client.LOTRTickHandlerClient", new RenderingAdapter.NoReturn() {
			@Override
			public Double resize(String text) {
				double ret = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
				return ret > 0.2 && ret < 1.0 ? ret : null;
			}
		});
	}
	
}
