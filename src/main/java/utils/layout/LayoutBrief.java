package utils.layout;

import platform.PlatformMapModule;
import utils.coordinate.Coordinate;

public class LayoutBrief {
    final Coordinate[] coords;
    final PlatformMapModule world;

    public LayoutBrief(PlatformMapModule world, Coordinate... obj){
        this.world = world;
        this.coords = obj;
    }
}
