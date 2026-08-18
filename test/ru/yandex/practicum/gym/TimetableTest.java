package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.TreeMap;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay,ArrayList<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        //поскольку на одно время может быть несколько тренировок
            //создаем переменную для подсчета
        int countOfMonday = 0;

        //проходимя по хеш элементам в хеш таблице (получаем список тренировок)
        for(ArrayList<TrainingSession> trainingSessions : monday.values()) {
            countOfMonday += trainingSessions.size();
        }

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, countOfMonday);
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(tuesday);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay,ArrayList<TrainingSession>> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        //поскольку на одно время может быть несколько тренировок
        //создаем переменную для подсчета
        int countOfMonday = 0;

        //проходимя по хеш элементам в хеш таблице (получаем список тренировок)
        for(ArrayList<TrainingSession> trainingSessions : monday.values()) {
            countOfMonday += trainingSessions.size();
        }

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, countOfMonday);
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
            //получаем ключи все элементов хеш таблицы
        ArrayList<TimeOfDay> keys = new ArrayList<>(thursday.keySet());
        Assertions.assertEquals(new TimeOfDay(13, 0), keys.get(0));
        Assertions.assertEquals(new TimeOfDay(20, 0), keys.get(1));

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(tuesday);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        ArrayList<TrainingSession> monday13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(13,0));
        Assertions.assertEquals(1, monday13.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        ArrayList<TrainingSession> monday14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(14,0));
        Assertions.assertNull(monday14);
    }

    @Test
    void testGetCounterCoachTrainings() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Surname1","Namme1", "middleName1");
        Coach coach2 = new Coach("Surname2","Name2", "middleName2");
        Coach coach3 = new Coach("Surname3","Name3", "middleName3");

        Group group1 = new Group("Title1",Age.CHILD,90);

        //Проверить, что возращает пустой список если расписание пустое
        Assertions.assertEquals(0,timetable.getCountByCoaches().size());

        //если добавить несколько тренировок, первый должен быть coach2
        timetable.addNewTrainingSession(new TrainingSession(group1,coach1,DayOfWeek.MONDAY,new TimeOfDay(4,30)));
        timetable.addNewTrainingSession(new TrainingSession(group1,coach2,DayOfWeek.TUESDAY,new TimeOfDay(4,30)));
        timetable.addNewTrainingSession(new TrainingSession(group1,coach3,DayOfWeek.THURSDAY,new TimeOfDay(4,30)));
        timetable.addNewTrainingSession(new TrainingSession(group1,coach2,DayOfWeek.TUESDAY,new TimeOfDay(4,30)));

        Assertions.assertEquals(coach2,timetable.getCountByCoaches().get(0).getCoach());

        //если увеличиваем тренировки для coach1
        timetable.addNewTrainingSession(new TrainingSession(group1,coach1,DayOfWeek.WEDNESDAY,new TimeOfDay(4,30)));
        timetable.addNewTrainingSession(new TrainingSession(group1,coach1,DayOfWeek.FRIDAY,new TimeOfDay(4,30)));

        Assertions.assertEquals(coach1,timetable.getCountByCoaches().get(0).getCoach());
    }
}
