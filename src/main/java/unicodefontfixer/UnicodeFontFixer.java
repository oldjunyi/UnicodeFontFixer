package unicodefontfixer;

import java.lang.reflect.Field;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = UnicodeFontFixer.MODID, useMetadata = true)
public class UnicodeFontFixer {
	
	public static final String MODID = "UnicodeFontFixer";
	
	@Instance(MODID)
	public static UnicodeFontFixer instance;
	
	@SideOnly(Side.CLIENT)
	public FontRendererEx fontRendererStandard, fontRendererGalactic;
	
	@SideOnly(Side.CLIENT)
	public ConfigManager configManager;
	
	@EventHandler
	@SideOnly(Side.CLIENT)
	public void preInit(FMLPreInitializationEvent event) {
		configManager = new ConfigManager(event.getModConfigurationDirectory());
	}
	
	@EventHandler
	@SideOnly(Side.CLIENT)
	public void initialize(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(configManager);
		configManager.reload();
		Minecraft mc = Minecraft.getMinecraft();
		fontRendererStandard = new FontRendererEx(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
		fontRendererGalactic = new FontRendererEx(mc.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), mc.renderEngine, false);
		mc.fontRenderer = setDelegate(mc.fontRenderer, fontRendererStandard);
		mc.standardGalacticFontRenderer = setDelegate(mc.standardGalacticFontRenderer, fontRendererGalactic);
	}
	
	@SideOnly(Side.CLIENT)
	public FontRenderer setDelegate(FontRenderer inner, FontRendererEx outer) {
		outer.proxy = inner;
		outer.FONT_HEIGHT = inner.FONT_HEIGHT;
		outer.fontRandom  = inner.fontRandom;
		return outer;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiOpen(GuiOpenEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		FontRendererEx.checkingStackDepth = true;
		mc.fontRenderer.drawString("", 0, 0, 0);
		mc.standardGalacticFontRenderer.drawString("", 0, 0, 0);
		FontRendererEx.checkingStackDepth = false;
		if (event.gui instanceof GuiLanguage)
			event.gui = new GuiLanguageEx((GuiLanguage)event.gui);
	}
	
}
