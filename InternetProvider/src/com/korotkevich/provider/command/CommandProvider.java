package com.korotkevich.provider.command;

/**
 * Defines command according to incoming command name
 * @author Korotkevich Kirill 2018-05-11
 *
 */
public class CommandProvider {

	/**
	 * Searches command im CommandType enum according to command name(String)
	 * @param commandName incomming command name(String)
	 * @return Command object
	 */
	public static Command defineCommand(String commandName){
		CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
		Command command = commandType.getCommand();
		
		return command;
		
	}
}
