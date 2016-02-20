package unicodefontfixer.mods;

import java.util.HashMap;

import unicodefontfixer.RenderingAdapter;

public interface ModHandler {

	public String getModID();
	
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters);
	
}
