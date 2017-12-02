import Exceptions.character.LeaderNotQualified;
import Exceptions.plate.MapExpansionFailure;
import character.Beings;
import character.Representative;
import character.hero.Grandpa;
import character.villain.Subs.Scorpion;
import platform.plate.*;
import utils.BACKGROUNDS;
import utils.coordinate._2Coordinate;
import utils.layout.Layout;
import utils.layout.LayoutBrief;

public class Narrator {
    static private PlateMapModule Background;
    static private PlateLayoutManipModule Manip;
    static private Plate Personae;

    static private Representative GoodmanLeader;
    static private Representative BadguyLeader;

    static void ThereWasAWorld()throws MapExpansionFailure {
        Background = new PlateMapModule_Background(
                PlateSettings.Regularized.granularity(),
                PlateSettings.Regularized.start(),
                PlateSettings.Regularized.XNum(),
                PlateSettings.Regularized.YNum(),
                BACKGROUNDS.Tree);

        Manip = null;
    }

    static void ThereWereSuchLeaders(Beings Good, Beings Bad) throws LeaderNotQualified {
        if(Good instanceof Representative)
            GoodmanLeader = (Representative) Good;
        else
            throw new LeaderNotQualified(Good.TellMyName() + " is not a qualified GoodmanLeader", Good);
        if(Bad instanceof Representative)
            BadguyLeader = (Representative) Bad;
        else
            throw new LeaderNotQualified(Bad.TellMyName() + " is not a qualified BadguyLeader", Bad);
        Personae = Plate.CreateRealm(PlateSettings.Regularized, Background, Manip, Good, Bad);
    }

    static void TheyLedManyPeople(_2Coordinate[] GoodMan, _2Coordinate[] BadGuy){
        GoodmanLeader.DefaultConstituents(new LayoutBrief(Background, GoodMan));
        BadguyLeader.DefaultConstituents(new LayoutBrief(Background, BadGuy));
    }

    static void HeChangeTheTeam(Representative he, _2Coordinate... layout){
        he.RangeConstituents(new LayoutBrief(Background, layout));
    }

    static void Current(){
        System.out.println(Background.MakeEveryoneResponse());
    }

    public static void main(String[] argv){
        try{
            ThereWasAWorld();
            ThereWereSuchLeaders(new Grandpa(new _2Coordinate(6,2)), new Scorpion(new _2Coordinate(9,12)));
            TheyLedManyPeople(Layout.Changshe, Layout.Heyi);
            Current();

            HeChangeTheTeam(BadguyLeader, Layout.Fanggang);
            Current();

            HeChangeTheTeam(BadguyLeader, Layout.Fengshi);
            Current();

            HeChangeTheTeam(BadguyLeader, Layout.Yanyue);
            Current();

            HeChangeTheTeam(BadguyLeader, Layout.Yanxing);
            Current();
        }
        catch (MapExpansionFailure ex){
            System.out.println("It is just a fantasy.");
        }
        catch (LeaderNotQualified ex){
            System.out.println("Another bad story.");
        }
    }
}
