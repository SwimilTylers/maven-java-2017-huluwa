package utils.factory;

import utils.position.Position;

public interface PFactory<P extends Position> {
    P NewPosition(Object... GeneralOptions) throws Exception;
    P NewPosition(Enum EnumOptions, Object... GeneralOptions) throws Exception;
}
