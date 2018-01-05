package character.villain.Subs;

import Exceptions.character.FriendFireException;
import character.Beings;
import character.Subordinate;
import character.hero.Grandpa;
import character.hero.Huluwa;
import character.villain.Serpent;
import utils.FOREGROUNDS;
import utils.coordinate._2Coordinate;

public class Minion extends Subordinate {

    public Minion(_2Coordinate birthplace){
        super(birthplace);
        super.ChangeVisual(FOREGROUNDS.Frog);
    }

    @Override
    public String TellMyName(){
        return "喽啰";
    }

    @Override
    protected void AfterMeetingBeings(){
        throw null;
    }

    @Override
    public boolean isHero(){
        return false;
    }

    static public Minion[] RecruitMinions(int total_num, Minion... veterans){
        Minion[] ret = new Minion[total_num];

        int idx = 0;
        for (Minion veteran:veterans
             ) {
            ret[idx++] = veteran;
        }

        for (; idx < total_num; idx++) {
            ret[idx] = new Minion(null);
        }

        return ret;
    }

    @Override
    public boolean WinOrNot(Beings enemy) throws FriendFireException {
        if(enemy instanceof Minion || enemy instanceof Scorpion || enemy instanceof Serpent)
            throw new FriendFireException(enemy);
        if (enemy instanceof Grandpa)
            return true;
        else if(enemy instanceof Huluwa){
            int win = new java.util.Random().nextInt(100);
            if(win > 35)    return false;
            return true;
        }
        return true;
    }
}
