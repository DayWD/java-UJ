package uj.java.w7.insurance;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class InsuranceValueCollector implements Collector<InsuranceEntry, Map<String, BigDecimal>, List<Pair>> {
    /**
     * A function that creates and returns a new mutable result container.
     *
     * @return a function which returns a new, mutable result container
     */
    @Override
    public Supplier<Map<String, BigDecimal>> supplier() {
        return HashMap::new;
    }

    /**
     * A function that folds a value into a mutable result container.
     *
     * @return a function which folds a value into a mutable result container
     */
    @Override
    public BiConsumer<Map<String, BigDecimal>, InsuranceEntry> accumulator() {
        return (map, insurance) -> {
            if (!map.containsKey(insurance.county())) {
                map.put(insurance.county(), insurance.tiv_2012().subtract(insurance.tiv_2011()));
            } else {
                BigDecimal oldValue = map.get(insurance.county());
                map.put(insurance.county(), oldValue.add(insurance.tiv_2012().subtract(insurance.tiv_2011())));
            }
        };
    }

    /**
     * A function that accepts two partial results and merges them.  The
     * combiner function may fold state from one argument into the other and
     * return that, or may return a new result container.
     *
     * @return a function which combines two partial results into a combined
     * result
     */
    @Override
    public BinaryOperator<Map<String, BigDecimal>> combiner() {
        return (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        };
    }

    /**
     * Perform the final transformation from the intermediate accumulation type
     * {@code A} to the final result type {@code R}.
     *
     * <p>If the characteristic {@code IDENTITY_FINISH} is
     * set, this function may be presumed to be an identity transform with an
     * unchecked cast from {@code A} to {@code R}.
     *
     * @return a function which transforms the intermediate result to the final
     * result
     */
    @Override
    public Function<Map<String, BigDecimal>, List<Pair>> finisher() {
        return map -> {
            List<Pair> list = new ArrayList<>();
            for (var objects : map.entrySet()) {
                list.add(new Pair(objects.getKey(), objects.getValue()));
            }
            list.sort(Comparator.comparing(Pair::insuranceValueDifference).reversed());
            return list.subList(0, 10);
        };
    }

    /**
     * Returns a {@code Set} of {@code Collector.Characteristics} indicating
     * the characteristics of this Collector.  This set should be immutable.
     *
     * @return an immutable set of collector characteristics
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
