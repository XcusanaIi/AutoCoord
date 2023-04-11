package net.xcu.autocoord.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.xcu.autocoord.event.MacroHandler;

public class ToggleAutoYawCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ty";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ty";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            boolean bl = MacroHandler.toggleAutoYaw();
            ChatComponentText info;
            if (bl) {
                info = new ChatComponentText("Auto Yaw On");
                info.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN));
            }else {
                info = new ChatComponentText("Auto Yaw Off");
                info.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
            }
            player.addChatMessage(info);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
