package ui;

import character.Beings;
import character.hero.Grandpa;
import character.hero.Huluwas.*;
import character.villain.Serpent;
import character.villain.Subs.Minion;
import platform.plate.PlateMapModule;
import ui.demo.Player;
import ui.demo.Thing2D;
import ui.demo.Tile;
import utils.coordinate._2Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Battlefield extends JPanel{
    private final int SPACE;
    private final int Horizontal_num;
    private final int Vertical_num;

    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<PlayerPayload> players = new ArrayList<>();

    private final int width;
    private final int height;
    private boolean completed = false;

    private final PlateMapModule MapModule;

    public Battlefield(int space, int horizontal_num, int vertical_num) {
        SPACE = space;
        Horizontal_num = horizontal_num;
        Vertical_num = vertical_num;

        width = space * Horizontal_num;
        height = space * Vertical_num;

        MapModule = new PlateMapModule(new _2Coordinate(1,1), new _2Coordinate(0,0), horizontal_num, vertical_num);

        addKeyListener(new Battlefield.TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() {
        return this.width;
    }

    public int getBoardHeight() {
        return this.height;
    }


    public final void initPlayers(){
        // grandpa
        Beings newGuy = new Grandpa(new _2Coordinate(0,0));
        newGuy.Birth(MapModule.Location(newGuy.TellMyBirthplace()));
        players.add(new PlayerPayload(newGuy, MapModule, this,
                "player.png", "player.png", PlayerPayload.RelativeMove.Random));

        // Huluwas
        players.add(new HuluwaPayload(new Dawa(new _2Coordinate(2,1)), MapModule, this));
        players.add(new HuluwaPayload(new Erwa(new _2Coordinate(2,2)), MapModule, this));
        players.add(new HuluwaPayload(new Sanwa(new _2Coordinate(2,3)), MapModule, this));
        players.add(new HuluwaPayload(new Siwa(new _2Coordinate(2,4)), MapModule, this));
        players.add(new HuluwaPayload(new Wuwa(new _2Coordinate(2,5)), MapModule, this));
        players.add(new HuluwaPayload(new Liuwa(new _2Coordinate(2,6)), MapModule, this));
        players.add(new HuluwaPayload(new Qiwa(new _2Coordinate(2,7)), MapModule, this));


        // serpent
        newGuy = new Serpent(new _2Coordinate(7,0));
        newGuy.Birth(MapModule.Location(newGuy.TellMyBirthplace()));
        players.add(new PlayerPayload(newGuy, MapModule, this,
                "player.png", "player.png", PlayerPayload.RelativeMove.Random));

        // minion
        players.add(new MinionPayload(new _2Coordinate(6,1), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(6,2), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(6,3), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(6,4), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(6,5), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(6,6), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(6,7), MapModule, this));


    }

    public final void initBackground(){
        for (int i = 0; i < Vertical_num; ++i){
            for (int j = 0; j < Horizontal_num; ++j){
                tiles.add(new Tile(i, j));
            }
        }
    }

    public final void initWorld() {
        initBackground();
        initPlayers();
    }

    public void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Thing2D> world = new ArrayList<>();
        world.addAll(tiles);


        world.addAll(players);


        for (int i = 0; i < world.size(); i++) {

            Thing2D item = world.get(i);


            if (item instanceof Player) {
                g.drawImage(item.getImage(), item.x()*SPACE + 2, item.y()*SPACE + 2, this);
            } else {
                g.drawImage(item.getImage(), item.x()*SPACE, item.y()*SPACE, this);
            }

            if (completed) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (completed) {
                return;
            }


            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                ExecutorService exec = Executors.newCachedThreadPool();
                for (PlayerPayload player:players
                     ) {
                    exec.execute(player);
                }
                exec.shutdown();
            }

            repaint();
        }
    }


    public void restartLevel() {

        tiles.clear();
        initWorld();
        if (completed) {
            completed = false;
        }
    }
}
