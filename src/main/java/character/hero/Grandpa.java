package character.hero;

import Exceptions.character.FriendFireException;
import character.Beings;
import character.Representative;
import character.hero.Huluwas.*;
import character.villain.Serpent;
import character.villain.Subs.Minion;
import character.villain.Subs.Scorpion;
import utils.FOREGROUNDS;
import utils.coordinate._2Coordinate;
import utils.layout.Layout;
import utils.layout.LayoutBrief;
import utils.position.BasePosition;
import utils.sorter.ComparingInterface;
import utils.sorter.Sorter;

/**
 * Grandpa is responsible for the management of Huluwas
 */

public class Grandpa extends Beings implements Representative {

    static private boolean DUPLICATED_LOCK = false;
    private Huluwa[] Huluwas;

    private Layout CurrentLayout;

    public Grandpa(_2Coordinate birthplace){
        super(birthplace);
        super.ChangeVisual(FOREGROUNDS.Elder);
        if(DUPLICATED_LOCK)
            throw null;
        DUPLICATED_LOCK = true;
    }

    public void unlock(){
        DUPLICATED_LOCK = false;
    }

    @Override
    public boolean WinOrNot(Beings enemy) throws FriendFireException {
        if(enemy instanceof Grandpa || enemy instanceof Huluwa)
            throw new FriendFireException(enemy);
        return false;
    }


    public void DefaultConstituents(LayoutBrief init){
        SetLayout(init);
        if(CurrentLayout.nodes.length != 7) throw null;
        Huluwas = new Huluwa[]{new Dawa(null),
                new Erwa(null), new Sanwa(null),
                new Siwa(null), new Wuwa(null),
                new Liuwa(null), new Qiwa(null)};
        for (Huluwa baby:Huluwas
                ) {
            baby.FindMyPlaceInLayout(CurrentLayout);
        }
    }
    /*
        @Override
        public void AddRepresent(Beings... obj){
            throw null;
        }
    */

    @Deprecated
    public void RangeConstituents(LayoutBrief layout){
        SetLayout(layout);
        for (Huluwa baby:Huluwas
                ) {
            if(!baby.FindMyPlaceInLayout(CurrentLayout))
                break;
        }
    }


    @Deprecated
    public void SortConstituents(Sorter sorter, ComparingInterface cmpInterface){
        sorter.Sort(CurrentLayout, cmpInterface);
    }


    @Deprecated
    public Beings Hail(String name){
        if(name == this.TellMyName())
            return this;
        for (Huluwa i:Huluwas
                ) {
            if(i.TellMyName() == name)
                return i;
        }
        return null;
    }

    private void SetLayout(LayoutBrief bf){
        CurrentLayout = new Layout(bf);
    }

    @Override
    public String TellMyName(){
        return "爷爷";
    }

    @Override
    protected void AfterMeetingBeings(){
        throw null;
    }

    @Override
    public boolean isHero(){
        return true;
    }
}
