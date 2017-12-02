package Exceptions.position;

import utils.position.Position;

public class CoordinateMissingException extends Exception{
    Position problemPoint;

    public CoordinateMissingException(Position problemPoint){
        this.problemPoint = problemPoint;
    }

    public Position getProblemPoint(){
        return problemPoint;
    }
}
