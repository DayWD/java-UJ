package uj.java.map2d;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

record Pair<R, C>(R row, C column) {
}

public class Map2DImpl<R, C, V> implements Map2D<R, C, V> {
    public Map<Pair<R, C>, V> map = new HashMap<>();

    @Override
    public V put(R rowKey, C columnKey, V value) {
        return map.put(new Pair<>(Objects.requireNonNull(rowKey), Objects.requireNonNull(columnKey)), value);
    }

    @Override
    public V get(R rowKey, C columnKey) {
        return map.get(new Pair<>(rowKey, columnKey));
    }

    @Override
    public V getOrDefault(R rowKey, C columnKey, V defaultValue) {
        return map.getOrDefault(new Pair<>(rowKey, columnKey), defaultValue);
    }

    @Override
    public V remove(R rowKey, C columnKey) {
        return map.remove(new Pair<>(rowKey, columnKey));
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return !isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Map<C, V> rowView(R rowKey) {
        Map<C, V> rowMap = new HashMap<>();
        for (var entry : map.entrySet()) {
            if (entry.getKey().row().equals(rowKey))
                rowMap.put(entry.getKey().column(), entry.getValue());
        }
        return Collections.unmodifiableMap(rowMap);
    }

    @Override
    public Map<R, V> columnView(C columnKey) {
        Map<R, V> columnMap = new HashMap<>();
        for (var entry : map.entrySet()) {
            if (entry.getKey().column().equals(columnKey))
                columnMap.put(entry.getKey().row(), entry.getValue());
        }
        return Collections.unmodifiableMap(columnMap);
    }

    @Override
    public boolean hasValue(V value) {
        return map.containsValue(value);
    }

    @Override
    public boolean hasKey(R rowKey, C columnKey) {
        return map.containsKey(new Pair<>(rowKey, columnKey));
    }

    @Override
    public boolean hasRow(R rowKey) {
        for (var entry : map.entrySet()) {
            if (entry.getKey().row().equals(rowKey))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasColumn(C columnKey) {
        for (var entry : map.entrySet()) {
            if (entry.getKey().column().equals(columnKey))
                return true;
        }
        return false;
    }

    @Override
    public Map<R, Map<C, V>> rowMapView() {
        Map<R, Map<C, V>> rowMap = new HashMap<>();
        for (var entry : map.entrySet()) {
            Map<C, V> newValueMap = rowMap.computeIfAbsent(entry.getKey().row(), r -> new HashMap<>());
            newValueMap.put(entry.getKey().column(), entry.getValue());
        }
        return Collections.unmodifiableMap(rowMap);
    }

    @Override
    public Map<C, Map<R, V>> columnMapView() {
        Map<C, Map<R, V>> columnMap = new HashMap<>();
        for (var entry : map.entrySet()) {
            Map<R, V> newValueMap = columnMap.computeIfAbsent(entry.getKey().column(), r -> new HashMap<>());
            newValueMap.put(entry.getKey().row(), entry.getValue());
        }
        return Collections.unmodifiableMap(columnMap);
    }

    @Override
    public Map2D<R, C, V> fillMapFromRow(Map<? super C, ? super V> target, R rowKey) {
        for (var entry : map.entrySet()) {
            if (entry.getKey().row().equals(rowKey)) {
                target.put(entry.getKey().column(), entry.getValue());
            }
        }
        return this;
    }

    @Override
    public Map2D<R, C, V> fillMapFromColumn(Map<? super R, ? super V> target, C columnKey) {
        for (var entry : map.entrySet()) {
            if (entry.getKey().column().equals(columnKey)) {
                target.put(entry.getKey().row(), entry.getValue());
            }
        }
        return this;
    }

    @Override
    public Map2D<R, C, V> putAll(Map2D<? extends R, ? extends C, ? extends V> source) {
        for (var entry : source.rowMapView().entrySet()) {
            for (var innerMap : entry.getValue().entrySet()) {
                map.put(new Pair<>(entry.getKey(), innerMap.getKey()), innerMap.getValue());
            }
        }
        return this;
    }

    @Override
    public Map2D<R, C, V> putAllToRow(Map<? extends C, ? extends V> source, R rowKey) {
        for (var entry : source.entrySet()) {
            map.put(new Pair<>(rowKey, entry.getKey()), entry.getValue());
        }
        return this;
    }

    @Override
    public Map2D<R, C, V> putAllToColumn(Map<? extends R, ? extends V> source, C columnKey) {
        for (var entry : source.entrySet()) {
            map.put(new Pair<>(entry.getKey(), columnKey), entry.getValue());
        }
        return this;
    }

    @Override
    public <R2, C2, V2> Map2D<R2, C2, V2> copyWithConversion(Function<? super R, ? extends R2> rowFunction, Function<? super C, ? extends C2> columnFunction, Function<? super V, ? extends V2> valueFunction) {
        Map2D<R2, C2, V2> copyMap = Map2D.createInstance();

        for (var entry : map.entrySet()) {
            var row = rowFunction.apply(entry.getKey().row());
            var column = columnFunction.apply(entry.getKey().column());
            var value = valueFunction.apply(entry.getValue());
            copyMap.put(row, column, value);
        }
        return copyMap;
    }
}