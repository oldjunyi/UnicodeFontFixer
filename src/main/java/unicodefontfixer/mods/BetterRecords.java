package unicodefontfixer.mods;

import java.util.HashMap;

import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;
import unicodefontfixer.UnicodeFontFixer;

public class BetterRecords implements ModHandler {

	@Override
	public String getModID() {
		return "betterrecords";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		
		adapters[0].put("com.codingforcookies.betterrecords.src.gui.GuiRecordEtcher", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				super.adjust(text);
				if (text.color == 2267426 || text.color == 10035746) {
					GL11.glTranslated(2, 0, 0);
				} else {
					FontRenderer fr = UnicodeFontFixer.instance.fontRendererStandard.proxy;
					while (fr.getStringWidth(text.string) > 60) text.string = text.string.substring(0, text.string.length() - 1);
				}
				return null;
			}
			@Override
			public Double resize(String text) {
				return (double)(GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX));
			}
		});
		adapters[0].put("com.codingforcookies.betterrecords.src.gui.GuiFrequencyTuner", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				super.adjust(text);
				if (text.color == 2267426 || text.color == 10035746) GL11.glTranslated(2, 0, 0);
				return null;
			}
			@Override
			public Double resize(String text) {
				return (double)(GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX));
			}
		});
		adapters[0].put("com.codingforcookies.betterrecords.src.gui.GuiBetterDisclaimer", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				super.adjust(text);
				GL11.glTranslated(-40, 0, 0);
				return null;
			}
			@Override
			public Double resize(String text) {
				return (double)(GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX));
			}
		});
		adapters[0].put("com.codingforcookies.betterrecords.src.client.BetterEventHandler", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.75F);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(225.0F, text.y > 5 ? (text.y +  3) * 0.5f : 0.0f);
				GL11.glVertex2f(180.0F, text.y > 5 ? (text.y +  3) * 0.5f : 0.0f);
				GL11.glVertex2f(180.0F, text.y > 5 ? (text.y + 13) * 0.5f : 9.0f);
				GL11.glVertex2f(225.0F, text.y > 5 ? (text.y + 13) * 0.5f : 9.0f);
				GL11.glEnd();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(  0.0F, text.y > 5 ? (text.y +  3) * 0.5f : 0.0f);
				GL11.glVertex2f(-45.0F, text.y > 5 ? (text.y +  3) * 0.5f : 0.0f);
				GL11.glVertex2f(-45.0F, text.y > 5 ? (text.y + 13) * 0.5f : 9.0f);
				GL11.glVertex2f(  0.0F, text.y > 5 ? (text.y + 13) * 0.5f : 9.0f);
				GL11.glEnd();
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2, Math.round((text.y + 6) * (ratio - 1) * 2) / 2, 0);
				GL11.glTranslated(0, (text.y - 5) * 0.2 - 1, 0);
				return null;
			}
			@Override
			public Double resize(String text) {
				return (double)(GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX));
			}
		});
	}
	
}
