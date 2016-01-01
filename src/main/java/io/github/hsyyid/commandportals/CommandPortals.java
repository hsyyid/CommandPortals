package io.github.hsyyid.commandportals;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import io.github.hsyyid.commandportals.cmdexecutors.ToggleSetPortalExecutor;
import io.github.hsyyid.commandportals.listeners.PlayerMoveListener;
import io.github.hsyyid.commandportals.listeners.PlayerPlaceBlockListener;
import io.github.hsyyid.commandportals.utils.SetPortal;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@Plugin(id = "CommandPortals", name = "CommandPortals", version = "0.2")
public class CommandPortals
{
	public static Game game;
	public static ConfigurationNode config;
	public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
	public static Set<SetPortal> setPortalsList = Sets.newHashSet();

	@Inject
	private Logger logger;

	public Logger getLogger()
	{
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot = true)
	private File dConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> confManager;

	@Listener
	public void onServerInit(GameInitializationEvent event)
	{
		getLogger().info("CommandPortals loading...");

		game = Sponge.getGame();

		try
		{
			if (!dConfig.exists())
			{
				dConfig.createNewFile();
				config = confManager.load();
				confManager.save(config);
			}
			
			configurationManager = confManager;
			config = confManager.load();
		}
		catch (IOException exception)
		{
			getLogger().error("The default configuration could not be loaded or created!");
		}
		
		CommandSpec toggleSetPortalCommandSpec = CommandSpec.builder()
			.description(Text.of("Toggle Set Portal Command"))
			.permission("commandportals.togglesetportal")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("command")))))
			.executor(new ToggleSetPortalExecutor())
			.build();

		game.getCommandManager().register(this, toggleSetPortalCommandSpec, "togglesetportal");

		game.getEventManager().registerListeners(this, new PlayerPlaceBlockListener());
		game.getEventManager().registerListeners(this, new PlayerMoveListener());

		getLogger().info("-----------------------------");
		getLogger().info("CommandPortals was made by HassanS6000!");
		getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
		getLogger().info("Have fun, and enjoy! :D");
		getLogger().info("-----------------------------");
		getLogger().info("CommandPortals loaded!");
	}
	
	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
