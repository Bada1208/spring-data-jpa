package com.example.demo;


import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            generateRandomStudents(studentRepository);
            studentRepository
                    .findAll(Sort.by(Sort.Direction.ASC, "firstName"))
                    .forEach(student -> System.out.println(student.getFirstName()));
            PageRequest pageRequest = PageRequest.of(
                    0,
                    5,
                    Sort.by("firstName").ascending());
            System.out.println("PageRequest sorting:");
            studentRepository.findAll(pageRequest).forEach(student -> System.out.println(student.getFirstName()));
        };
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(15, 80)
            );
            studentRepository.save(student);
        }
    }

}
