package net.hycrafthd.gradle_files_test_mod;

import net.minecraft.client.Minecraft;

public class TestCommonClass {

	private static final Minecraft minecraft = Minecraft.getInstance();

	public static void test() {
		minecraft.getCameraEntity();
		final Thread thread = minecraft.gameThread;
		System.out.println(thread);
	}

}
