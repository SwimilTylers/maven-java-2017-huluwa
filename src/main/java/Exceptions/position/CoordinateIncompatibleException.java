package Exceptions.position;

public class CoordinateIncompatibleException extends Exception {

    public CoordinateIncompatibleException(Object WrongPoint, String Message, Object... Whole){
        super(Message);
        this.WrongPoint = WrongPoint;
        this.Input = Whole;
    }

    public Object[] getInput() {
        return Input;
    }




    private Object[] Input;
    private Object WrongPoint;

    public Object getWrongPoint() {
        return WrongPoint;
    }
}
