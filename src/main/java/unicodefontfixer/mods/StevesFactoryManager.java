package unicodefontfixer.mods;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class StevesFactoryManager implements ModHandler {

	@Override
	public String getModID() {
		return "StevesFactoryManager";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		RenderingAdapter worker = new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				GL11.glTranslated(text.x * (ratio - 1) + 0.8, (text.y + 6) * (ratio - 1), 0);
				FloatBuffer fb = BufferUtils.createFloatBuffer(16);
				GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, fb);
				double dx = Math.round(fb.get(12) * 2) / 2 - fb.get(12);
				double dy = Math.round(fb.get(13) * 2) / 2 - fb.get(13);
				GL11.glTranslated(dx, dy, 0);
				return ratio;
			}
		};
		adapters[0].put("vswe.stevesfactory.interfaces.GuiBase", worker);
		adapters[1].put("vswe.stevesfactory.interfaces.GuiBase", worker);
	}
	
}
