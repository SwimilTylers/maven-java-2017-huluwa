package platform.plate;

import utils.COORD;
import utils.coordinate._2Coordinate;
import utils.layout.Layout;
import utils.layout.LayoutManip;

public class PlateLayoutManipModule implements LayoutManip {
    final public _2Coordinate granularity;
    final public _2Coordinate start;
    final public int[] size;

    PlateLayoutManipModule(_2Coordinate granularity, _2Coordinate start, int XNum, int YNum){
        this.granularity = new _2Coordinate(granularity);
        this.start = new _2Coordinate(start);
        size = new int[2];
        size[COORD.X.d()] = XNum;
        size[COORD.Y.d()] = YNum;
    }

    @Override
    public Layout CentroSymmetry(Layout orig){
        return null;
    }

    @Override
    public Layout InversionSymmetry(Layout orig){
        return null;
    }

    @Override
    public Layout ReversionSymmetry(Layout orig){
        return null;
    }

    @Override
    public Layout Combination(Layout... origs){
        return null;
    }

    @Override
    public Layout Intersection(Layout... origs){
        return null;
    }
}
