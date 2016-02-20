package unicodefontfixer.mods;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class MineTradingCards implements ModHandler {

	@Override
	public String getModID() {
		return "is_mtc";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("com.is.cards.Card_UI", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				double ratio = GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX);
				GL11.glScaled(1 / ratio, 1 / ratio, 1.0);
				
				Minecraft mc = Minecraft.getMinecraft();
				GuiScreen screen = mc.currentScreen;
				final ResourceLocation background = new ResourceLocation("is_mtc", "textures/gui/UI_Card.png");
				final Rectangle bigLabelBar = new Rectangle(0, 154, 154, 42);
				final Rectangle body = new Rectangle(0, 0, 134, 134);
				int cutX = 10, midX = 32, labelWidth = (bigLabelBar.getWidth() - cutX) * 2 + midX;
				int posX = (screen.width - labelWidth) / 2;
				int posY = (screen.height - body.getHeight()) / 2;
				text.x = (int)Math.round((posX + 5) / ratio);
				text.wrapWidth = (int)Math.round((labelWidth - 18) / ratio);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(background);
				screen.drawTexturedModalRect(posX, posY - bigLabelBar.getHeight(), bigLabelBar.getX(), bigLabelBar.getY(), bigLabelBar.getWidth() - cutX, bigLabelBar.getHeight());
				screen.drawTexturedModalRect(posX + bigLabelBar.getWidth() - cutX + midX, posY - bigLabelBar.getHeight(), bigLabelBar.getX() + cutX, bigLabelBar.getY(), bigLabelBar.getWidth() - cutX, bigLabelBar.getHeight());
				
		        GL11.glTranslated(Math.round(text.x * (ratio - 1) * 2) / 2, Math.round((text.y + 6) * (ratio - 1) * 2) / 2 + 1, 0);
				return ratio;
			}
			
		});
		adapters[0].put("com.is.binder.Binder_UI", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				GL11.glTranslated(0, -1, 0);
				return ratio;
			}
		});
	}
	
}
