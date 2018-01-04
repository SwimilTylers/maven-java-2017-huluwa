package Exceptions.character;

import character.Beings;

public class FriendFireException extends Exception{
    private Beings friend;

    public FriendFireException(Beings f){
        friend = f;
    }

    Beings getFriend(){
        return friend;
    }
}
