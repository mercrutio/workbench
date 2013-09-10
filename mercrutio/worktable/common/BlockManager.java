package mercrutio.worktable.common;

import mercrutio.worktable.common.ore.*;
import mercrutio.worktable.common.workbench.BlockWorkbench;
import mercrutio.worktable.common.workbench.WorkbenchInventory;
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
	public static Block uranium238;
	public static Block zinc;
	public static Block platinum;
	public static Block silicon;
	public static Block aluminum;
	public static Block nickel;
	public static Block germanium;
	public static Block tin;
	public static Block uranium235;
	public static Block tungsten;
	
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
		uranium238 = new OreUranium238(Ids.oreUranium238_actual);
		registerBlock (uranium238, "Uranium-238", "uranium238");
		zinc = new OreZinc(Ids.oreZinc_actual);
		registerBlock (zinc, "Zinc", "zinc");
		platinum = new OrePlatinum(Ids.orePlatinum_actual);
		registerBlock (platinum, "Platinum", "platinum");
		silicon = new OreSilicon(Ids.oreSilicon_actual);
		registerBlock (silicon, "Silicon", "silicon");
		aluminum = new OreAluminum(Ids.oreAluminum_actual);
		registerBlock (aluminum, "Aluminum", "aluminum");
		nickel = new OreNickel(Ids.oreNickel_actual);
		registerBlock (nickel, "Nickel", "nickel");
		germanium = new OreGermanium(Ids.oreGermanium_actual);
		registerBlock (germanium, "Germanium", "germanium");
		tin = new OreTin(Ids.oreTin_actual);
		registerBlock (tin, "Tin", "tin");
		uranium235 = new OreUranium235(Ids.oreUranium235_actual);
		registerBlock (uranium235, "Uranium-235", "uranium235");
		tungsten = new OreTungsten(Ids.oreTungsten_actual);
		registerBlock (tungsten, "Tungsten", "tungsten");
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
		Ids.oreUranium238_actual = config.getBlock("uranium238 Id", Ids.oreUranium238_default, (String)null).getInt();
		Ids.oreZinc_actual = config.getBlock("zinc Id", Ids.oreZinc_default, (String)null).getInt();
		Ids.orePlatinum_actual = config.getBlock("platinum Id", Ids.orePlatinum_default, (String)null).getInt();
		Ids.oreSilicon_actual = config.getBlock("silicon Id", Ids.oreSilicon_default, (String)null).getInt();
		Ids.oreAluminum_actual = config.getBlock("aluminum Id", Ids.oreAluminum_default, (String)null).getInt();
		Ids.oreNickel_actual = config.getBlock("nickel Id", Ids.oreNickel_default, (String)null).getInt();
		Ids.oreGermanium_actual = config.getBlock("germanium Id", Ids.oreGermanium_default, (String)null).getInt();
		Ids.oreTin_actual = config.getBlock("tin Id", Ids.oreTin_default, (String)null).getInt();
		Ids.oreUranium235_actual = config.getBlock("uranium235 Id", Ids.oreUranium235_default, (String)null).getInt();
		Ids.oreTungsten_actual = config.getBlock("tungsten Id", Ids.oreTungsten_default, (String)null).getInt();
	}
}
