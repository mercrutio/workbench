package mercrutio.worktable.common;

import mercrutio.worktable.common.block.interact.CanDisplay;
import mercrutio.worktable.common.block.interact.CanPower;
import mercrutio.worktable.common.block.wiring.BlockWire;
import mercrutio.worktable.common.block.wiring.WireEntity;
import mercrutio.worktable.common.event.machines.PowerEvent;
import mercrutio.worktable.common.event.machines.TPowerEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;

public class MercrutioEventHandler {
	
	public MercrutioEventHandler(){
		
	}
	
	@ForgeSubscribe
	public void createPower (PowerEvent event) {
		if (!event.world.isRemote) {
			int rad = 1;
			for (int x = event.x - rad; x <= event.x + rad; x++) {
				for (int z = event.z - rad; z <= event.z + rad; z++) {
					if (event.world.getBlockTileEntity(x,event.y,z) instanceof CanPower) {
						CanPower blockEntity = (CanPower) event.world.getBlockTileEntity(x, event.y, z);
						blockEntity.setCurrent(blockEntity.getCurrent() + event.current);
						if (blockEntity instanceof WireEntity) {
							((WireEntity)blockEntity).recieveAndSendPower(event);
						}
						if (blockEntity instanceof CanDisplay) {
							((CanDisplay)blockEntity).requestClientUpdate();
						}
					}
				}
			}
		}
	}
	
	@ForgeSubscribe
	public void transmitPower(TPowerEvent event) {
		if (!event.world.isRemote) {
			if (event.world.getBlockTileEntity(event.x2, event.y2, event.z2) instanceof CanPower) {
				CanPower blockEntity = (CanPower) event.world.getBlockTileEntity(event.x2, event.y2, event.z2);
				blockEntity.setCurrent(blockEntity.getCurrent() + event.current);
				if (blockEntity instanceof WireEntity) {
					((WireEntity)blockEntity).recieveAndSendPower(event);
				}
				if (blockEntity instanceof CanDisplay) {
					((CanDisplay)blockEntity).requestClientUpdate();
				}
			}
		}
	}
	
	@ForgeSubscribe
	public void mercrutioCommand (ServerChatEvent event) {
		String sender = event.username;
		if (sender != "") {
			ServerChatCommand com = new ServerChatCommand(event);
			switch (com.getCommand()){
			case "#echo":
				System.out.println(sender + ": " + com.getArgs());
				break;
			default:
				System.out.println(sender + ": Command Not Recongnized...");
			}
		}
	}
	
}