package unicodefontfixer;

import java.util.Map;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class GuiLanguageEx extends GuiLanguage {
	
	public GuiButton btnFixFont;
	
	public GuiLanguageEx(GuiLanguage gui) {
		super(
			(GuiScreen)ObfuscationReflectionHelper.getPrivateValue(GuiLanguage.class, gui, "field_146453_a"),
			(GameSettings)ObfuscationReflectionHelper.getPrivateValue(GuiLanguage.class, gui, "field_146451_g"),
			(LanguageManager)ObfuscationReflectionHelper.getPrivateValue(GuiLanguage.class, gui, "field_146454_h")
		);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		GuiOptionButton btnUnicode = (GuiOptionButton)this.buttonList.get(0);
		GuiOptionButton btnDone = (GuiOptionButton)this.buttonList.get(1);
		btnFixFont = new GuiButton(93, this.width / 2 + 2, this.height - 52, 150, 20, "");
		btnDone.yPosition = this.height - 28;
		btnDone.xPosition = this.width / 2 - 75;
		btnUnicode.yPosition = this.height - 52;
		btnUnicode.xPosition = this.width / 2 - 150 - 2;
		this.buttonList.add(btnFixFont);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GuiSlot langlist = ObfuscationReflectionHelper.getPrivateValue(GuiLanguage.class, this, "field_146450_f");
		langlist.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), this.width / 2, 16, 16777215);
		updateButtonText();
		for (int i = 0; i < this.buttonList.size(); i++)
			((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
		for (int i = 0; i < this.labelList.size(); i++)
			((GuiLabel)this.labelList.get(i)).func_146159_a(this.mc, mouseX, mouseY);
	}
	
	@Override
	protected void actionPerformed(GuiButton btn) {
		GameSettings gs = ObfuscationReflectionHelper.getPrivateValue(GuiLanguage.class, this, "field_146451_g");
		if (btn.enabled && btn.id == 93) {
			ConfigManager cm = UnicodeFontFixer.instance.configManager;
			if (cm.fixDerpyFont.getString().equals("always")) {
				cm.fixDerpyFont.set("moderate");
				FontRendererEx.policy = 2;
			} else if (cm.fixDerpyFont.getString().equals("moderate")) {
				cm.fixDerpyFont.set("disabled");
				FontRendererEx.policy = 0;
			} else {
				cm.fixDerpyFont.set("always");
				FontRendererEx.policy = 1;
			}
			cm.update();
		} else {
			super.actionPerformed(btn);
		}
	}
	
	private void updateButtonText() {
		btnFixFont.displayString =
				StatCollector.translateToLocal("options.unicodefontfixer.fixDerpyFont") + ": " +
				StatCollector.translateToLocal("options.unicodefontfixer.fixDerpyFont." + UnicodeFontFixer.instance.configManager.fixDerpyFont.getString());
	}

}
