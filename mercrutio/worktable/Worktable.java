package mercrutio.worktable;

import mercrutio.worktable.common.CommonProxy;
import mercrutio.worktable.common.EventManager;
import mercrutio.worktable.common.MercrutioEventHandler;
import mercrutio.worktable.common.MercrutioGuiHandler;
import mercrutio.worktable.common.PacketHandler;
import mercrutio.worktable.common.block.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Worktable.modId, name = "Mercrutio's Worktable", version = "0.1.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"MercrutioPackets"}, packetHandler = PacketHandler.class)

public class Worktable {
	
	public static final String modId = "mercrutiosWorktable";
	
	@Instance(modId)
	public static Worktable instance;
	
	private MercrutioGuiHandler guiHandlerMercrutio = new MercrutioGuiHandler();
	EventManager eventManager = new EventManager();
	MercrutioEventHandler eventHandler = new MercrutioEventHandler();
		
	@SidedProxy(clientSide = "mercrutio.worktable.client.ClientProxy",
			serverSide = "mercrutio.worktable.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		BlockManager.getIds(config);
		config.save();
	}
	
	@EventHandler
	public void Load(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(eventManager);
		
		BlockManager.registerBlocks();
	
		GameRegistry.addShapedRecipe(new ItemStack(BlockManager.worktable), 
				"GGG",
				"PWP",
				"PCP",
				'W',Block.workbench,
				'C',Block.chest,
				'P',Block.planks,
				'G',Block.glass);
				
		NetworkRegistry.instance().registerGuiHandler(this, guiHandlerMercrutio);
		MinecraftForge.EVENT_BUS.register(eventHandler);
		
		proxy.registerRenderThings();
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {

	}

}
