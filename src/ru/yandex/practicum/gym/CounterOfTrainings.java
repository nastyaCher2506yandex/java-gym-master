package ru.yandex.practicum.gym;

public class CounterOfTrainings {
    private Coach coach;
    private Integer countTrainings = 0;

    CounterOfTrainings(Coach coach, Integer countTrainings) {
        this.coach = coach;
        this.countTrainings = countTrainings;
    }

    public Coach getCoach() { return coach; }
    public int getCountTrainings() { return countTrainings; }

    @Override
    public String toString() {
        return coach +
                " = " + countTrainings;
    }
}
