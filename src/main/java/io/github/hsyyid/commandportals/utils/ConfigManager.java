package io.github.hsyyid.commandportals.utils;

import io.github.hsyyid.commandportals.CommandPortals;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.util.Optional;

public class ConfigManager
{
	public static Optional<Object> getValue(String path)
	{
		ConfigurationNode valueNode = CommandPortals.config.getNode((Object[]) (path).split("\\."));

		if (valueNode.getValue() != null)
		{
			return Optional.of(valueNode.getValue());
		}
		else
		{
			return Optional.empty();
		}
	}

	public static void saveLocation(Location<World> location, String command)
	{
		try
		{
			ConfigurationLoader<CommentedConfigurationNode> configManager = CommandPortals.getConfigManager();
			CommandPortals.config.getNode("portal", "location", String.valueOf(location.getBlockX()), String.valueOf(location.getBlockY()), String.valueOf(location.getBlockZ())).setValue(command);

			try
			{
				configManager.save(CommandPortals.config);
				configManager.load();
			}
			catch (IOException e)
			{
				System.out.println("Error while updating config.");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
