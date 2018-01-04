package ui;

import Exceptions.character.FriendFireException;
import Exceptions.plate.OutOfBorderException;
import character.Beings;
import character.hero.Huluwa;
import character.villain.Subs.Minion;
import platform.plate.PlateMapModule;
import ui.demo.Thing2D;
import utils.coordinate._2Coordinate;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PlayerPayload extends Thing2D implements Runnable{
    protected final Beings player;
    protected final PlateMapModule world;
    protected final Battlefield field;
    protected RelativeMove move;

    protected final Image deadImage;
    protected final Image fightImage;
    protected boolean isFired = false;

    public PlayerPayload(Beings player, PlateMapModule world, Battlefield field, String imageResource, String imageDeadResource,
                         RelativeMove relativeMove) {
        super(((int) player.TellMyBirthplace().X()), ((int) player.TellMyBirthplace().Y()));

        URL loc = this.getClass().getClassLoader().getResource(imageResource);
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);

        URL deadLoc = this.getClass().getClassLoader().getResource(imageDeadResource);
        ImageIcon deadIia = new ImageIcon(deadLoc);
        deadImage = deadIia.getImage();

        URL fireLoc = this.getClass().getClassLoader().getResource("tile.png");
        ImageIcon fireIia = new ImageIcon(fireLoc);
        fightImage = fireIia.getImage();


        this.player = player;
        this.world = world;
        this.field = field;

        move = relativeMove;
    }

    public void run() {
        while (!Thread.interrupted()) {
            if (player.whetherAlive()){
                _2Coordinate pos = ((_2Coordinate) this.player.TellBasePosition().getCoord());

                _2Coordinate change = this.move.next();

                try {
                    this.player.JumpTOAndChallenge(world.LocationWithBorderTest(new _2Coordinate(change.X() + pos.X(),
                            change.Y() + pos.Y())));
                } catch (OutOfBorderException e) {
                    try {
                        this.player.JumpTOAndChallenge(world.Location(new _2Coordinate(pos.X(), pos.Y())));
                    } catch (FriendFireException e1) {
                        this.player.JumpTO(player.TellBasePosition());
                    }
                    if(move == RelativeMove.toLeft) move = RelativeMove.toRight;
                    else if(move == RelativeMove.toRight) move = RelativeMove.toLeft;
                } catch (FriendFireException e) {
                    this.player.JumpTO(player.TellBasePosition());
                }

                if(player.whetherAlive()) {
                    int x = super.x();
                    int y = super.y();
                    try {
                        super.setX(((int) player.TellBasePosition().getCoord().getTensors()[0]));
                        super.setY(((int) player.TellBasePosition().getCoord().getTensors()[1]));
                    }catch (NullPointerException e) {
                        super.setX(x);
                        super.setY(y);
                        return;
                    }
                    try {

                        Thread.sleep(new java.util.Random().nextInt(200) + 100);
                        this.field.repaint();

                    } catch (Exception e) {

                    }
                }
            }
            else{
                if(isFired){
                    this.setImage(deadImage);
                    try {

                        Thread.sleep(new java.util.Random().nextInt(200) + 100);
                        this.field.repaint();

                    } catch (Exception e) {

                    }
                }
                else{
                    this.setImage(fightImage);
                    try {

                        Thread.sleep(new java.util.Random().nextInt(200) + 100);
                        this.field.repaint();

                    } catch (Exception e) {

                    }
                    finally {
                        isFired = true;
                    }
                }
            }
        }
    }

    public interface RelativeMove{
        _2Coordinate next();
        RelativeMove still = ()->new _2Coordinate(0,0);
        RelativeMove toRight = ()->new _2Coordinate(new java.util.Random().nextInt(2),0);
        RelativeMove toLeft = ()->new _2Coordinate(-(new java.util.Random().nextInt(2)),0);
        RelativeMove Random = ()->new _2Coordinate(2-(new java.util.Random().nextInt(4)), 2-(new java.util.Random().nextInt(4)));
    }
}

class MinionPayload extends PlayerPayload{
    public MinionPayload(_2Coordinate coord, PlateMapModule MapModule, Battlefield battlefield){
        super(new Minion(coord), MapModule, battlefield,
                "player.png", "tile.png", RelativeMove.Random);
        player.Birth(MapModule.Location(player.TellMyBirthplace()));
    }
}

class HuluwaPayload extends PlayerPayload{
    public HuluwaPayload(Huluwa huluwa, PlateMapModule MapModule, Battlefield battlefield){
        super(huluwa, MapModule, battlefield,
                "player.png", "tile.png", RelativeMove.Random);
        player.Birth(MapModule.Location(player.TellMyBirthplace()));
    }
}
