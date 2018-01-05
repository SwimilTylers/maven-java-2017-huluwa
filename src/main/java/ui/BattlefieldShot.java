package ui;

import java.io.Serializable;
import java.util.ArrayList;

public class BattlefieldShot implements Serializable {
    private static final long serialVersionUID = 127097037015630254L;
    private final ArrayList<PayloadShot> shots = new ArrayList<>();
    private final int Good, Bad;

    public BattlefieldShot(ArrayList<PlayerPayload> players, int GoodCount, int BadCount){
        for (PlayerPayload player:players
                ) {
            shots.add(player.shot());
        }
        Good = GoodCount;
        Bad = BadCount;
    }

    public ArrayList<PayloadShot> getShots() {
        return shots;
    }

    public int getBad() {
        return Bad;
    }

    public int getGood() {
        return Good;
    }
}
