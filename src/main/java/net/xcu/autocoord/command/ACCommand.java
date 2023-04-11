package net.xcu.autocoord.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.xcu.autocoord.event.MacroHandler;

import java.util.Arrays;

public class ACCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "acsc";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/acsc <x> <z> <f>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 3) {
            throw new CommandException("Invalid arguments. Usage: " + getCommandUsage(sender));
        }
        for (int i = 0; i < MacroHandler.coords.length; i++) {
            MacroHandler.coords[i] = parseDouble(args[i]);
        }

        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ChatComponentText info = new ChatComponentText("Coords set to ");
            ChatComponentText coords = new ChatComponentText(Arrays.toString(args));
            coords.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA));
            info.appendSibling(coords);
            player.addChatMessage(info);
            MacroHandler.onStartAC();
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
