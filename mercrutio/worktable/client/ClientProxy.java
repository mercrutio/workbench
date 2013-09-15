package mercrutio.worktable.client;

import mercrutio.worktable.common.CommonProxy;
import mercrutio.worktable.common.Ids;
import mercrutio.worktable.common.block.wiring.StdWireEntity;
import mercrutio.worktable.common.block.wiring.render.StdWireEntityRenderer;
import mercrutio.worktable.common.block.wiring.render.StdWireItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {}
	
	@Override 
	public void registerRenderThings() {
		ClientRegistry.bindTileEntitySpecialRenderer(StdWireEntity.class, new StdWireEntityRenderer());
		MinecraftForgeClient.registerItemRenderer(Ids.wire_actual, new StdWireItemRenderer());
	}

}
