package io.github.hsyyid.commandportals.cmdexecutors;

import io.github.hsyyid.commandportals.CommandPortals;
import io.github.hsyyid.commandportals.utils.SetPortal;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class ToggleSetPortalExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> command = ctx.<String> getOne("command");

		if (src instanceof Player)
		{
			Player player = (Player) src;

			SetPortal setPortalFound = null;

			for (SetPortal setPortal : CommandPortals.setPortalsList)
			{
				if (setPortal.getPlayerUUID().equals(player.getUniqueId()))
				{
					setPortalFound = setPortal;
					break;
				}
			}

			if (setPortalFound != null)
			{
				player.sendMessage(Text.of(TextColors.BLUE, "[CommandPortals]: ", TextColors.GOLD, "Un-toggled the setting of portals."));
				CommandPortals.setPortalsList.remove(setPortalFound);
			}
			else if (command.isPresent())
			{
				SetPortal setPortal = new SetPortal(player.getUniqueId(), command.get());
				CommandPortals.setPortalsList.add(setPortal);
				player.sendMessage(Text.of(TextColors.BLUE, "[CommandPortals]: ", TextColors.GOLD, "Toggled the setting of portal's any portals placed from now will execute the command"));
			}
			else
			{
				player.sendMessage(Text.of(TextColors.BLUE, "[CommandPortals]: ", TextColors.DARK_RED, "Error! ", TextColors.RED, "Command not inputed."));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /togglesetportal!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /togglesetportal!"));
		}

		return CommandResult.success();
	}
}
