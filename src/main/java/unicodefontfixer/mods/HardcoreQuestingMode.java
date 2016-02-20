package unicodefontfixer.mods;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import unicodefontfixer.CallerClassTracer;
import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;
import unicodefontfixer.UnicodeFontFixer;

public class HardcoreQuestingMode implements ModHandler {

	@Override
	public String getModID() {
		return "HardcoreQuesting";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("hardcorequesting.client.interfaces.GuiBase", new RenderingAdapter() {
			@Override
			public Double resize(String text) {
				final CallerClassTracer cct = new CallerClassTracer();
				String name = cct.getCaller(UnicodeFontFixer.instance.fontRendererStandard.stackDepth + 5).getName();
				if (name.startsWith("hardcorequesting.quests.Quest")) return 0.7;
				return null;
			}
		});
	}
	
}
