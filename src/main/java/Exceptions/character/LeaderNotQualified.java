package Exceptions.character;

import character.Beings;

public class LeaderNotQualified extends Exception{
    Beings NotQualified;

    public LeaderNotQualified(String msg, Beings NotQualified){
        super(msg);
        this.NotQualified = NotQualified;
    }

    public Beings getNotQualified(){
        return NotQualified;
    }
}
