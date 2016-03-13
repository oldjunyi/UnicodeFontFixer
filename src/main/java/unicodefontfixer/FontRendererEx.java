package unicodefontfixer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import unicodefontfixer.mods.*;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class FontRendererEx extends FontRenderer {
	
	public static int policy = 1;
	public static HashMap<String, Double> textScale = new HashMap();
	public static HashMap<String, RenderingAdapter>[] adapters = new HashMap[]{new HashMap(), new HashMap()};
	public static RenderingAdapter debugger = new RenderingAdapter.Debugger();
	public static boolean initialized = false;
	public static boolean checkingStackDepth = false;
	
	public FontRenderer proxy;
	public int stackDepth;
	
	public FontRendererEx(GameSettings gs, ResourceLocation rl, TextureManager tm, boolean flag) {
		super(gs, rl, tm, flag);
	}
	
	public int drawStringChecked(RenderingText text) {
		if (text.string == null) return 0;
		if (checkingStackDepth) {
			final CallerClassTracer cct = new CallerClassTracer();
			Class[] cs = cct.getCallerStack();
			stackDepth = 0;
			for (int i = 3; i < cs.length; i++) {
				if (cs[i].getName().contains("unicodefontfixer.UnicodeFontFixer")) {
					stackDepth = i - 3;
					break;
				}
			}
			return 0;
		}
		Double ratio = null;
		boolean pop = false;
		proxy.FONT_HEIGHT = this.FONT_HEIGHT;
		double rawScale = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
		if (rawScale >= 0.25 && rawScale < 1.0 && ((policy == 2 && this.getUnicodeFlag()) || policy == 1)) {
			GL11.glPushMatrix();
			pop = true;
			int idx = stackDepth + 1;
			text.stackTrace = new Throwable().getStackTrace();
			RenderingAdapter    worker = adapters[0].get(text.stackTrace[++idx].getClassName());
			if (worker == null) worker = adapters[1].get(text.stackTrace[++idx].getClassName());
			if (worker == null) worker = debugger;
			ratio = worker.adjust(text);
			if (ratio != null) {
				textScale.put(text.string.trim() + "@" + text.stackTrace[idx].getClassName(), ratio);
				text.wrapWidth = (int)Math.round(text.wrapWidth * ratio);
			}
		}
		int at = 0;
		if (text.wrap) {
			proxy.drawSplitString(text.string, text.x, text.y, text.wrapWidth, text.color);
		} else {
			at = proxy.drawString(text.string, text.x, text.y, text.color, text.dropShadow);
		}
		if (pop) GL11.glPopMatrix();
		if (ratio != null) return (int)Math.round(text.x + (at - text.x) / ratio);
		return at;
	}
	
	public double getScaleRatio(String string) {
		Double ratio = null;
		if (string != null && ((policy == 2 && this.getUnicodeFlag()) || policy == 1)) {
			final CallerClassTracer cct = new CallerClassTracer();
			String caller = cct.getCaller(stackDepth + 3).getName(); 
			RenderingAdapter worker = adapters[0].get(caller);
			if (worker != null) ratio = worker.resize(string);
			if (ratio  == null) ratio = textScale.get(string.trim() + "@" + caller);
		}
		proxy.FONT_HEIGHT = this.FONT_HEIGHT;
		return ratio == null ? 1.0 : ratio;
	}
	
	@Override
	public void drawSplitString(String string, int x, int y, int wrapWidth, int color) {
		drawStringChecked(new RenderingText(string, x, y, color, wrapWidth, true));
	}
	
	@Override
	public int drawString(String string, int x, int y, int color) {
		return drawStringChecked(new RenderingText(string, x, y, color, false));
    }
	
	@Override
	public int drawStringWithShadow(String string, int x, int y, int color){
		return drawStringChecked(new RenderingText(string, x, y, color, true));
    }
	
	@Override
	public int drawString(String string, int x, int y, int color, boolean dropShadow) {
		return drawStringChecked(new RenderingText(string, x, y, color, dropShadow));
    }
	
	@Override
	public boolean getBidiFlag() {
        return proxy.getBidiFlag();
    }
	
	@Override
	public int getCharWidth(char ch) {
        return proxy.getCharWidth(ch);
    }
	
	@Override
	public int getStringWidth(String string) {
		double ratio = getScaleRatio(string);
		return (int)Math.round(proxy.getStringWidth(string) / ratio);
	}
	
	@Override
    public boolean getUnicodeFlag() {
		return proxy.getUnicodeFlag();
    }
	
	@Override
    public List listFormattedStringToWidth(String string, int width) {
		double ratio = getScaleRatio(string);
		return proxy.listFormattedStringToWidth(string, (int)Math.round(width * ratio));
    }
	
	@Override
	public void onResourceManagerReload(IResourceManager rm) {
		proxy.onResourceManagerReload(rm);
    }
	
	@Override
	public void setBidiFlag(boolean flag) {
        proxy.setBidiFlag(flag);
    }
	
	@Override
    public void setUnicodeFlag(boolean flag) {
		Minecraft mc = Minecraft.getMinecraft();
		proxy.setUnicodeFlag(flag || mc.gameSettings.forceUnicodeFont);
    }
	
	@Override
	public int splitStringWidth(String string, int width) {
		double ratio = getScaleRatio(string);
		return proxy.splitStringWidth(string, (int)Math.round(width * ratio));
	}
	
	@Override
	public String trimStringToWidth(String string, int width) {
		double ratio = getScaleRatio(string);
		return proxy.trimStringToWidth(string, (int)Math.round(width * ratio), false);
	}
	
	@Override
	public String trimStringToWidth(String string, int width, boolean flag) {
		double ratio = getScaleRatio(string);
		return proxy.trimStringToWidth(string, (int)Math.round(width * ratio), flag);
	}

}
