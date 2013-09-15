package mercrutio.worktable.common.block.wiring;

import mercrutio.worktable.common.block.interact.CanPower;
import net.minecraft.tileentity.TileEntity;

public class StdWireEntity extends WireEntity {
		
	public enum SidesOut {
		Std, oneOut, twoOpp, twoAdj;
	}
	
	public SidesOut sides;
	
	public StdWireEntity() {
		this.sides = SidesOut.Std;
	}
	
}
