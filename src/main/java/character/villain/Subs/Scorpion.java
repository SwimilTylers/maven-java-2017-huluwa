package character.villain.Subs;

import Exceptions.character.FriendFireException;
import character.Beings;
import character.Subordinate;
import character.hero.Grandpa;
import character.hero.Huluwa;
import character.villain.Serpent;
import utils.FOREGROUNDS;
import utils.coordinate._2Coordinate;

public class Scorpion extends Subordinate{

    public Scorpion(_2Coordinate birthplace){
        super(birthplace);
        super.ChangeVisual(FOREGROUNDS.Scorpion);
    }

    @Override
    public boolean WinOrNot(Beings enemy) throws FriendFireException {
        if(enemy instanceof Minion || enemy instanceof Scorpion || enemy instanceof Serpent)
            throw new FriendFireException(enemy);
        if (enemy instanceof Grandpa)
            return true;
        else if(enemy instanceof Huluwa){
            int win = new java.util.Random().nextInt(100);
            if(win > 50)    return false;
            return true;
        }
        return true;
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
