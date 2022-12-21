package com.ctypists.tankstars;

public class SaveGameObj implements java.io.Serializable {
    private String tank1Name;
    private String tank2Name;
    private int tank1health;
    private int tank2health;

    public SaveGameObj(String tank1Name, String tank2Name, int tank1health, int tank2health) {
        this.tank1Name = tank1Name;
        this.tank2Name = tank2Name;
        this.tank1health = tank1health;
        this.tank2health = tank2health;
    }
}
