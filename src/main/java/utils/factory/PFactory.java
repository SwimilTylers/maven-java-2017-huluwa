package utils.factory;

import utils.position.Position;

public interface PFactory<P extends Position> {
    P NewPosition(Object... coordinates) throws Exception;
}
