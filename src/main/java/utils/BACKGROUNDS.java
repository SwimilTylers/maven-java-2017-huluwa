package utils;

public enum BACKGROUNDS {

    Prairie("\uD83C\uDF3F"), Mountain("⛰️"), Volcano("\uD83C\uDF0B"), Tree("\uD83C\uDF32"),
    Flower("\uD83C\uDF37"), Fire("\uD83D\uDD25"), Desert("\uD83C\uDFDC️"), Frog("\uD83C\uDF2B️");

    private String ShowAs;

    BACKGROUNDS(String PresentAs){
        this.ShowAs = PresentAs;
    }

    final public String getName(){
        return this.ShowAs;
    }

    @Override
    public String toString(){
        return getName();
    }
}
