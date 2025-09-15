package com.practice.mcpserver;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentService.class);

    private List<Student> students = new ArrayList<>();

    @PostConstruct
    public void init() {
        students.addAll(List.of(
                new Student("Tom", "male", 25, "tom123@gmail.com"),
                new Student("Lisa", "female", 25, "lisa123@gmail.com"),
                new Student("Jack", "male", 25, "jack123@gmail.com")
        ));
    }

    @Tool(name = "getAllStudents", description = "获取所有的学生信息")
    public List<Student> getAllStudents() {
        return students;
    }

    @Tool(name = "getStudentByName", description = "根据名称获取学生信息")
    public List<Student> getStudent(String name) {
        return students;
    }

    @Tool(name = "addStudent", description = "新增一名学生")
    public Student addStudent(String name, String gender,int age, String email) {
        Student student = new Student(name, gender, age, email);
        students.add(student);
        return student;
    }

}