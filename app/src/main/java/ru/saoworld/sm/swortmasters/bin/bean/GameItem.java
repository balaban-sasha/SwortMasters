package ru.saoworld.sm.swortmasters.bin.bean;


public class GameItem {
    private String name;
    private String descrpiption;
    private int stamina;
    private int aglitity;
    private int strength;
    private int attack;
    private int health;
    private int dodge;
    private int defense;
    private int speed;
    private int level;
    private int itemId;
    private int itemCost;
    private GameItem gameItem;

    public String getName() {
        return name;
    }

    public String getDescrpiption() {
        return descrpiption;
    }

    public int getStamina() {
        return stamina;
    }

    public int getAglitity() {
        return aglitity;
    }

    public int getStrength() {
        return strength;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public int getDodge() {
        return dodge;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLevel() {
        return level;
    }
    public int getItemCost() {
        return itemCost;
    }

    public int getItemId() {
        return itemId;
    }

    public GameItem(int itemId, String name, String descrpiption, int stamina, int aglitity, int strength, int attack, int health, int dodge, int defense, int speed, int level, int cost) {
        this.itemId = itemId;
        this.name = name;
        this.descrpiption = descrpiption;
        this.stamina = stamina;
        this.aglitity = aglitity;
        this.strength = strength;
        this.attack = attack;
        this.health = health;
        this.dodge = dodge;
        this.defense = defense;
        this.speed = speed;
        this.level = level;
        this.itemCost = cost;
    }

    @Override
    public String toString() {
        String characteristic = "";
        if (aglitity != 0)
            characteristic += "Aglity: " + aglitity + ". ";
        if (strength != 0)
            characteristic += "Strength: " + strength + ". ";
        if (stamina != 0)
            characteristic += "Stamina: " + stamina + ". ";
        if (dodge != 0)
            characteristic += "Dodge: " + dodge + ". ";
        if (attack != 0)
            characteristic += "Attack: " + attack + ". ";
        if (health != 0)
            characteristic += "Health: " + health + ". ";
        if (defense != 0)
            characteristic += "Defense: " + defense + ". ";
        if (speed != 0)
            characteristic += "Speed: " + speed + ". ";
        if (level != 0)
            characteristic += "Level: " + level + ". ";
        if (itemCost != 0)
            characteristic += "Price: " + itemCost + ". ";
        return characteristic;
    }
}
