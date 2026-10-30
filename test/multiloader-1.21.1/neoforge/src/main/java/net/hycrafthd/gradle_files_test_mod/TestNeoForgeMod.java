package net.hycrafthd.gradle_files_test_mod;

import net.minecraft.client.Minecraft;
import net.neoforged.fml.common.Mod;

@Mod("gradle_files_test")
public class TestNeoForgeMod {

	public TestNeoForgeMod() {
		final Minecraft minecraft = Minecraft.getInstance();
		minecraft.getWindow().getGuiScaledHeight();

		TestCommonClass.test();
	}

}
