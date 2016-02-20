package unicodefontfixer;

import net.minecraft.client.Minecraft;

public class RenderingText {

	public StackTraceElement[] stackTrace;
	public String string;
	public int x, y, color;
	public int wrapWidth;
	public boolean dropShadow, wrap;

	public RenderingText(String string, int x, int y, int color, boolean dropShadow) {
		this.string = string;
		this.x = x;
		this.y = y;
		this.color = color;
		this.dropShadow = dropShadow;
	}
	
	public RenderingText(String string, int x, int y, int color, int wrapWidth, boolean dropShadow) {
		this(string, x, y, color, dropShadow);
		this.wrapWidth = wrapWidth;
		this.wrap = true;
	}
	
}
