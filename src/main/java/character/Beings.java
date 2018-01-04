package character;

import utils.FOREGROUNDS;
import utils.coordinate._2Coordinate;
import utils.layout.Layout;
import utils.position.BasePosition;

abstract public class Beings {
    private BasePosition where;
    private _2Coordinate birthplace;
    private FOREGROUNDS visualization = FOREGROUNDS.Folk;
//    private ArrayList<Beings> Friends;

    public Beings(_2Coordinate birthplace){
        this.birthplace = birthplace;
    }

    final public void ChangeBirthplace(_2Coordinate wt){
        synchronized (this) {
            birthplace = wt;
        }
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
        synchronized (this) {
            return where;
        }
    }

    final public void JumpTO(BasePosition toBasePosition){
        synchronized (this) {
            if (toBasePosition == null) throw null;
            if (where == toBasePosition) return;
            if (toBasePosition.isOccupied()) {
                AfterMeetingBeings();
                return;
            }
            if (where != null) {
                if (where.ConsistencyCheck(this)) {
                    JumpOut();
                } else
                    throw null;
            }
            toBasePosition.checkin(this);
            where = toBasePosition;
        }
    }

    final public synchronized BasePosition JumpOut(){
        synchronized (this) {
            BasePosition fromBasePosition = where;
            if (where != null) {
                if (!where.ConsistencyCheck(this)) throw null;
                where.checkout();
                where = null;
            }
            return fromBasePosition;
        }
    }


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

    public boolean FindMyPlaceInLayout(Layout layout){
        if (layout == null) return false;
        BasePosition selected = layout.FindVacantPlace();
        if (selected == null) return false;
        synchronized (this) {
            JumpOut();
            JumpTO(selected);
            return true;

        /*
        if(!layout.isAvailable())   return false;
        while (true){
            int favor = new Random().nextInt(layout.length);
            if(!layout.nodes[favor].isOccupied()){
                JumpOut();
                JumpTO(layout.nodes[favor]);
                return true;
            }
        }
        */
        }
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
