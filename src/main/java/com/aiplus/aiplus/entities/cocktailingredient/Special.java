package com.aiplus.aiplus.entities.cocktailingredient;

enum specialIngredient{
    Agave, Amaretto, Angostura, BleuCorac, BlueCuraçao, Brancamenta, Brandy, Cocco, Cognac, Cointreau, Disaronno, FdSamb, Fernet, Genziana, GrMarnier, Jagermeister, Jägermeister, Kahlua, Limoncello, LiquoreCaffe, LiquoreCiliegia, LiquoreSambuca, Maraschino, Mezcal, Midori, Passoa, Ratafia, Tabasco, TriploSecco
}

public class Special {

    private String specialIngredient;
    private double ml;

public Special(specialIngredient specialIng, double ml) {
    this.specialIngredient = specialIng.toString();
    this.ml = ml;
}

public Special(String specialIng, double ml) {
    this.specialIngredient = specialIng;
    this.ml = ml;
}
}

