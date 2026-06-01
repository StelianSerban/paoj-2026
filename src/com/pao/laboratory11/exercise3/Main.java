package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Transaction> data = List.of(
                new Transaction(1,  new BigDecimal("1200.00"), LocalDate.of(2026, 1, 5),  "RO", "WEB"),
                new Transaction(2,  new BigDecimal("350.00"),  LocalDate.of(2026, 1, 8),  "RU", "ATM"),
                new Transaction(3,  new BigDecimal("6500.00"), LocalDate.of(2026, 1, 10), "NG", "APP"),
                new Transaction(4,  new BigDecimal("90.00"),   LocalDate.of(2026, 1, 12), "RO", "POS"),
                new Transaction(5,  new BigDecimal("1200.00"), LocalDate.of(2026, 1, 15), "DE", "WEB"),
                new Transaction(6,  new BigDecimal("4800.00"), LocalDate.of(2026, 2, 3),  "RU", "CRYPTO"),
                new Transaction(7,  new BigDecimal("250.00"),  LocalDate.of(2026, 2, 7),  "FR", "APP"),
                new Transaction(8,  new BigDecimal("6500.00"), LocalDate.of(2026, 2, 11), "RO", "WEB"),
                new Transaction(9,  new BigDecimal("100.00"),  LocalDate.of(2026, 2, 14), "KP", "ATM"),
                new Transaction(10, new BigDecimal("3300.00"), LocalDate.of(2026, 3, 1),  "DE", "CRYPTO")
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(5));

        // ── Interogare 1: Top 5 tranzacții după amount (tie-break: id asc) ──────
        System.out.println("=== Top 5 tranzacții ===");
        snap.getTopTransactions().forEach(tx ->
                System.out.printf("  #%2d | %10s RON | %s | %s | %s%n",
                        tx.getId(), tx.getAmount(), tx.getDate(), tx.getCountry(), tx.getChannel()));

        // ── Interogare 2: Număr tranzacții per țară (descendent) ─────────────────
        System.out.println("\n=== Tranzacții per țară ===");
        snap.getCountByCountry().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.printf("  %-4s : %d tranzacții%n", e.getKey(), e.getValue()));

        // ── Interogare 3: Canale ordonate după număr de utilizări (descendent) ───
        System.out.println("\n=== Utilizare canale ===");
        snap.getCountByChannel().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.printf("  %-8s : %d ori%n", e.getKey(), e.getValue()));

        // ── Interogare 4: Total sume per țară (descendent) ───────────────────────
        System.out.println("\n=== Total sume per țară ===");
        snap.getTotalByCountry().entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.printf("  %-4s : %10.2f RON%n", e.getKey(), e.getValue()));

        // ── Interogare 5: Total general ──────────────────────────────────────────
        System.out.printf("%n=== Total general: %.2f RON ===%n", snap.getTotalAmount());
    }
}