package Exceptions.position;

public class BackgroundIncompatibleException  extends Exception{
    public BackgroundIncompatibleException(String Message, Object WrongPoint){
        super(Message);
        this.WrongPoint = WrongPoint;
    }

    private Object WrongPoint;

    public Object getWrongPoint() {
        return WrongPoint;
    }
}
