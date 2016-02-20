package unicodefontfixer.mods;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class ModularPowersuits implements ModHandler {

	@Override
	public String getModID() {
		return "powersuits";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("net.machinemuse.utils.render.MuseRenderer", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				FloatBuffer fb = BufferUtils.createFloatBuffer(16);
				GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, fb);
				GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2, Math.round((text.y + 10) * (ratio - 1) * 2) / 2, 0);
				text.string = text.string.replace("At Full Charge", "(Max)");
				text.string = text.string.replace(" Consuption", "");
				text.string = text.string.replace(" Consumption", "");
				text.string = text.string.replace(" consumption", "");
				text.string = text.string.replace(" Compensation", " Add");
				text.string = text.string.replace(" Boost", " Add");
				text.string = text.string.replace(" Explosiveness", " Explosion");
				return ratio;
			}
		});
	}
	
}
