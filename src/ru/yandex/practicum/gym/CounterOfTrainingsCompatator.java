package ru.yandex.practicum.gym;

import java.util.Comparator;

public class CounterOfTrainingsCompatator implements Comparator<CounterOfTrainings> {
    @Override
    public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
        //сортировка по убыванию
        return Integer.compare(o2.getCountTrainings(), o1.getCountTrainings());
    }
}
