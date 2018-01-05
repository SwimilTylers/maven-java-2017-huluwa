package ui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.net.URL;

public class PayloadShot implements Serializable {
    private static final long serialVersionUID = -6948701666143027339L;
    private final int x, y;
    private final String imgName;
    private Image img = null;

    public PayloadShot(int x, int y, String imgName) {
        this.x = x;
        this.y = y;
        this.imgName = imgName;
    }

    public Image getImg(){
        if(img == null){
            URL loc = this.getClass().getClassLoader().getResource(imgName);
            ImageIcon iia = new ImageIcon(loc);
            img = iia.getImage();
        }
        return img;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
