package com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student maria = new Student("Maria", "Jones", "maria@gmail.com", 23);
            Student ali = new Student("Ali", "Jones", "ali@gmail.com", 18);
            studentRepository.saveAll(List.of(maria, ali));
            studentRepository.findById(2L).ifPresentOrElse(System.out::println, () -> {
                System.out.println("Student with ID 2 not found");
            });
            List<Student> all = studentRepository.findAll();
            all.forEach(System.out::println);
            studentRepository.deleteById(1L);
            System.out.println(studentRepository.count());
        };
    }

}
