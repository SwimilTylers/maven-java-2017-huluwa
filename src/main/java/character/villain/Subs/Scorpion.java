package character.villain.Subs;

import character.Subordinate;
import utils.FOREGROUNDS;
import utils.coordinate._2Coordinate;

public class Scorpion extends Subordinate{

    public Scorpion(_2Coordinate birthplace){
        super(birthplace);
        super.ChangeVisual(FOREGROUNDS.Scorpion);
    }

    @Override
    public String TellMyName(){
        return "蝎精";
    }

    @Override
    protected void AfterMeetingBeings(){
        throw null;
    }

    @Override
    public boolean isHero(){
        return false;
    }
}
