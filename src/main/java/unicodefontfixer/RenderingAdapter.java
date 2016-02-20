package unicodefontfixer;

import java.nio.FloatBuffer;
import java.util.HashSet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class RenderingAdapter {
	
	public Double adjust(RenderingText text) {
		double ratio = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
		GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
		GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2, Math.round((text.y + 6) * (ratio - 1) * 2) / 2, 0);
		return ratio;
	}
	
	public Double resize(String text) {
		return null;
	}
	
	public static class NoReturn extends RenderingAdapter {
		
		@Override
		public Double adjust(RenderingText text) {
			super.adjust(text);
			return null;
		}
		
	}
	
	public static class Debugger extends RenderingAdapter {
		
		public static HashSet<String> history = new HashSet();
		
		@Override
		public Double adjust(RenderingText text) {
			if (!history.add(text.stackTrace[2].toString())) return null;
			FloatBuffer fb = BufferUtils.createFloatBuffer(16);
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, fb);
			System.out.println("==== " + text.string + " ====");
			System.out.println(" at (" + text.x + ", " + text.y + ")");
			for (int i = 0; i < 4; i++) {
				System.out.println("    |" + fb.get(i * 4 + 0) +
							", " + fb.get(i * 4 + 1) +
							", " + fb.get(i * 4 + 2) +
							", " + fb.get(i * 4 + 3) + "|");
			}
			for (int i = 0; i < text.stackTrace.length; i++)
				System.out.println("stackTrace[" + i + "] = " + text.stackTrace[i].toString());
			return null;
		}
		
	}
	
}
