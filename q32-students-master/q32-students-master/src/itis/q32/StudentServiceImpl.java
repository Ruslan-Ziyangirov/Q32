package itis.q32;

import itis.q32.entities.Group;
import itis.q32.entities.Student;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {


    @Override
    public Group getSmallestGroup(List<Student> students) {

        Optional<Map.Entry<Group, Long>> smallestGroup = students.stream()
                .map(Student::getGroup)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue());


        return smallestGroup.map(Map.Entry::getKey).orElse(null);

    }

    @Override
    public Integer countGroupsWithOnlyAdultStudents(List<Student> students) {

        LocalDate dataNow = LocalDate.now();
        long result = students.stream()
                .filter(student -> dataNow.getYear() - student.getBirthdayDate().getYear() >= 18)
                .map(Student::getGroup)
                .distinct()
                .count();
        return Integer.parseInt(String.valueOf(result));
    }

    @Override
    public Map<String, Integer> getGroupScoreSumMap(List<Student> students, String studentSurname) {
        return null;
    }

    @Override
    public Map<Boolean, Map<String, Integer>> groupStudentScoreWithThreshold(List<Student> students, Integer threshold) {
        return null;
    }
}
