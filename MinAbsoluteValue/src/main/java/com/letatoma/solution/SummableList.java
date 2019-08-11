package com.letatoma.solution;

import java.util.ArrayList;
import java.util.Collection;

public class SummableList extends ArrayList<Integer> {
    private int sum = 0;

    public int getSum() {
        return sum;
    }

    @Override
    public boolean add(Integer integer) {
        sum += integer;
        return super.add(integer);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Integer)) {
            throw new IllegalArgumentException();
        }
        Integer integer = (Integer) o;
        sum -= integer;
        return super.remove(o);
    }

    @Override
    public Integer remove(int index) {
        Integer number = this.get(index);
        sum -= number;
        return super.remove(index);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        for (Integer element : c) {
            sum += element;
            this.add(element);
        }
        return true;
    }
}

