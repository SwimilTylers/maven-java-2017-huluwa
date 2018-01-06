package ui;

import character.Beings;
import character.hero.Grandpa;
import character.hero.Huluwas.*;
import character.villain.Serpent;
import platform.plate.PlateMapModule;
import ui.demo.Thing2D;
import ui.demo.Tile;
import utils.coordinate._2Coordinate;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Battlefield extends JPanel{
    private final int SPACE;
    private final int Horizontal_num;
    private final int Vertical_num;

    private Tile Background;
    private Tile Win, Lose, Welcome;
    private ArrayList<PlayerPayload> players = new ArrayList<>();
    private HashSet<PlayerPayload> deadplayer = new HashSet<>();

    private final int width;
    private final int height;
    private boolean completed = false;
    private boolean welcome = true;

    private final PlateMapModule MapModule;

    private int GoodCount;
    private int BadCount;

    private final SynchroShot shot = new SynchroShot();
    private LinkedList<BattlefieldShot> shot_save = new LinkedList<>();
    private Integer currentStep = 0;
    private Boolean isSaved = false;
    private Boolean isReplay = false;

    private ObjectOutputStream out;

    public Battlefield(int space, int horizontal_num, int vertical_num, int widthMargin, int heigthMargin) {
        SPACE = space;
        Horizontal_num = horizontal_num;
        Vertical_num = vertical_num;

        width = space * (Horizontal_num - 1) + widthMargin;
        height = space * (Vertical_num - 1) + heigthMargin;

        MapModule = new PlateMapModule(new _2Coordinate(1,1), new _2Coordinate(0,0), horizontal_num, vertical_num);

        addKeyListener(new Battlefield.TAdapter());
        setFocusable(true);
        initWorld();

    }

    public void minusBad(PlayerPayload requester){
        synchronized (deadplayer) {
            if (deadplayer.contains(requester)) return;
            deadplayer.add(requester);
        }
        --BadCount;
    }

    public void minusGood(PlayerPayload requester){
        synchronized (deadplayer) {
            if (deadplayer.contains(requester)) return;
            deadplayer.add(requester);
        }
        --GoodCount;
    }

    public int getBoardWidth() {
        return this.width;
    }

    public int getBoardHeight() {
        return this.height;
    }


    public final void initPlayers(){
        // grandpa
        Beings newGuy = new Grandpa(new _2Coordinate(4,7));
        newGuy.Birth(MapModule.Location(newGuy.TellMyBirthplace()));
        players.add(new PlayerPayload(newGuy, MapModule, this,
                "Grandpa.png", "DeadGrandpa.png", PlayerPayload.RelativeMove.Random));


        // minion
        players.add(new MinionPayload(new _2Coordinate(20,1), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(20,3), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(20,5), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(20,7), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(20,9), MapModule, this));


        players.add(new MinionPayload(new _2Coordinate(23,2), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(23,4), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(23,6), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(23,8), MapModule, this));
        players.add(new MinionPayload(new _2Coordinate(23,10), MapModule, this));


        // Huluwas
        players.add(new HuluwaPayload(new Dawa(new _2Coordinate(8,3)), "Huluwa/Dawa.png", MapModule, this));
        players.add(new HuluwaPayload(new Erwa(new _2Coordinate(8,4)), "Huluwa/Erwa.png", MapModule, this));
        players.add(new HuluwaPayload(new Sanwa(new _2Coordinate(8,5)), "Huluwa/Sanwa.png", MapModule, this));
        players.add(new HuluwaPayload(new Siwa(new _2Coordinate(8,6)), "Huluwa/Siwa.png", MapModule, this));
        players.add(new HuluwaPayload(new Wuwa(new _2Coordinate(8,7)), "Huluwa/Wuwa.png", MapModule, this));
        players.add(new HuluwaPayload(new Liuwa(new _2Coordinate(8,8)), "Huluwa/Liuwa.png", MapModule, this));
        players.add(new HuluwaPayload(new Qiwa(new _2Coordinate(8,9)), "Huluwa/Qiwa.png", MapModule, this));


        // serpent
        newGuy = new Serpent(new _2Coordinate(28,6));
        newGuy.Birth(MapModule.Location(newGuy.TellMyBirthplace()));
        players.add(new PlayerPayload(newGuy, MapModule, this,
                "Serpent.png", "DeadSerpent.png", PlayerPayload.RelativeMove.Random));


        GoodCount = 8;
        BadCount = 11;

    }

    public final void initBackground(){
        Background = new Tile(0,0);
        Win = new Tile(0,0,"Win.png");
        Lose = new Tile(0,0,"Lose.png");
        Welcome = new Tile(0,0,"Welcome.png");
    }

    public final void initWorld() {
        initBackground();
        initPlayers();
    }

    public void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if(welcome) {
            g.drawImage(Welcome.getImage(), 0, 0, width + SPACE, height + SPACE, this);
            return;
        }

        if(GoodCount > 0 && BadCount > 0) {
            g.drawImage(Background.getImage(), 0, 0, width + SPACE, height + SPACE, this);
            if(!isReplay){

                ArrayList<PlayerPayload> alive = new ArrayList<>();

                for (PlayerPayload player:players
                        ) {
                    if(deadplayer.contains(player))
                        g.drawImage(player.getImage(), player.x() * SPACE + 2, player.y() * SPACE + 2, this);
                    else
                        alive.add(player);
                }


                for (PlayerPayload player:alive
                     ) {
                    g.drawImage(player.getImage(), player.x() * SPACE + 2, player.y() * SPACE + 2, this);
                }
            }
            else{
                try {
                    ArrayList<PayloadShot> shots_set = null;
                    synchronized (currentStep) {
                        shots_set = shot_save.get(currentStep).getShots();
                        GoodCount = shot_save.get(currentStep).getGood();
                        BadCount = shot_save.get(currentStep).getBad();
                        ++currentStep;
                    }

                    if (shots_set == null) return;
                    for (int i = 0; i < players.size(); i++) {
                        PayloadShot item = shots_set.get(i);
                        g.drawImage(item.getImg(), item.getX() * SPACE + 2, item.getY() * SPACE + 2, this);
                    }
                } catch(Exception e) {}
            }
        }
        else {
            if (GoodCount <= 0) {
                g.drawImage(Lose.getImage(), 0, 0, width + SPACE, height + SPACE, this);

            }
            else if(BadCount <= 0){
                g.drawImage(Win.getImage(), 0, 0, width + SPACE, height + SPACE, this);
            }
            else{
                System.out.println(GoodCount+" "+BadCount);
            }
            try {
                synchronized (isSaved){
                    if (!isSaved && !isReplay) {
                        shot_save.add(new BattlefieldShot(players, GoodCount, BadCount));
                        out.writeObject(shot_save);
                        out.close();
                        out = null;
                        isSaved = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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
                if (!welcome){
                    try {
                        out = new ObjectOutputStream(new FileOutputStream(new File(((Long)System.currentTimeMillis())+".rcd")));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    ExecutorService exec = Executors.newCachedThreadPool();
                    for (PlayerPayload player:players
                            ) {
                        exec.execute(player);
                    }
                    exec.execute(shot);
                    exec.shutdown();
                    completed = true;
                }
                welcome = false;
            }
            else if (key == KeyEvent.VK_L) {
                JFileChooser dlg = new JFileChooser();
                dlg.setDialogTitle("选择回放记录");
                dlg.addChoosableFileFilter(new FileNameExtensionFilter("Display record", "rcd"));
                if (dlg.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                    File fn = dlg.getSelectedFile();

                    if (fn != null) {

                        isReplay = true;
                        completed = true;
                        welcome = false;

                        ObjectInputStream in = null;
                        try {
                            in = new ObjectInputStream(new FileInputStream(fn));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        if (in == null) return;
                        try {
                            shot_save = (LinkedList<BattlefieldShot>) in.readObject();
                        } catch (IOException | ClassNotFoundException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                in.close();
                                new Thread(shot).start();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }

            repaint();
        }
    }


    public void restartLevel() {

//        tiles.clear();
        initWorld();
        if (completed) {
            completed = false;
        }
    }

    public class SynchroShot implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    while (GoodCount <= 0 && BadCount <= 0)
                        Battlefield.this.wait();
                    TimeUnit.MILLISECONDS.sleep(100);

                    shot_save.add(new BattlefieldShot(players, GoodCount, BadCount));

                    repaint();
                }
            } catch (InterruptedException e) {
            }
        }
    }
    @Override
    protected void finalize() throws Throwable {
        if(!isSaved && !isReplay) {
            out.writeObject(shot_save);
            out.close();
        }
        super.finalize();
    }

}
