package mercrutio.worktable.common.block;

import mercrutio.worktable.common.Ids;
import mercrutio.worktable.common.block.interact.processor.BlockProcessor;
import mercrutio.worktable.common.block.interact.processor.ProcessorEntity;
import mercrutio.worktable.common.block.interact.workbench.BlockWorkbench;
import mercrutio.worktable.common.block.interact.workbench.WorkbenchInventory;
import mercrutio.worktable.common.block.ore.OreAluminum;
import mercrutio.worktable.common.block.ore.OreCopper;
import mercrutio.worktable.common.block.ore.OreLead;
import mercrutio.worktable.common.block.ore.OrePlatinum;
import mercrutio.worktable.common.block.ore.OreSilicon;
import mercrutio.worktable.common.block.ore.OreSilver;
import mercrutio.worktable.common.block.ore.OreTungsten;
import mercrutio.worktable.common.block.ore.OreUranium;
import mercrutio.worktable.common.block.ore.OreZinc;
import mercrutio.worktable.common.block.wiring.BlockStdWire;
import mercrutio.worktable.common.block.wiring.StdWireEntity;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockManager {
	public static Block worktable;
	public static Block silver;
	public static Block copper;
	public static Block lead;
	public static Block uranium;
	public static Block zinc;
	public static Block platinum;
	public static Block silicon;
	public static Block aluminum;
	public static Block tungsten;
	public static Block processor;
	public static Block wire;
	
	public static void registerBlocks(){
		worktable = new BlockWorkbench(Ids.workbench_actual)
			.setHardness(3.0f)
			.setResistance(3.0f)
			.setStepSound(Block.soundWoodFootstep);
		registerBlock (worktable, "Worktable", "worktable");
		GameRegistry.registerTileEntity(WorkbenchInventory.class, "worktableInventory");
		
		silver = new OreSilver(Ids.oreSilver_actual);
		registerBlock (silver, "Silver", "silver");
		copper = new OreCopper(Ids.oreCopper_actual);
		registerBlock (copper, "Copper", "copper");
		lead = new OreLead(Ids.oreLead_actual);
		registerBlock (lead, "Lead", "lead");
		uranium = new OreUranium(Ids.oreUranium_actual);
		registerBlock (uranium, "Uranium", "uranium");
		zinc = new OreZinc(Ids.oreZinc_actual);
		registerBlock (zinc, "Zinc", "zinc");
		platinum = new OrePlatinum(Ids.orePlatinum_actual);
		registerBlock (platinum, "Platinum", "platinum");
		silicon = new OreSilicon(Ids.oreSilicon_actual);
		registerBlock (silicon, "Silicon", "silicon");
		aluminum = new OreAluminum(Ids.oreAluminum_actual);
		registerBlock (aluminum, "Aluminum", "aluminum");
		tungsten = new OreTungsten(Ids.oreTungsten_actual);
		registerBlock (tungsten, "Tungsten", "tungsten");
		
		processor = new BlockProcessor(Ids.processor_actual);
		registerBlock (processor, "Processor", "processor");
		GameRegistry.registerTileEntity(ProcessorEntity.class, "processorInventory");
		
		wire = new BlockStdWire(Ids.wire_actual);
		registerBlock (wire, "Wire", "wire");
		GameRegistry.registerTileEntity(StdWireEntity.class, "wireEntity");
	}
	
	private static void registerBlock(Block block, String langName, String gameName, CreativeTabs tab) {
		block.setUnlocalizedName(gameName);
		block.setCreativeTab(tab);
		GameRegistry.registerBlock(block, gameName);
		LanguageRegistry.addName(block, langName);
	}
	
	private static void registerBlock(Block block, String langName, String gameName) {
		block.setUnlocalizedName(gameName);
		block.setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(block, gameName);
		LanguageRegistry.addName(block, langName);
	}

	public static void getIds(Configuration config) {
		Ids.workbench_actual = config.getBlock("workbench Id", Ids.workbench_default, (String)null).getInt();
		Ids.oreSilver_actual = config.getBlock("silver Id", Ids.oreSilver_default, (String)null).getInt();
		Ids.oreCopper_actual = config.getBlock("copper Id", Ids.oreCopper_default, (String)null).getInt();
		Ids.oreLead_actual = config.getBlock("lead Id", Ids.oreLead_default, (String)null).getInt();
		Ids.oreUranium_actual = config.getBlock("uranium Id", Ids.oreUranium_default, (String)null).getInt();
		Ids.oreZinc_actual = config.getBlock("zinc Id", Ids.oreZinc_default, (String)null).getInt();
		Ids.orePlatinum_actual = config.getBlock("platinum Id", Ids.orePlatinum_default, (String)null).getInt();
		Ids.oreSilicon_actual = config.getBlock("silicon Id", Ids.oreSilicon_default, (String)null).getInt();
		Ids.oreAluminum_actual = config.getBlock("aluminum Id", Ids.oreAluminum_default, (String)null).getInt();
		Ids.oreTungsten_actual = config.getBlock("tungsten Id", Ids.oreTungsten_default, (String)null).getInt();
		Ids.processor_actual = config.getBlock("processor Id", Ids.processor_default, (String)null).getInt();
		Ids.wire_actual = config.getBlock("wire Id", Ids.wire_default, (String) null).getInt();
	}
}
