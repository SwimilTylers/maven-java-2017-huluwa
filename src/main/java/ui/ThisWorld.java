package ui;
import javax.swing.*;

public class ThisWorld extends JFrame{
    private final int OFFSET = 30;


    public ThisWorld() {
        InitUI();
    }

    public void InitUI() {
//        Battlefield battlefield = new Battlefield(50,32,16, 0, 0);
        Battlefield battlefield = new Battlefield(50,29,13,50*3,50*3);
        add(battlefield);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(battlefield.getBoardWidth() + OFFSET,
                battlefield.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("葫芦娃大战蛇精");
    }
}
