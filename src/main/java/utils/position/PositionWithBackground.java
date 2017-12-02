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
    public String toString(){
        if(content == null)
            return coord + backgrounds.toString();
        else
            return coord + content.TellMyName();
    }
}
