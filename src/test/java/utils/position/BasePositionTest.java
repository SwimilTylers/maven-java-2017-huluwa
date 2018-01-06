package utils.position;

import Exceptions.character.FriendFireException;
import character.Beings;
import character.hero.Grandpa;
import character.villain.Serpent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.coordinate._2Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BasePositionTest {
    private ArrayList<Position> positions = new ArrayList<>();

    @Before
    public void load() throws Exception{
        positions.add(new Position(0,0));
        positions.add(new Position(0,1));
        positions.add(new Position(1,0));
        positions.add(new Position(1,1));
    }

    @After
    public void unload() throws Exception{
        positions.clear();
    }

    @Test
    public void checkin() throws Exception {
        Beings being_1 = new Grandpa(new _2Coordinate(0,0));
        ((Grandpa)being_1).unlock();
        being_1.Birth(positions.get(0));

        Beings being_2 = new Serpent(new _2Coordinate(0,1));
        being_2.Birth(positions.get(1));
        ((Serpent)being_2).unlock();

        positions.get(0).checkin(being_2);
        assertSame(positions.get(0), being_1.TellBasePosition());
        assertSame(positions.get(1), being_2.TellBasePosition());
    }

    @Test
    public void checkout_robust() throws Exception{
        positions.get(1).checkout();
    }

    @Test
    public void checkout() throws Exception {
        Beings being_1 = new Grandpa(new _2Coordinate(0,0));
        ((Grandpa)being_1).unlock();
        being_1.Birth(positions.get(1));
        positions.get(1).checkout();
        assertSame(null, positions.get(1).content);
    }

    @Test
    public void challenge() throws Exception {
        Beings being_1 = new Grandpa(new _2Coordinate(0,0));
        being_1.Birth(positions.get(0));
        ((Grandpa)being_1).unlock();
        Beings being_2 = new Serpent(new _2Coordinate(0,1));
        ((Serpent)being_2).unlock();
        assertTrue(positions.get(1).challenge(being_2));
        assertTrue(positions.get(0).challenge(being_2));
    }

    @Test(expected = FriendFireException.class)
    public void self_challenge() throws Exception{
        Beings being_1 = new Grandpa(new _2Coordinate(0,0));
        ((Grandpa)being_1).unlock();
        being_1.Birth(positions.get(0));
        assertTrue(positions.get(0).challenge(being_1));
    }

}