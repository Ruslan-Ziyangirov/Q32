package itis.q32;


/*
В папке resources находятся два .csv файла.
Один содержит данные о группах в университете в следующем формате: ID группы, название группы, код группы
Второй содержит данные о студентах: ФИО, дата рождения, айди группы, количество очков рейтинга

напишите код который превратит содержимое файлов в обьекты из пакета "entities", выведите в консоль всех студентов,
в читабельном виде, с информацией о группе
Используя StudentService, выведите:

1. Число групп с только совершеннолетними студентами
2. Самую маленькую группу
3. Отношение группа - сумма балов студентов фамилия которых совпадает с заданной строкой
4. Отношения студент - дельта баллов до проходного порога (порог передается параметром),
 сгруппированные по признаку пройден порог, или нет

Требования к реализации: все методы в StudentService должны быть реализованы с использованием StreamApi.
Использование обычных циклов и дополнительных переменных приведет к снижению баллов, но допустимо.
Парсинг файлов и реализация методов оцениваются ОТДЕЛЬНО
*/

import itis.q32.entities.Group;
import itis.q32.entities.Student;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MainClass {

    private StudentService studentService = new StudentServiceImpl();

    public static void main(String[] args) {
        new MainClass().run(
                "",
                "");
    }

    private void run(String studentsPath, String groupsPath) {

        ArrayList<Group> allGroup = new ArrayList<>();
        ArrayList<Student> studentArrayList = new ArrayList<>();

        FileReader fileReaderGroups = null;
        try {
            fileReaderGroups = new FileReader(groupsPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scannerGroups = new Scanner(fileReaderGroups);

        while (scannerGroups.hasNext()){

            Group group = new Group();

            String stringGroups = scannerGroups.nextLine();
            String[] columns  = stringGroups.split(", ");

            Long id = Long.parseLong(columns[0]);
            String title = columns[1];
            String code = columns[2];

            group.setId(id);
            group.setTitle(title);
            group.setCode(code);

            allGroup.add(group);
        }



        FileReader fileReaderStudents = null;
        try {
            fileReaderStudents = new FileReader(studentsPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scannerStudents = new Scanner(fileReaderStudents);

        while (scannerStudents.hasNext()){

            Student student = new Student();

            String stringStudent = scannerStudents.nextLine();
            String[] columns = stringStudent.split(", ");

            String fullName = columns[0];

            String[] columnsOfBirthday = columns[1].split("-");
            LocalDate localDate = LocalDate.of(Integer.parseInt(columnsOfBirthday[0]),Integer.parseInt(columnsOfBirthday[1]),
                    Integer.parseInt(columnsOfBirthday[2]));

            Long id = Long.parseLong(columns[2]);
            Integer score = Integer.parseInt(columns[3]);

            student.setFullName(fullName);
            student.setBirthdayDate(localDate);
            student.setScore(score);

            for (Group group : allGroup) {
                if (group.getId().equals(id)) {
                    student.setGroup(group);
                }
            }

            studentArrayList.add(student);
        }

        System.out.println(studentArrayList);
        System.out.println(studentService.getSmallestGroup(studentArrayList));
        System.out.println(studentService.countGroupsWithOnlyAdultStudents(studentArrayList));
    }

}










