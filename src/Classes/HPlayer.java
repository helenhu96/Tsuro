package Classes;

abstract class HPlayer implements IPlayer {
    protected String name;
    protected String color;
    HPlayer(String name) { this.name = name; }


    public String name() { return this.name; }


}
