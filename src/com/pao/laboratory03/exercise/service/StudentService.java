package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudentService
{
    private List<Student> students;

    private StudentService()
    {
        students = new ArrayList<Student>();
    }

    public void addStudent(String name, int age)
    {
        for(Student st : students)
        {
            if(Objects.equals(st.getName(), name))
            {
                throw new RuntimeException();
            }
        }
        Student nou = new Student(name, age);
        students.add(nou);
    }

    public Student findByName(String name)
    {
        for(Student st : students)
        {
            if(Objects.equals(st.getName(), name))
            {
                return st;
            }
        }
        throw new StudentNotFoundException("student negasit");
    }

    public void addGrade(String studentName, Subject subject, double grade)
    {
        Student s = findByName(studentName);
        s.addGrade(subject, grade);
    }

    public void printAllStudents()
    {
        for(Student st : students)
        {
            System.out.println(st.getName() + " " + st.getGrades());
        }
    }

    public void printTopStudents()
    {
        for(int i = 0; i < students.size() - 1; i++)
        {
            for(int j = i + 1; j < students.size(); j++)
            {
                if(students.get(i).getAverage() > students.get(j).getAverage())
                {
                    Student si = students.get(i);
                    Student sj = students.get(j);
                    Student aux = si;
                    si = sj;
                    sj = aux;
                }
            }
        }

        printAllStudents();
    }

    public Map<Subject, Double> getAveragePerSubject()
    {
        // completeaza asta, si partea cu bonus.
    }
}
