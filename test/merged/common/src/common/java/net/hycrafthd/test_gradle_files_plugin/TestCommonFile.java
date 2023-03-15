package net.hycrafthd.test_gradle_files_plugin;

import net.minecraft.client.Minecraft;

public class TestCommonFile {
	
	private static final Minecraft minecraft = Minecraft.getInstance();
	
	public static void test() {
		minecraft.getCameraEntity();
		Thread thread = minecraft.gameThread;
		System.out.println(thread);
	}
	
}
