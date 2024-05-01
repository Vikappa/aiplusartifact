package com.aiplus.aiplus.entities;

import com.aiplus.aiplus.entities.cocktailingredient.*;

import java.util.ArrayList;

enum Glass {
    Calice, Coppa, Flute, High, Low, Short, Shot;
}

enum Style {
     Dry, On_the_rocks, Shake_and_Strain, Shake_and_Pour, Mix_in_glass, Muddler, Frozen;
}

public class Cocktail {
    private int id;
    private String name;
    private Glass glass;
    private Style style;

    private ArrayList<Spirit> sprits;
    private Bitter bitter;
    private Wine wine;
    private ArrayList<Special> specialIngredients;
    private ArrayList<SweetAndSour> sweetAndSours;
    private ArrayList<Soft> softs;
    private ArrayList<Fruit> fruits;
    private ArrayList<Syrup> syrups;
}
