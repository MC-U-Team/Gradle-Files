package net.hycrafthd.gradle_files_test_mod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "testmod")
public class TestForgeMod {

	@EventHandler
	public void init(FMLInitializationEvent event) {
		final Minecraft minecraft = Minecraft.getMinecraft();
		minecraft.displayGuiScreen(null);
		final Thread thread = minecraft.thread;
		System.out.println(thread);
	}

}
