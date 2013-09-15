package mercrutio.worktable.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import mercrutio.worktable.common.block.interact.processor.ContainerProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
        if (packet.channel.equals("MercrutioPackets")) {
        	if (((EntityPlayer)player).openContainer instanceof ContainerProcessor) {
                handleProcessor(packet, player);
        	}
        }
	}

	private void handleProcessor(Packet250CustomPayload packet, Player player) {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        boolean request = true;
        boolean init = false;
        try {
			request = inputStream.readBoolean();
			init = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
        if (init) {
        	ContainerProcessor cont = (ContainerProcessor) ((EntityPlayer) player).openContainer;
	        if (!request) {
	        	cont.recieveUpdates(packet);
	        } else {
	        	cont.update();
	        }
        }
	}
}
