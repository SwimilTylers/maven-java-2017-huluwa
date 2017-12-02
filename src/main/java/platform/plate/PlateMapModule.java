package platform.plate;

import Exceptions.plate.MapExpansionFailure;
import platform.PlatformMapModule;
import utils.COORD;
import utils.coordinate.Coordinate;
import utils.coordinate._2Coordinate;
import utils.factory.PFactory;
import utils.position.Position;

public class PlateMapModule implements PlatformMapModule {
    final public _2Coordinate granularity;
    final public _2Coordinate start;
    final public int[] size;

    final protected Position[][] Map;

    PlateMapModule(_2Coordinate granularity, _2Coordinate start, int XNum, int YNum){
        this.granularity = new _2Coordinate(granularity);
        this.start = new _2Coordinate(start);
        size = new int[2];
        size[COORD.X.d()] = XNum;
        size[COORD.Y.d()] = YNum;

        Map = new Position[size[COORD.Y.d()]][size[COORD.X.d()]];

        for (int i = 0; i < size[COORD.Y.d()]; i++) {
            for (int j = 0; j < size[COORD.X.d()]; j++) {
                Map[i][j] = new Position(start.X() + granularity.X() * j, start.Y() + granularity.Y() * i);
            }
        }
    }

    PlateMapModule(_2Coordinate granularity, _2Coordinate start, int XNum, int YNum, PFactory Factory, Object... Additionals)
        throws MapExpansionFailure
    {
        this.granularity = new _2Coordinate(granularity);
        this.start = new _2Coordinate(start);
        size = new int[2];
        size[COORD.X.d()] = XNum;
        size[COORD.Y.d()] = YNum;

        Map = new Position[size[COORD.Y.d()]][size[COORD.X.d()]];

        try {
            for (int i = 0; i < size[COORD.Y.d()]; i++) {
                for (int j = 0; j < size[COORD.X.d()]; j++) {
                    Map[i][j] = Factory.NewPosition(start.X() + granularity.X() * j, start.Y() + granularity.Y() * i, Additionals);
                }
            }
        }catch(Exception ex){System.out.println(ex.getMessage()); }
    }



    @Override
    public Position Location(Coordinate _coord){
        if(_coord.dimension != 2)    throw null;
        _2Coordinate coord = (_2Coordinate)_coord;
        int[] idx = {0, 0};
        idx[0] = (int)((coord.Y() - start.Y())/granularity.Y());
        idx[1] = (int)((coord.X() - start.X())/granularity.X());

        if(idx[0] < 0 || idx[0] >= size[COORD.Y.d()]
                || idx[1] < 0 || idx[1] >= size[COORD.X.d()])
            return null;
        return Map[idx[0]][idx[1]];
    }

    @Override
    public Position[] Location(Coordinate[] coords){
        Position[] ret = new Position[coords.length];
        for (int i = 0; i < coords.length; i++) {
            ret[i] = Location(coords[i]);
        }
        return ret;
    }

    @Override
    public String MakeEveryoneResponse(){
        String ret = new String();
        for (Position[] row:Map
                ) {
            String rowString = new String();
            for (Position col:row
                    ) {
                if(col.isOccupied())
                    rowString += ("{" + col.getContent().TellMyName() + "}\t");
                else
                    rowString += "{...}\t";
            }
            ret += (rowString + "\n");
        }
        return ret;
    }
}
