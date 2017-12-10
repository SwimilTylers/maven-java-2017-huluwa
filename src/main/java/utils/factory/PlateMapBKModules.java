package utils.factory;

import Exceptions.plate.MapExpansionFailure;
import Exceptions.position.BackgroundIncompatibleException;
import javafx.util.Pair;
import platform.plate.PlateMapModule_Background;
import platform.plate.PlateSettings;
import utils.BACKGROUNDS;
import utils.layout.Layout;

import java.util.ArrayList;

public class PlateMapBKModules extends PlateMapModules {
    static public PlateMapModule_Background InitializeMapModule(PlateSettings Settings, Enum EnumOptions, Object... Additional) {
        try {
            if(!(EnumOptions instanceof BACKGROUNDS))
                try {
                    throw new BackgroundIncompatibleException("Background should be a BACKGROUND enum", EnumOptions);
                } catch (BackgroundIncompatibleException e) {
                    e.printStackTrace();
                }
            return new PlateMapModule_Background(Settings.granularity(), Settings.start(), Settings.XNum(), Settings.YNum(), (BACKGROUNDS) EnumOptions);
        } catch (MapExpansionFailure mapExpansionFailure) {
            mapExpansionFailure.printStackTrace();
            System.out.println(mapExpansionFailure.getMessage());
        }
        return null;
    }

    static public PlateMapModule_Background InitializeMapModule(PlateSettings Settings, Object... Additional) {
        return InitializeMapModule(Settings, BACKGROUNDS.values()[new java.util.Random().nextInt(BACKGROUNDS.values().length)], Additional);
    }

    static public PlateMapModule_Background InitializeMapModule(PlateSettings Settings, Enum EnumOptions, ArrayList<Pair<BACKGROUNDS, Layout>> layouts){
        PlateMapModule_Background Map = InitializeMapModule(Settings, EnumOptions);
        for (Pair<BACKGROUNDS, Layout> layout:layouts
             ) {
            Map.setBackground(layout.getKey(), layout.getValue());
        }
        return Map;
    }
}
