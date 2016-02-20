package unicodefontfixer.mods;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;
import unicodefontfixer.UnicodeFontFixer;

public class BiblioCraft implements ModHandler {

	@Override
	public String getModID() {
		return "BiblioCraft";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[1].put("jds.bibliocraft.gui.GuiClock", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				int len = UnicodeFontFixer.instance.fontRendererStandard.proxy.getStringWidth(text.string);
				GL11.glTranslatef(-0.4f * len, 0, 0);
				return super.adjust(text);
			}
		});
		adapters[0].put("jds.bibliocraft.gui.GuiFancySign", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				text.wrapWidth = text.wrapWidth * 9 / 8;
				return super.adjust(text);
			}
		});
		adapters[0].put("jds.bibliocraft.gui.GuiRedstoneBook", new RenderingAdapter() {
			boolean flag = false;
			@Override
			public Double adjust(RenderingText text) {
				if (flag) text.string = "";
				flag = !flag;
				return super.adjust(text);
			}
		});
		adapters[0].put("jds.bibliocraft.gui.GuiButtonClipboard", new RenderingAdapter());
		adapters[0].put("jds.bibliocraft.gui.GuiStockCatalog", new RenderingAdapter());
		adapters[0].put("jds.bibliocraft.gui.GuiBiblioTextField", new RenderingAdapter());
		adapters[0].put("jds.bibliocraft.gui.GuiRecipeBook", new RenderingAdapter());
	}
	
}
