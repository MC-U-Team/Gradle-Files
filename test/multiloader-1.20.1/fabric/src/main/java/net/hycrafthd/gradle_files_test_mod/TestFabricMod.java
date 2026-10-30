package net.hycrafthd.gradle_files_test_mod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;

public class TestFabricMod implements ModInitializer {

	@Override
	public void onInitialize() {
		final Minecraft minecraft = Minecraft.getInstance();
		minecraft.getWindow().getGuiScaledHeight();

		TestCommonClass.test();
	}

}
