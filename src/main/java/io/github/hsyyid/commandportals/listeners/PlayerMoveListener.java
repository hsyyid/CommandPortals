package io.github.hsyyid.commandportals.listeners;

import io.github.hsyyid.commandportals.CommandPortals;
import io.github.hsyyid.commandportals.utils.ConfigManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;

import java.util.Optional;

public class PlayerMoveListener
{
	@Listener
	public void onPlayerMove(DisplaceEntityEvent event)
	{
		if (event.getTargetEntity() instanceof Player)
		{
			Player player = (Player) event.getTargetEntity();
			Optional<Object> optionalValue = ConfigManager.getValue("portal.location." + player.getLocation().getBlockX() + "." + player.getLocation().getBlockY() + "." + player.getLocation().getBlockZ());

			if (optionalValue.isPresent())
			{
				Object object = optionalValue.get();
				String command = (String) object;

				if (!command.equals("null"))
				{
					command = command.replaceAll("@p", player.getName());
					CommandPortals.game.getCommandManager().process(CommandPortals.game.getServer().getConsole(), command);
				}
			}
		}
	}
}
