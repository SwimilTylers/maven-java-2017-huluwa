package utils.position;

import utils.coordinate._2Coordinate;

public class Position extends BasePosition {

    public Position(double x, double y){
        super(2);
        coord = new _2Coordinate(x, y);
    }

    public void setPosition(double x, double y){
        coord = new _2Coordinate(x,y);
    }

    @Deprecated
    public String visualize(){
        if(content == null)
            return "{empty} ";
        else
            return "{" + content.TellMyName() + "}";
    }

    @Override
    public String toString(){
        return coord + visualize();
    }


}
