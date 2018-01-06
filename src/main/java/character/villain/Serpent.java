package character.villain;

import Exceptions.character.FriendFireException;
import character.Beings;
import character.Representative;
import character.hero.Grandpa;
import character.hero.Huluwa;
import character.villain.Subs.Minion;
import character.villain.Subs.Scorpion;
import utils.FOREGROUNDS;
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
        super.ChangeVisual(FOREGROUNDS.Snake);
        if(DUPLICATED_LOCK)
            throw null;
        DUPLICATED_LOCK = true;
    }

    public void unlock(){
        DUPLICATED_LOCK = false;
    }

    public int getSoldierUnderCommand() {
        return SoldierUnderCommand;
    }

    @Override
    public boolean WinOrNot(Beings enemy) throws FriendFireException {
        if(enemy instanceof Minion || enemy instanceof Scorpion || enemy instanceof Serpent)
            throw new FriendFireException(enemy);
        if (enemy instanceof Grandpa)
            return true;
        else if(enemy instanceof Huluwa){
            int win = new java.util.Random().nextInt(100);
            if(win > 80)    return false;
            return true;
        }
        return true;
    }


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


    public void SortConstituents(Sorter sorter, ComparingInterface cmpInterface){
        throw null;
    }


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
