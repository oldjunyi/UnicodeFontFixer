package unicodefontfixer.mods;

import java.util.HashMap;

import net.minecraft.util.EnumChatFormatting;
import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class Botania implements ModHandler {

	@Override
	public String getModID() {
		return "Botania";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		RenderingAdapter removeBold = new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				text.string = text.string.replace(EnumChatFormatting.BOLD.toString(), "");
				return super.adjust(text);
			}
		};
		adapters[0].put("vazkii.botania.client.core.handler.TooltipAdditionDisplayHandler", removeBold);
		adapters[0].put("vazkii.botania.client.core.handler.HUDHandler", removeBold);
		adapters[0].put("vazkii.botania.client.gui.lexicon.GuiLexiconIndex", removeBold);
	}
	
}
