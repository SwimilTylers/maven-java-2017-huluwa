package utils;

public enum FOREGROUNDS {
    Snake("\uD83D\uDC0D"), Frog("\uD83D\uDC38"), Scorpion("\uD83E\uDD82"), Elder("\uD83D\uDC74"),
    Young("\uD83D\uDC66"), Folk("\uD83D\uDC68");

    private String ShowAs;

    FOREGROUNDS(String PresentAs){
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
