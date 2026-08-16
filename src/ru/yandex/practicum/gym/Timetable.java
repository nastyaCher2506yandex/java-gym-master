package ru.yandex.practicum.gym;

import java.awt.*;
import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    Timetable() {
        timetable = new HashMap<>();
    }

    public HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> getListTimetable() {
        return timetable;
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if(dayTimetable == null) {
            dayTimetable = new TreeMap<>();
            dayTimetable.put(timeOfDay,new ArrayList<>());
            timetable.put(dayOfWeek,dayTimetable);
        }
        ArrayList<TrainingSession> trainingSessions = dayTimetable.get(timeOfDay);
        if(trainingSessions == null) {
            trainingSessions = new ArrayList<>();
            dayTimetable.put(timeOfDay, trainingSessions);
        }
        trainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay,ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //сложность должна быть О(1), метод возращает все расписание в определеный день
        //для избегания ошибки NullPointerException, возвращает пустую таблицу
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //сложность должна быть О(1), метод возращает какие тренировке будут в определеный день недели и час
        if(timetable.get(dayOfWeek) != null) {
            return timetable.get(dayOfWeek).get(timeOfDay);
        }
        return null;
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach,Integer> coachesCounter = new HashMap<>();
        for(TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTimetable : timetable.values()) {
            for(ArrayList<TrainingSession> trainingSessions : dayTimetable.values()) {
                for(TrainingSession training : trainingSessions) {
                    Coach coach = training.getCoach();
                    Integer counter = (coachesCounter.getOrDefault(coach, 0)) + 1;
                    coachesCounter.put(coach,counter);
                }
            }
        }

        ArrayList<CounterOfTrainings> counterOfTrainings = new ArrayList<>();

        for(Map.Entry<Coach,Integer> entry : coachesCounter.entrySet()) {
            counterOfTrainings.add(new CounterOfTrainings(entry.getKey(),entry.getValue()));
        }

        CounterOfTrainingsCompatator counterOfTrainingsCompatator = new CounterOfTrainingsCompatator();
        counterOfTrainings.sort(counterOfTrainingsCompatator);

        return counterOfTrainings;
    }

}
