package unicodefontfixer.mods;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import unicodefontfixer.RenderingAdapter;
import unicodefontfixer.RenderingText;

public class TravellersGear implements ModHandler {

	@Override
	public String getModID() {
		return "TravellersGear";
	}

	@Override
	public void registerAdapters(HashMap<String, RenderingAdapter>[] adapters) {
		adapters[0].put("travellersgear.client.gui.GuiTravellersInv", new RenderingAdapter() {
			@Override
			public Double adjust(RenderingText text) {
				Double ratio = super.adjust(text);
				GL11.glTranslated(-0.5, 0.5, 0);
				return ratio;
			}
		});
	}
	
}
