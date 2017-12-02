package character.villain;

import character.Beings;
import character.Representative;
import character.villain.Subs.Minion;
import character.villain.Subs.Scorpion;
import utils.coordinate._2Coordinate;
import utils.layout.Layout;
import utils.layout.LayoutBrief;
import utils.sorter.ComparingInterface;
import utils.sorter.Sorter;

public class Serpent extends Beings implements Representative {
    static private boolean DUPLICATED_LOCK = false;

    private Beings General;
    private Minion[] Soldiers;
    private int SoldierUnderCommand = 0;
    private Layout CurrentSubLayout;

    public Serpent(_2Coordinate birthplace){
        super(birthplace);
        if(DUPLICATED_LOCK)
            throw null;
        DUPLICATED_LOCK = true;
    }

    public int getSoldierUnderCommand() {
        return SoldierUnderCommand;
    }

    @Override
    public void DefaultConstituents(LayoutBrief init){
        SetLayout(init);
        General = new Scorpion(null);
        Soldiers = Minion.RecruitMinions(CurrentSubLayout.length - 1);
        General.JumpTO(CurrentSubLayout.FindHead());
        for (Beings soldier:Soldiers
                ) {
            soldier.FindMyPlaceInLayout(CurrentSubLayout);
        }
        SoldierUnderCommand = Soldiers.length;
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
        PrepareForNewDesignation();
        General.JumpTO(CurrentSubLayout.FindHead());
        if(CurrentSubLayout.length - 1 > Soldiers.length)
            Soldiers = Minion.RecruitMinions(CurrentSubLayout.length - 1, Soldiers);
        for (Beings soldier:Soldiers
                ) {
            boolean chk = soldier.FindMyPlaceInLayout(CurrentSubLayout);
            if(chk == false){
                soldier.JumpOut();
            }
        }
        int remain = CurrentSubLayout.length - CurrentSubLayout.getVacantNumber() - 1;
        SoldierUnderCommand =  remain > 0 ? remain : 0;
    }

    @Override
    public void SortConstituents(Sorter sorter, ComparingInterface cmpInterface){
        throw null;
    }

    @Override
    public Beings Hail(String name){
        if(this.TellMyName() == name)
            return this;
        if(General.TellMyName() == name)
            return General;
        for (Minion i:Soldiers
                ) {
            if(i.TellMyName() == name)
                return i;
        }
        return null;
    }

    private void SetLayout(LayoutBrief bf){
        CurrentSubLayout = new Layout(bf);
    }

    private void PrepareForNewDesignation(){
        General.JumpOut();
        for (int i = 0; i < Soldiers.length; i++) {
            Soldiers[i].JumpOut();
        }
    }

    @Override
    public String TellMyName(){
        return "蛇精";
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
