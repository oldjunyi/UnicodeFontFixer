package unicodefontfixer.mods;

import java.util.HashMap;

import unicodefontfixer.RenderingAdapter;

public class PneumaticCraft implements ModHandler {

	@Override
	public String getModID() {
		return "PneumaticCraft";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("pneumaticCraft.client.gui.GuiThermopneumaticProcessingPlant", new RenderingAdapter());
		adapters[0].put("pneumaticCraft.common.progwidgets.ProgWidget", new RenderingAdapter());
	}
	
}
