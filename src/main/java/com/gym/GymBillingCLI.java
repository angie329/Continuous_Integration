package com.gym;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GymBillingCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistema de Facturación de Gimnasio (ESPOL IS2) ===");
        System.out.print("Ingrese el plan (BASIC, STANDARD, PREMIUM): ");
        String plan = scanner.nextLine().trim().toUpperCase();

        System.out.print("¿Desea agregar extras? (separados por coma o ENTER para ninguno): ");
        String extrasInput = scanner.nextLine().trim();
        List<String> extras = new ArrayList<>();
        if (!extrasInput.isEmpty()) {
            for (String part : extrasInput.split(",")) {
                if (!part.trim().isEmpty()) {
                    extras.add(part.trim().toUpperCase());
                }
            }
        }

        System.out.print("Ingrese el número de miembros: ");
        int members = Integer.parseInt(scanner.nextLine().trim());

        // Cálculo modular
        var subtotalRes = GymBilling.calculateSubtotal(plan, extras, members);
        if (!subtotalRes.isValid()) {
            System.out.println("Selección inválida. Resultado: -1");
            return;
        }

        BigDecimal subtotal = subtotalRes.subtotal();
        BigDecimal afterGroup = GymBillingDiscounts.applyGroupDiscount(subtotal, members);
        BigDecimal afterOffer = GymBillingDiscounts.applySpecialOffer(afterGroup);
        BigDecimal afterSurcharge = GymBillingDiscounts.applyPremiumSurcharge(afterOffer, subtotalRes.hasPremiumExtra());
        int total = GymBilling.roundResult(afterSurcharge);

        System.out.println("\n--- Desglose de Facturación ---");
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Tras Descuento Grupal (R4): $" + afterGroup);
        System.out.println("Tras Oferta Especial (R5): $" + afterOffer);
        System.out.println("Tras Recargo Premium (R6): $" + afterSurcharge);
        System.out.println("Total Final Redondeado (R7): $" + total);

        System.out.print("\n¿Desea confirmar la compra? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if ("y".equals(confirm)) {
            System.out.println("Resultado de la transacción: " + total);
        } else {
            System.out.println("Operación cancelada. Resultado: -1");
        }
    }
}
