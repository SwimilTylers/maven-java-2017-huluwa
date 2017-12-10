package utils.factory;

import platform.plate.PlateMapModule;
import platform.plate.PlateSettings;

public class PlateMapModules{
    static public PlateMapModule InitializeMapModule(PlateSettings Settings, Enum EnumOptions, Object... Additional) {
        return new PlateMapModule(Settings.granularity(), Settings.start(), Settings.XNum(), Settings.YNum());
    }

    static public PlateMapModule InitializeMapModule(PlateSettings Settings, Object... Additional) {
        return InitializeMapModule(Settings, null, Additional);
    }
}
