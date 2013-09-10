package mercrutio.worktable;

import mercrutio.worktable.common.BlockManager;
import mercrutio.worktable.common.CommonProxy;
import mercrutio.worktable.common.EventManager;
import mercrutio.worktable.common.GuiHandlerMercrutio;
import mercrutio.worktable.common.Ids;
import mercrutio.worktable.common.workbench.BlockWorkbench;
import mercrutio.worktable.common.workbench.WorkbenchInventory;
import mercrutio.worktable.common.workbench.worktable.BetterCraftingManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
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
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Worktable.modId, name = "Mercrutio's Worktable", version = "0.1.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class Worktable {
	
	public static final String modId = "mercrutiosWorktable";
	
	@Instance(modId)
	public static Worktable instance;
	
	private GuiHandlerMercrutio guiHandlerMercrutio = new GuiHandlerMercrutio();
	EventManager eventmanager = new EventManager();
		
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
		GameRegistry.registerWorldGenerator(eventmanager);
		
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
		
		proxy.registerRenderThings();
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {

	}

}
