package unicodefontfixer.mods;

import java.nio.FloatBuffer;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Strings;

import unicodefontfixer.CallerClassTracer;
import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;
import unicodefontfixer.UnicodeFontFixer;

public class Thaumcraft implements ModHandler {

	@Override
	public String getModID() {
		return "Thaumcraft";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("thaumcraft.client.gui.GuiArcaneWorkbench", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				GL11.glTranslated(-11, 1, 0);
				text.string = text.string.replace("Insufficient", "No enough");
				return ratio;
			}
		});
		adapters[0].put("thaumcraft.client.lib.UtilsFX", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				boolean inGUI = (GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX) == 0.5);
				if (text.color == 0 && inGUI) {
					text.string = "";
					return null;
				}
				double ratio = 0.5;
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2 + 1) / 2, Math.round((text.y + 6) * (ratio - 1) * 2) / 2, 0);
				if (inGUI) {
					FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
					for (int i = -1; i <= 1; i++)
					for (int j = -1; j <= 1; j++) {
						if ((i == 0 || j == 0) && (i != 0 || j != 0)) {
							GL11.glTranslated(+i * 0.5, +j * 0.5, 0);
							fr.drawString(text.string, text.x, text.y, 0);
							GL11.glTranslated(-i * 0.5, -j * 0.5, 0);
						}
					}
				}
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
		adapters[0].put("thaumcraft.client.renderers.item.ItemThaumometerRenderer", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = 0.875;
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2, Math.round((text.y + 6) * (ratio - 1) * 2) / 2, 0);
				return null;
			}
			@Override
			public Double resize(String text) {
				return 0.875;
			}
		});
		adapters[1].put("thaumcraft.client.lib.REHNotifyHandler", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				GL11.glTranslated(2, 0.5, 0);
				return ratio;
			}
		});
		adapters[0].put("thaumcraft.client.gui.GuiResearchBrowser", new RenderingAdapter());
		adapters[0].put("thaumcraft.client.gui.GuiGolem", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				if (text.wrap) {
					GL11.glTranslated(-97, 5, 0);
				} else {
					if (text.string.equals("Not sneaking")) text.string = "Stand";
				}
				return ratio;
			}
		});
		adapters[0].put("thaumcraft.client.gui.GuiTravelingTrunk", new RenderingAdapter());
		adapters[0].put("thaumcraft.client.gui.GuiArcaneBore", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2 - 5, Math.round((text.y + 6) * (ratio - 1)) / 2 - 3, 0);
				return ratio;
			}
		});
		adapters[0].put("thaumcraft.client.gui.GuiFocalManipulator", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2, Math.round(text.y * (ratio - 1) * 1.6) / 2 - 5.5, 0);
				if (text.string.equals("Perditio")) text.string = "Perdi";
				return ratio;
			}
		});
		adapters[1].put("thaumcraft.client.lib.ClientTickEventsFML", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = 0.5;
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2, Math.round((text.y + 6) * (ratio - 1) * 2) / 2, 0);
				return ratio;
			}
		});
	}
	
}
