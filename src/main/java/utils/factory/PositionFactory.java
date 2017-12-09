package utils.factory;

import Exceptions.position.CoordinateIncompatibleException;
import utils.position.Position;

public class PositionFactory implements PFactory<Position>{

    public Position NewPosition(Object... coordinates) throws CoordinateIncompatibleException {
        if(!(coordinates[0] instanceof Double))
            throw new CoordinateIncompatibleException(coordinates[0], "First Coordinate is not a Double as required", coordinates);
        if(!(coordinates[1] instanceof Double))
            throw new CoordinateIncompatibleException(coordinates[1], "Second Coordinate is not a Double as required", coordinates);
        return new Position(((Double) coordinates[0]), ((Double) coordinates[1]));
    }

    public Position NewPosition(Enum EnumOptions, Object... GeneralOptions) throws Exception {
        return NewPosition(GeneralOptions);
    }
}
