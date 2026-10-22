package com.gym;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * Lógica asignada a Developer A:
 * - R1: Catálogo y validación de planes
 * - R2: Catálogo y validación de extras (los premium solo aplican a PREMIUM)
 * - R3: Cálculo del subtotal: (precio_plan + extras) * miembros
 * - R7: Redondeo mitad hacia arriba al final y retorno de -1 si es inválido
 *
 * Rama en Git: feature/dev-a
 */
public class GymBilling {

    // R1: Precios de los planes
    public static final Map<String, BigDecimal> PLAN_PRICES = Map.of(
        "BASIC", new BigDecimal("30"),
        "STANDARD", new BigDecimal("45"),
        "PREMIUM", new BigDecimal("70")
    );

    public record ExtraItem(BigDecimal price, boolean isPremium) {}

    // R2: Precios de los extras y si son premium
    public static final Map<String, ExtraItem> EXTRA_PRICES = Map.of(
        "GROUP_CLASSES", new ExtraItem(new BigDecimal("15"), false),
        "PERSONAL_TRAINING", new ExtraItem(new BigDecimal("25"), false),
        "SPA", new ExtraItem(new BigDecimal("30"), true),
        "SPECIALIZED_PROGRAM", new ExtraItem(new BigDecimal("40"), true)
    );

    public record SubtotalResult(BigDecimal subtotal, boolean hasPremiumExtra, boolean isValid) {}

    /**
     * R1, R2, R3: Valida entradas y calcula el subtotal.
     */
    public static SubtotalResult calculateSubtotal(String plan, List<String> extras, int members) {
        if (!PLAN_PRICES.containsKey(plan) || members < 1) {
            return new SubtotalResult(BigDecimal.ZERO, false, false);
        }

        BigDecimal extrasCost = BigDecimal.ZERO;
        boolean hasPremiumExtra = false;

        for (String extra : extras) {
            if (!EXTRA_PRICES.containsKey(extra)) {
                return new SubtotalResult(BigDecimal.ZERO, false, false);
            }
            ExtraItem item = EXTRA_PRICES.get(extra);
            if (item.isPremium()) {
                hasPremiumExtra = true;
                if (!"PREMIUM".equals(plan)) {
                    return new SubtotalResult(BigDecimal.ZERO, false, false);
                }
            }
            extrasCost = extrasCost.add(item.price());
        }

        BigDecimal planPrice = PLAN_PRICES.get(plan);
        BigDecimal subtotal = planPrice.add(extrasCost).multiply(BigDecimal.valueOf(members));
        return new SubtotalResult(subtotal, hasPremiumExtra, true);
    }

    /**
     * R7: Redondea el total final hacia arriba al número entero más cercano.
     */
    public static int roundResult(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            return -1;
        }
        return amount.setScale(0, RoundingMode.HALF_UP).intValue();
    }
}
