package mercrutio.worktable.common;

import net.minecraftforge.event.ServerChatEvent;

public class ServerChatCommand{
	private ServerChatEvent event;
	private String com;
	private String args;
	
	public ServerChatCommand (ServerChatEvent event) {
		this.event = event;
		if (event.message != null) {
			char c = event.message.charAt(0);
			if (c == '#') {
				String com = "";
				int comLength = 0;
				for (int i = 1; c != ' '; i++) {
					com = com + Character.toString(c);
					c = event.message.charAt(i);
					comLength++;
				}
				this.com = com;
				this.args = event.message.substring(comLength);
			}
		}
	}
	
	public boolean isCommand(){
		
		if (com != null){
			return true;
		}
		return false;
	}
	
	public String getCommand(){
		return com;
	}
	public String getArgs(){
		return args;
	}
	
}
