package se.jtiden.sudoku.struct;

import java.util.*;

public class Array2d<T> implements CollectionDecorator<T>, Iterable<T> {
    private final int width;
    private final int height;
    private final Map<Coordinate, T> map;

    public Array2d(int width, int height) {
        this.width = width;
        this.height = height;
        map = new HashMap<Coordinate, T>(width*height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void set(Coordinate coordinate, T value) {
        validate(coordinate);
        map.put(coordinate, value);
    }

    public void validate(Coordinate coordinate) {
        if (coordinate.x <= 0) {
            throw new ArrayIndexOutOfBoundsException("Array coordinates must be positive, x:" + coordinate.x + ".");
        }

        if (coordinate.y <= 0) {
            throw new ArrayIndexOutOfBoundsException("Array coordinates must be positive, y:" + coordinate.y + ".");
        }

        if (coordinate.x > width) {
            throw new ArrayIndexOutOfBoundsException("Array coordinates out of bounds, x:" + coordinate.x + " width:" + width + ".");
        }

        if (coordinate.y > height) {
            throw new ArrayIndexOutOfBoundsException("Array coordinates out of bounds, y:" + coordinate.y + " height:" + height + ".");
        }
    }

    public T get(Coordinate coordinate) {
        validate(coordinate);
        return map.get(coordinate);
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public void forEach(Consumer<T> action) {
        for(T t : map.values()) {
            action.apply(t);
        }
    }

    @Override
    public <R> R reduce(final Collector<T, R> collector) {
        return new CollectionDecoratorImpl<>(this).reduce(collector);
    }

    @Override
    public boolean anyMatch(final Predicate<T> predicate) {
        return new CollectionDecoratorImpl<T>(this).anyMatch(predicate);
    }

    @Override
    public CollectionDecorator<T> filter(final Predicate<T> predicate) {
        return new CollectionDecoratorImpl<T>(this).filter(predicate);
    }

    public Iterable<? extends T> getRow(int y) {
        List<T> row = new ArrayList<T>();
        for (Coordinate key : map.keySet()) {
            if (key.y == y) {
                row.add(map.get(key));
            }
        }
        return row;
    }

    public Iterable<? extends T> getColumn(int x) {
        List<T> column = new ArrayList<T>();
        for (Coordinate key : map.keySet()) {
            if (key.x == x) {
                column.add(map.get(key));
            }
        }
        return column;
    }
}
