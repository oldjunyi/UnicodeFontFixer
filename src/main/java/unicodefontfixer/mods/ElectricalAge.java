package unicodefontfixer.mods;

import java.util.HashMap;

import unicodefontfixer.RenderingAdapter;

public class ElectricalAge implements ModHandler {

	@Override
	public String getModID() {
		return "Eln";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("mods.eln.sixnode.tutorialsign.TutorialSignOverlay", new RenderingAdapter());
	}
	
}
