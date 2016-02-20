package unicodefontfixer.mods;

import java.util.HashMap;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class Forestry implements ModHandler {

	@Override
	public String getModID() {
		return "Forestry";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		RenderingAdapter adapterEscritoire = new RenderingAdapter() {
			boolean flag = false;
			@Override
			public Double adjust(RenderingText text) {
				flag = !flag;
				if (flag) text.string = "";
				text.wrapWidth += 30;
				return super.adjust(text);
			}
		};
		adapters[0].put("forestry.core.gui.GuiForestry", new RenderingAdapter());	// FR 3
		adapters[0].put("forestry.core.gui.GuiAlyzer", new RenderingAdapter()); 	// FR 3
		adapters[0].put("forestry.core.gui.GuiEscritoire", adapterEscritoire); // FR 3
		adapters[1].put("forestry.core.gui.GuiEscritoire", adapterEscritoire); // FR 4
	}
	
}
