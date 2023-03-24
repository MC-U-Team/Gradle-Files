package net.hycrafthd.test_gradle_files_plugin;

import net.fabricmc.api.ModInitializer;

public class TestMod implements ModInitializer {
	
	public static final String MODID = "test_mod";
	
	@Override
	public void onInitialize() {
		System.out.println("Load test mod for gradle files plugin");
		
		final net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();
		minecraft.getWindow().getGuiScaledHeight();
		
		TestCommonFile.test();
	}
	
}
