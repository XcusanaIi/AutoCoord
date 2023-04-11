package net.xcu.autocoord;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.xcu.autocoord.command.ACCommand;
import net.xcu.autocoord.command.ToggleAutoYawCommand;
import net.xcu.autocoord.event.MacroHandler;

@Mod(modid = AutoCoord.MODID, version = AutoCoord.VERSION)
public class AutoCoord {
    public static final String MODID = "autocoord";
    public static final String VERSION = "0.0.1";
    public static final KeyBinding MACRO_AC = new KeyBinding("keyBinding.macro_ac", 0, "category.autocoord");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientRegistry.registerKeyBinding(MACRO_AC);
        ClientCommandHandler.instance.registerCommand(new ACCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleAutoYawCommand());
        MinecraftForge.EVENT_BUS.register(new MacroHandler());
    }
}
