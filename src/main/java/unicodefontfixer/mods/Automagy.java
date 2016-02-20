package unicodefontfixer.mods;

import java.util.HashMap;

import unicodefontfixer.RenderingAdapter;

public class Automagy implements ModHandler {

	@Override
	public String getModID() {
		return "Automagy";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[1].put("tuhljin.automagy.gui.ModGuiContainer", new RenderingAdapter());
		adapters[1].put("tuhljin.automagy.gui.GuiButtonScaledText", new RenderingAdapter());
	}
	
}
