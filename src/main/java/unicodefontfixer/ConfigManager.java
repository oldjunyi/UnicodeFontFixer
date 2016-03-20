package unicodefontfixer;

import java.io.File;
import java.util.ArrayList;

import unicodefontfixer.mods.*;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigManager {

	public Configuration file;
	public Property fixDerpyFont;
	public Property blacklist;
	public ArrayList<ModHandler> mods = new ArrayList();
	
	public ConfigManager(File configDir) {
		file = new Configuration(new File(configDir, "UnicodeFontFixer.cfg"));
		mods.add(new Thaumcraft());
		mods.add(new Forestry());
		mods.add(new StevesFactoryManager());
		mods.add(new HardcoreQuestingMode());
		mods.add(new Automagy());
		mods.add(new ElectricalAge());
		mods.add(new TravellersGear());
		mods.add(new ModularPowersuits());
		mods.add(new MineTradingCards());
		mods.add(new TinkersConstruct());
		mods.add(new BiblioCraft());
		mods.add(new AppliedEnergistics());
		mods.add(new ThaumicHorizons());
		mods.add(new BetterRecords());
		mods.add(new LordOfTheRings());
		mods.add(new ArchitectureCraft());
		mods.add(new PneumaticCraft());
		mods.add(new Botania());
		mods.add(new Mekanism());
	}
	
	@SubscribeEvent
	public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(UnicodeFontFixer.MODID)) update();
	}
	
	public void reload() {
		file.load();
		blacklist = file.get("general", "blacklist", new String[] {"Example Mod", "ExampleCraft|Factory"});
		blacklist.comment = StatCollector.translateToLocal("config.unicodefontfixer.blacklist");
		fixDerpyFont = file.get("general", "fixDerpyFont", "always");
		fixDerpyFont.comment = StatCollector.translateToLocal("config.unicodefontfixer.fixDerpyFont");
		fixDerpyFont.comment += "\n  disabled: " + StatCollector.translateToLocal("config.unicodefontfixer.fixDerpyFont.disabled");
		fixDerpyFont.comment += "\n  always: " + StatCollector.translateToLocal("config.unicodefontfixer.fixDerpyFont.always");
		fixDerpyFont.comment += "\n  moderate: " + StatCollector.translateToLocal("config.unicodefontfixer.fixDerpyFont.moderate");
		fixDerpyFont.comment += "\n" + StatCollector.translateToLocal("config.unicodefontfixer.fixDerpyFont.hint");
		update();
	}
	
	public void update() {
		String[] blackNames = blacklist.getStringList();
		for (int i = 0; i < FontRendererEx.adapters.length; i++) FontRendererEx.adapters[i].clear();
		for (int i = 0; i < mods.size(); i++) {
			ModHandler mh = mods.get(i);
			boolean exclude = !Loader.isModLoaded(mh.getModID());
			for (int j = 0; j < blackNames.length && !exclude; j++) {
				String lhs = mh.getModID().replaceAll("\\s+","");
				String rhs = blackNames[j].replaceAll("\\s+","");
				if (lhs.equalsIgnoreCase(rhs)) exclude = true;
				lhs = mh.getClass().getSimpleName().replaceAll("\\s+",""); 
				if (lhs.equalsIgnoreCase(rhs)) exclude = true;
			}
			if (!exclude) mh.registerAdapters(FontRendererEx.adapters);
		}
		if (fixDerpyFont.getString().toLowerCase().equals("moderate")) {
			fixDerpyFont.set("moderate");
			FontRendererEx.policy = 2;
		} else if (fixDerpyFont.getString().toLowerCase().equals("disabled")) {
			fixDerpyFont.set("disabled");
			FontRendererEx.policy = 0;
		} else {
			fixDerpyFont.set("always");
			FontRendererEx.policy = 1;
		}
		file.save();
	}
	
}
