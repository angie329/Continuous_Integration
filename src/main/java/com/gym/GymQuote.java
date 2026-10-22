package com.gym;

public class GymQuote {
    
    // R4, R5 y R6
    public static double applyDiscountsAndSurcharges(double amount, int members, boolean hasPremiumExtra) {
        // R4: Descuento grupal del 10% con 2 o más miembros
        if (members >= 2) {
            amount = amount * 0.90;
        }
        
        // R5: Oferta especial aplicada sobre el monto post-R4
        if (amount > 400) {
            amount = amount - 50;
        } else if (amount > 200) {
            amount = amount - 20;
        }
        
        // R6: Recargo premium del 15% aplicado sobre el monto post-R5
        if (hasPremiumExtra) {
            amount = amount * 1.15;
        }
        
        return amount;
    }
}