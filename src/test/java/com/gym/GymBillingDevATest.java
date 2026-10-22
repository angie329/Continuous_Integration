package com.gym;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias asignadas a Developer A (R1, R2, R3, R7).
 * Rama en Git: feature/dev-a
 *
 * NOTA PARA LA RÚBRICA:
 * 1. Para provocar el fallo intencional de la Parte 7 (Paso 4):
 *    Cambia temporalmente un assertEquals (ej. 30 por 99), sube el commit y abre el PR.
 * 2. Toma captura de GitHub Actions con cruz roja (❌).
 * 3. Corrige el test a su valor real y sube el commit para ver el check verde (✔️).
 */
public class GymBillingDevATest {

    @Test
    public void testR1PlanPricesValid() {
        var res = GymBilling.calculateSubtotal("BASIC", List.of(), 1);
        assertTrue(res.isValid());
        assertEquals(new BigDecimal("30"), res.subtotal());
    }

    @Test
    public void testR1InvalidPlanReturnsInvalid() {
        var res = GymBilling.calculateSubtotal("GOLD", List.of(), 1);
        assertFalse(res.isValid());
    }

    @Test
    public void testR2InvalidExtraReturnsInvalid() {
        var res = GymBilling.calculateSubtotal("BASIC", List.of("UNKNOWN_EXTRA"), 1);
        assertFalse(res.isValid());
    }

    @Test
    public void testR2PremiumExtraWithNonPremiumPlanFails() {
        // SPA es premium, en plan BASIC debe ser invalido
        var res = GymBilling.calculateSubtotal("BASIC", List.of("SPA"), 1);
        assertFalse(res.isValid());
    }

    @Test
    public void testR3SubtotalCalculation() {
        // STANDARD (45) + PERSONAL_TRAINING (25) = 70 * 2 miembros = 140
        var res = GymBilling.calculateSubtotal("STANDARD", List.of("PERSONAL_TRAINING"), 2);
        assertTrue(res.isValid());
        assertEquals(new BigDecimal("140"), res.subtotal());
    }

    @Test
    public void testR7RoundingHalfUp() {
        assertEquals(121, GymBilling.roundResult(new BigDecimal("121.5")));
        assertEquals(121, GymBilling.roundResult(new BigDecimal("121.4")));
        assertEquals(-1, GymBilling.roundResult(new BigDecimal("-1")));
    }
}
