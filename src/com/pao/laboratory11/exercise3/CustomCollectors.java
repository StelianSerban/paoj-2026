package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;

public final class CustomCollectors {

    private CustomCollectors() {}

    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {

        class Agg {
            final Map<String, Long>       countByCountry = new HashMap<>();
            final Map<String, Long>       countByChannel = new HashMap<>();
            final Map<String, BigDecimal> totalByCountry = new HashMap<>();
            BigDecimal                    totalAmount    = BigDecimal.ZERO;
            final List<Transaction>       all            = new ArrayList<>();

            void accumulate(Transaction tx) {
                countByCountry.merge(tx.getCountry(), 1L, Long::sum);
                countByChannel.merge(tx.getChannel(), 1L, Long::sum);
                totalByCountry.merge(tx.getCountry(), tx.getAmount(), BigDecimal::add);
                totalAmount = totalAmount.add(tx.getAmount());
                all.add(tx);
            }

            Agg combine(Agg other) {
                other.countByCountry.forEach((k, v) -> countByCountry.merge(k, v, Long::sum));
                other.countByChannel.forEach((k, v) -> countByChannel.merge(k, v, Long::sum));
                other.totalByCountry.forEach((k, v) -> totalByCountry.merge(k, v, BigDecimal::add));
                totalAmount = totalAmount.add(other.totalAmount);
                all.addAll(other.all);
                return this;
            }

            Snapshot finish() {
                // top N dupa amount desc, tie-break id asc (stabil)
                List<Transaction> top = all.stream()
                        .sorted(Comparator.comparing(Transaction::getAmount).reversed()
                                .thenComparingInt(Transaction::getId))
                        .limit(topN)
                        .collect(java.util.stream.Collectors.toList());

                return new Snapshot(countByCountry, countByChannel, totalByCountry, totalAmount, top);
            }
        }

        return Collector.of(
                Agg::new,
                Agg::accumulate,
                Agg::combine,
                Agg::finish,
                Collector.Characteristics.UNORDERED
        );
    }
}