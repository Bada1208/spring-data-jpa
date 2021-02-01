package com.example.demo;


import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(15, 80)
            );
            student.addBook(new Book(LocalDateTime.now().minusDays(4), "Clean code"));
            student.addBook(new Book(LocalDateTime.now(), "Shildt"));
            student.addBook(new Book(LocalDateTime.now().minusYears(2), "Java 8+"));
            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
            student.setStudentIdCard(studentIdCard);

            student.enrollToCourse(new Course("Computer Science", "IT"));
            student.enrollToCourse(new Course("Spring Data Jpa", "IT"));

            studentRepository.save(student);

            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println("fetch book lazy...");
                List<Book> books = student.getBooks();
                books.forEach(book ->
                        System.out.println(s.getFirstName() + " borrowed " + book.getBookName()));

            });

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
