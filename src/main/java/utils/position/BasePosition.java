package utils.position;

import Exceptions.character.FriendFireException;
import character.Beings;
import utils.coordinate.Coordinate;

public class BasePosition {
    protected Coordinate coord;
    protected final int dimension;
    protected Beings content;

    BasePosition(int dimensionality){
        dimension = dimensionality;
        //coord = new Coordinate(dimension);
    }

    public boolean isOccupied(){
        synchronized (this) {
            if (content == null)
                return false;
            else
                return true;
        }
    }

    public int TellDimensionality(){
        return dimension;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public boolean ConsistencyCheck(Beings chk){
        synchronized (this) {
            if (content != null) {
                return content == chk;
            } else
                return false;
        }
    }

    final public boolean checkin(Beings standin){
        synchronized (this) {
            if (content != null)
                return false;
            else {
                content = standin;
                return true;
            }
        }
    }

    final public boolean checkinAndChallenge(Beings standin) throws FriendFireException {
        synchronized (this) {
            if (content != null)
                return challenge_internal(standin);
            else {
                content = standin;
                return true;
            }
        }
    }

    final public Beings checkout(){
        synchronized (this) {
            if (content != null) {
                Beings reside = content;
                content = null;
                return reside;
            }
            return null;
        }
    }

    public Beings getContent() {
        synchronized (this) {
            return content;
        }
    }

    public boolean challenge(Beings beings) throws FriendFireException {
        synchronized (this){
            return challenge_internal(beings);
        }
    }

    private boolean challenge_internal(Beings invade) throws FriendFireException {
        boolean result = true;
        if(content == null || !content.whetherAlive())
            return true;
        result = invade.WinOrNot(content);
        if(result){
            content.makeDead();
            content = invade;
            return true;
        }
        else{
            return false;
        }
    }

}

