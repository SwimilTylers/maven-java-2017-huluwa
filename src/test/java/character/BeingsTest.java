package character;

import Exceptions.character.FriendFireException;
import character.hero.Grandpa;
import character.villain.Serpent;
import character.villain.Subs.Minion;
import utils.coordinate._2Coordinate;
import utils.position.Position;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BeingsTest {
    private ArrayList<Beings> beings = new ArrayList<>();
    private ArrayList<Position> positions = new ArrayList<>();


    @org.junit.Before
    public void init_again() throws Exception{
        beings.clear();
        beings.add(new Grandpa(new _2Coordinate(0,0)));
        beings.add(new Serpent(new _2Coordinate(0,1)));

        positions.clear();
        positions.add(new Position(0,0));
        positions.add(new Position(0,1));
        positions.add(new Position(1,0));
        positions.add(new Position(1,1));

        beings.get(0).Birth(positions.get(0));
        beings.get(1).Birth(positions.get(1));
    }

    @org.junit.After
    public void unload() throws Exception{
        ((Grandpa)beings.get(0)).unlock();
        ((Serpent)beings.get(1)).unlock();

        beings.clear();
        positions.clear();
    }


    @org.junit.Test
    public void jumpTO() throws Exception {
        beings.get(0).JumpTO(positions.get(1));
        assertSame(positions.get(0), beings.get(0).TellBasePosition());
        assertSame(positions.get(1), beings.get(1).TellBasePosition());

        beings.get(1).JumpTO(positions.get(0));
        assertSame(positions.get(0), beings.get(0).TellBasePosition());
        assertSame(positions.get(1), beings.get(1).TellBasePosition());
    }

    @org.junit.Test(expected = FriendFireException.class)
    public void challenge_friend() throws Exception {
        beings.add(new Minion(new _2Coordinate(1,0)));
        beings.get(2).Birth(positions.get(2));
        beings.get(2).Challenge(positions.get(1));
    }

    @org.junit.Test
    public void jumpTOAndChallenge_Failure() throws Exception {
        beings.get(0).JumpTOAndChallenge(positions.get(1));
        assertNull(beings.get(0).TellBasePosition());
        assertSame(positions.get(1), beings.get(1).TellBasePosition());

        beings.add(new Minion(new _2Coordinate(1,0)));
        beings.get(2).Birth(positions.get(2));
        beings.get(1).Challenge(positions.get(1));
        assertSame(positions.get(1), beings.get(1).TellBasePosition());
        assertSame(positions.get(2), beings.get(2).TellBasePosition());
    }


    @org.junit.Test
    public void jumpTOAndChallenge_Success() throws Exception {
        beings.get(1).JumpTOAndChallenge(positions.get(0));
        assertNull(beings.get(0).TellBasePosition());
        assertSame(positions.get(0), beings.get(1).TellBasePosition());
    }
}