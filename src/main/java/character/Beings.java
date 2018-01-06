package character;

import Exceptions.character.FriendFireException;
import utils.FOREGROUNDS;
import utils.coordinate._2Coordinate;
import utils.layout.Layout;
import utils.position.BasePosition;
import utils.position.Position;

abstract public class Beings {
    private BasePosition where;
    private _2Coordinate birthplace;
    private FOREGROUNDS visualization = FOREGROUNDS.Folk;
//    private ArrayList<Beings> Friends;
    Boolean isAlive = true;


    public Beings(_2Coordinate birthplace){
        this.birthplace = birthplace;
    }

    final public void ChangeBirthplace(_2Coordinate wt){
        birthplace = wt;
    }

    final public _2Coordinate TellMyBirthplace(){
        synchronized (this) {
            return birthplace;
        }
    }

    final public boolean Birth(BasePosition p_birthplace){
        synchronized (this) {
            if (p_birthplace.isOccupied()) return false;
            JumpTO(p_birthplace);
            return true;
        }
    }

    final public BasePosition TellBasePosition(){
        return where;
    }

    final public void JumpTO(BasePosition toBasePosition){
        if (toBasePosition == null) throw null;
        if (where == toBasePosition) return;
        BasePosition temp = where;
        if (where != null) {
            if (where.ConsistencyCheck(this)) {
                JumpOut();
            } else
                throw null;
        }
        synchronized (isAlive) {
            if(isAlive == false)    return;
            boolean result = toBasePosition.checkin(this);
            if (result)
                where = toBasePosition;
            else {
                result = temp.checkin(this);
                if(result)
                    where = temp;
                else
                    where = null;
            }
        }
    }

    final public void Challenge(BasePosition toBasePosition) throws FriendFireException {
        if (toBasePosition == null) throw null;
        if (where == toBasePosition) return;
        synchronized (isAlive) {
            if(isAlive == false)    return;
            boolean result = toBasePosition.challenge(this);
            if(!result) {
                isAlive = false;
                where = null;
            }
        }
    }

    final public void JumpTOAndChallenge(BasePosition toBasePosition) throws FriendFireException {
        if (toBasePosition == null) throw null;
        if (where == toBasePosition) return;
        synchronized (isAlive) {
            if(isAlive == false)    return;
            boolean result = toBasePosition.checkinAndChallenge(this);
            if (result){
                JumpOut();
                where = toBasePosition;
            }
            else {
                isAlive = false;
                where = null;
            }
        }
    }

    final public BasePosition JumpOut(){
        BasePosition fromBasePosition = where;
        if (where != null) {
            if (where.ConsistencyCheck(this))
                where.checkout();
            where = null;
        }
        return fromBasePosition;
    }

    final public void makeDead(){
        synchronized (isAlive) {
            isAlive = false;
            where = null;
        }
    }

    final public Boolean whetherAlive(){
        synchronized (isAlive) {
            return isAlive;
        }

    }

    abstract public boolean WinOrNot(Beings enemy) throws FriendFireException;

    @Deprecated
    final static public void ExchangeOurPosition(Beings a, Beings b){
        BasePosition temp = b.JumpOut();
        b.JumpTO(a.JumpOut());
        a.JumpTO(temp);
    }

    protected void ChangeVisual(FOREGROUNDS newVisual){
        visualization = newVisual;
    }

    public String Visualize(){
        return visualization.getName();
    }

    @Deprecated
    public boolean FindMyPlaceInLayout(Layout layout){
        if (layout == null) return false;
        BasePosition selected = layout.FindVacantPlace();
        if (selected == null) return false;
        JumpOut();
        JumpTO(selected);
        return true;
    }

/*
    final public void Makefriends(Beings... Friends){
        for (Beings i:Friends
             ) {
            this.Friends.add(i);
        }
    }

    final public boolean isMyFriends(Beings Friend){
        for (Beings i:Friends
             ) {
            if(i == Friend)
                return true;
        }
        return false;
    }
*/
    abstract public String TellMyName();
    abstract protected void AfterMeetingBeings();
    abstract public boolean isHero(); // A Being can portend to be a good guy
}
