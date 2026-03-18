package com.pao.laboratory03.exercise.model;

import java.util.HashMap;
import java.util.Map;
import com.pao.laboratory03.exercise.exception.*;

public class Student
{
    private String name;
    private int age;
    private Map<Subject, Double> grades;

    public Student(String name, int age)
    {
        this.name = name;
        this.age = age;
        grades = new HashMap<Subject, Double>();
        if(age < 18 || age > 60)
        {
            throw new InvalidStudentException("student invalid");
        }
    }

    public String getName()
    {
        return name;
    }
    public int getAge()
    {
        return age;
    }
    public Map<Subject, Double> getGrades()
    {
        return grades;
    }

    public void addGrade(Subject subject, double grade)
    {
        if(grade < 1 || grade > 10)
        {
            throw new InvalidGradeException("nota invalida");
        }
        grades.put(subject, grade);
    }

    public double getAverage()
    {
        double suma = 0;
        int nrNote = 0;
        for(double nota : grades.values())
        {
            suma += nota;
            nrNote += 1;
        }
        if(nrNote == 0)
        {
            return 0;
        }
        return suma / nrNote;
    }

    public String toString()
    {
        return "Student{name='" + name + "', age=" + age + ", avg=" + getAverage() + "}";
    }
}
