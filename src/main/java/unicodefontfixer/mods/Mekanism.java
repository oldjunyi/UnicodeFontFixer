package unicodefontfixer.mods;

import java.util.HashMap;

import unicodefontfixer.RenderingAdapter;

public class Mekanism implements ModHandler {

	@Override
	public String getModID() {
		return "Mekanism";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("mekanism.client.gui.GuiSeismicReader", new RenderingAdapter());
		adapters[0].put("mekanism.client.gui.GuiUpgradeManagement", new RenderingAdapter());
	}
	
}
