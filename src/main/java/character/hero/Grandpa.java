package character.hero;

import character.Beings;
import character.Representative;
import character.hero.Huluwas.*;
import utils.coordinate._2Coordinate;
import utils.layout.Layout;
import utils.layout.LayoutBrief;
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
        if(DUPLICATED_LOCK)
            throw null;
        DUPLICATED_LOCK = true;
    }

    @Override
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
    @Override
    public void RangeConstituents(LayoutBrief layout){
        SetLayout(layout);
        for (Huluwa baby:Huluwas
                ) {
            if(!baby.FindMyPlaceInLayout(CurrentLayout))
                break;
        }
    }

    @Override
    public void SortConstituents(Sorter sorter, ComparingInterface cmpInterface){
        sorter.Sort(CurrentLayout, cmpInterface);
    }

    @Override
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
