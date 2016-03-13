package unicodefontfixer.mods;

import java.util.HashMap;

import unicodefontfixer.RenderingAdapter;

public class ArchitectureCraft implements ModHandler {

	@Override
	public String getModID() {
		return "ArchitectureCraft";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("gcewing.architecture.BaseGui$Screen.drawString", new RenderingAdapter());
	}
	
}
