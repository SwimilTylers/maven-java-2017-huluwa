package platform;

import utils.coordinate.Coordinate;
import utils.position.Position;

public interface PlatformMapModule {
    Position Location(Coordinate _coord);
    Position[] Location(Coordinate[] coords);
    String MakeEveryoneResponse();
}
