package io.github.hsyyid.commandportals.listeners;

import io.github.hsyyid.commandportals.CommandPortals;
import io.github.hsyyid.commandportals.utils.ConfigManager;
import io.github.hsyyid.commandportals.utils.SetPortal;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PlayerPlaceBlockListener
{
	@Listener
	public void onPlayerPlaceBlock(ChangeBlockEvent.Place event, @First Player player)
	{
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
			for (Transaction<BlockSnapshot> transaction : event.getTransactions())
			{
				Location<World> location = transaction.getFinal().getLocation().get();
				ConfigManager.saveLocation(location, setPortalFound.getCommand());
			}

			player.sendMessage(Text.of(TextColors.BLUE, "[CommandPortals]: ", TextColors.GREEN, "Set portal."));
		}
	}
}
