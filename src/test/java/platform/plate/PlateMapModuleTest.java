package platform.plate;

import Exceptions.plate.OutOfBorderException;
import org.junit.Test;
import utils.coordinate._2Coordinate;

import static org.junit.Assert.*;

public class PlateMapModuleTest {
    private PlateMapModule Map = new PlateMapModule(new _2Coordinate(1,1), new _2Coordinate(0,0), 10, 10);

    @Test
    public void Consistency() throws Exception{
        assertTrue(3 == Map.LocationWithBorderTest(new _2Coordinate(3,7)).getCoord().getTensors()[0]);
        assertTrue(7 == Map.LocationWithBorderTest(new _2Coordinate(3,7)).getCoord().getTensors()[1]);

        assertTrue(9 == Map.LocationWithBorderTest(new _2Coordinate(9,2)).getCoord().getTensors()[0]);
        assertTrue(2 == Map.LocationWithBorderTest(new _2Coordinate(9,2)).getCoord().getTensors()[1]);

        assertTrue(4 == Map.LocationWithBorderTest(new _2Coordinate(4,5)).getCoord().getTensors()[0]);
        assertTrue(5 == Map.LocationWithBorderTest(new _2Coordinate(4,5)).getCoord().getTensors()[1]);
    }

    @Test(expected = OutOfBorderException.class)
    public void locationWithBorderTest() throws Exception {
        Map.LocationWithBorderTest(new _2Coordinate(10,3));
    }

}