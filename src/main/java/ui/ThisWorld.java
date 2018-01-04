package ui;
import javax.swing.*;

public class ThisWorld extends JFrame{
    private final int OFFSET = 30;


    public ThisWorld() {
        InitUI();
    }

    public void InitUI() {
        Battlefield battlefield = new Battlefield(40,30,30);
        add(battlefield);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(battlefield.getBoardWidth() + OFFSET,
                battlefield.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("Huluwas");
    }


    public static void main(String[] args) {
        ThisWorld ground = new ThisWorld();
        ground.setVisible(true);
    }
}
