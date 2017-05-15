package ru.saoworld.sm.swortmasters.bin.bean;

import java.util.PriorityQueue;


public class Characteristics {
    private int stamina;
    private int strength;
    private int agility;
    private int freeChar;
    private int hp;
    private int copper;
    private String name;
    private int id;

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return this.stamina;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setFreeChar(int freeChar) {
        this.freeChar = freeChar;
    }

    public int getFreeChar() {
        return this.freeChar;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getAgility() {
        return this.agility;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return this.hp;
    }

    public void setCopper(int copper) {
        this.copper = copper;
    }

    public int getCopper() {
        return this.copper;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
