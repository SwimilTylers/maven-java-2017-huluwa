package utils.position;

import utils.BACKGROUNDS;

public class PositionWithBackground extends Position {

    BACKGROUNDS backgrounds;

    public PositionWithBackground(double x, double y, BACKGROUNDS backgrounds) {
        super(x, y);
        this.backgrounds = backgrounds;
    }

    public void setBackgrounds(BACKGROUNDS backgrounds){
        this.backgrounds = backgrounds;
    }

    @Override
    public String visualize(){
        if(content == null)
            return backgrounds.toString();
        else
            return content.Visualize();
    }
}
