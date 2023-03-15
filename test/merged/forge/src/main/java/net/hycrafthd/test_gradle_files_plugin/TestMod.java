package net.hycrafthd.test_gradle_files_plugin;

import net.minecraftforge.fml.common.Mod;

@Mod(TestMod.MODID)
public class TestMod {
	
	public static final String MODID = "test_mod";
	
	public TestMod() {
		System.out.println("Load test mod for gradle files plugin");
		
		final net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();
		minecraft.getWindow().getGuiScaledHeight();
		
		TestCommonFile.test();
	}
	
}
