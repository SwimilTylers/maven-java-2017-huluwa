package utils.factory;

import Exceptions.position.BackgroundIncompatibleException;
import Exceptions.position.CoordinateIncompatibleException;
import utils.BACKGROUNDS;
import utils.position.PositionWithBackground;

public class PositionBKFactory implements PFactory<PositionWithBackground>{

    public PositionWithBackground NewPosition(Object... coordinates) throws Exception {
        return NewPosition(BACKGROUNDS.values()[(new java.util.Random()).nextInt(BACKGROUNDS.values().length)], coordinates);
    }

    public PositionWithBackground NewPosition(Enum EnumOptions, Object... coordinates) throws Exception {
        if(!(coordinates[0] instanceof Double))
            throw new CoordinateIncompatibleException(coordinates[0], "First Coordinate is not a Double as required", coordinates);
        if(!(coordinates[1] instanceof Double))
            throw new CoordinateIncompatibleException(coordinates[1], "Second Coordinate is not a Double as required", coordinates);
        if(!(EnumOptions instanceof BACKGROUNDS))
            throw new BackgroundIncompatibleException("Background should be a BACKGROUND enum", EnumOptions);
        return new PositionWithBackground(((Double) coordinates[0]), ((Double) coordinates[1]), ((BACKGROUNDS) EnumOptions));
    }
}
