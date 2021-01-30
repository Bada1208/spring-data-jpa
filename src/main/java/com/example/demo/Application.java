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
            System.out.println(studentRepository.findStudentByEmail("maria@gmail.com"));
            studentRepository.findStudentByFirstName("Ali")
                    .ifPresentOrElse(System.out::println, () -> {
                        System.out.println("student with name ali not fount");
                    });
        };
    }

}
