package io.github.hsyyid.commandportals.utils;

import java.util.UUID;

public class SetPortal
{
	private UUID playerUUID;
	private String command;
	
	public SetPortal(UUID playerUUID, String command)
	{
		this.playerUUID = playerUUID;
		this.command = command;
	}
	
	public UUID getPlayerUUID()
	{
		return playerUUID;
	}
	
	public String getCommand()
	{
		return command;
	}
	
	public void setPlayerUUID(UUID playerUUID)
	{
		this.playerUUID = playerUUID;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
}
