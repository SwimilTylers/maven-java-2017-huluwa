package ui;

import Exceptions.character.FriendFireException;
import Exceptions.plate.OutOfBorderException;
import character.Beings;
import character.hero.Grandpa;
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

    protected final String imgRsr, fireRsr, deadRsr;

    protected final Image deadImage;
    protected final Image fightImage;
    protected boolean isFired = false;

    public PlayerPayload(Beings player, PlateMapModule world, Battlefield field, String imageResource, String imageDeadResource,
                         RelativeMove relativeMove) {
        super(((int) player.TellMyBirthplace().X()), ((int) player.TellMyBirthplace().Y()));

        imgRsr = imageResource;
        deadRsr = imageDeadResource;
        fireRsr = "Boom.png";

        URL loc = this.getClass().getClassLoader().getResource(imageResource);
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);

        URL deadLoc = this.getClass().getClassLoader().getResource(imageDeadResource);
        ImageIcon deadIia = new ImageIcon(deadLoc);
        deadImage = deadIia.getImage();

        URL fireLoc = this.getClass().getClassLoader().getResource(fireRsr);
        ImageIcon fireIia = new ImageIcon(fireLoc);
        fightImage = fireIia.getImage();


        this.player = player;
        this.world = world;
        this.field = field;

        move = relativeMove;
    }

    public PayloadShot shot(){
        synchronized (this) {
            if (player.whetherAlive())
                return new PayloadShot(super.x(), super.y(), imgRsr);
            else if(!isFired)
                return new PayloadShot(super.x(),super.y(), fireRsr);
            else
                return new PayloadShot(super.x(),super.y(), deadRsr);
        }
    }

    private synchronized void internal_challenge(double x, double y){
        int[] change_x = new int[]{0,0,0,1,1,1,1,2,2,2,2,3,3,3,3};
        int[] change_y = new int[]{1,2,3,0,1,2,3,0,1,2,3,0,1,2,3};
        for (int i = 0; i < change_x.length; i++) {
            if(!this.player.whetherAlive()) break;
            try {
                this.player.Challenge(world.LocationWithBorderTest(new _2Coordinate(change_x[i] + x, change_y[i] + y)));
            } catch (FriendFireException e) {
            } catch (OutOfBorderException e) {
            }
        }

    }

    public boolean whetherAlive(){
        return player.whetherAlive();
    }

    public void run() {
        while (!Thread.interrupted()) {
            if (player.whetherAlive()){
                _2Coordinate pos = ((_2Coordinate) this.player.TellBasePosition().getCoord());



                _2Coordinate change = this.move.next();

                internal_challenge(pos.X(),pos.Y());

                try {
                    this.player.JumpTOAndChallenge(world.LocationWithBorderTest(new _2Coordinate(change.X() + pos.X(),
                            change.Y() + pos.Y())));
                } catch (OutOfBorderException e) {
                    if(move == RelativeMove.toLeft) move = RelativeMove.toRight;
                    else if(move == RelativeMove.toRight) move = RelativeMove.toLeft;
                    else if(move == RelativeMove.toLeftRandom) move = RelativeMove.toRightRandom;
                    else if(move == RelativeMove.toRightRandom) move = RelativeMove.toLeftRandom;
                } catch (FriendFireException e) {
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

                        Thread.sleep(new java.util.Random().nextInt(200) + 200);
                        //this.field.repaint();

                    } catch (Exception e) {

                    }
                }
            }
            else{
                if(isFired){
                    this.setImage(deadImage);
                    try {

                        Thread.sleep(1000);
                        //this.field.repaint();

                    } catch (Exception e) {

                    }
                }
                else{
                    this.setImage(fightImage);
                    try {
                        if(player instanceof Grandpa || player instanceof Huluwa)
                            field.minusGood(this);
                        else
                            field.minusBad(this);
                        Thread.sleep(new java.util.Random().nextInt(200) + 200);
                        //this.field.repaint();

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
        RelativeMove Random = ()->new _2Coordinate(2-(new java.util.Random().nextInt(5)), 1-(new java.util.Random().nextInt(3)));
        RelativeMove toLeftRandom = ()->new _2Coordinate(1-(new java.util.Random().nextInt(5)), 1-(new java.util.Random().nextInt(3)));
        RelativeMove toRightRandom = ()->new _2Coordinate((new java.util.Random().nextInt(5))-1, 1-(new java.util.Random().nextInt(3)));
    }
}

class MinionPayload extends PlayerPayload{
    public MinionPayload(_2Coordinate coord, PlateMapModule MapModule, Battlefield battlefield){
        super(new Minion(coord), MapModule, battlefield,
                "Toad.png", "DeadToad.png", RelativeMove.toLeftRandom);
        player.Birth(MapModule.Location(player.TellMyBirthplace()));
    }
}

class HuluwaPayload extends PlayerPayload{
    public HuluwaPayload(Huluwa huluwa, String image, PlateMapModule MapModule, Battlefield battlefield){
        super(huluwa, MapModule, battlefield,
                image, "Rock.png", RelativeMove.toRightRandom);
        player.Birth(MapModule.Location(player.TellMyBirthplace()));
    }
}
