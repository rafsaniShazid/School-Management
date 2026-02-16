package school_management.controller;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import school_management.model.Student;
import school_management.repository.StudentRepository;
import school_management.service.StudentService;

/**
 * Minimal integration test
 * Tests Service + Repository + Database layers
 */
@SpringBootTest
@ActiveProfiles("test")
public class StudentControllerIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void testCreateStudent() {
        Student student = new Student(null, "John Doe", "john@test.com", "1234567890");
        
        Student created = studentService.createStudent(student);

        assertNotNull(created.getId());
        assertEquals("John Doe", created.getName());
    }

    @Test
    void testGetAllStudents() {
        studentRepository.save(new Student(null, "Alice", "alice@test.com", "111"));
        studentRepository.save(new Student(null, "Bob", "bob@test.com", "222"));

        List<Student> students = studentService.getAllStudents();

        assertEquals(2, students.size());
    }

    @Test
    void testGetStudentById() {
        Student saved = studentRepository.save(
                new Student(null, "Charlie", "charlie@test.com", "333"));

        Optional<Student> found = studentService.getStudentById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Charlie", found.get().getName());
    }

    @Test
    void testUpdateStudent() {
        Student saved = studentRepository.save(
                new Student(null, "Old Name", "old@test.com", "111"));

        Student updated = studentService.updateStudent(
                saved.getId(), 
                new Student(null, "New Name", "new@test.com", "222"));

        assertEquals("New Name", updated.getName());
        assertEquals("new@test.com", updated.getEmail());
    }

    @Test
    void testDeleteStudent() {
        Student saved = studentRepository.save(
                new Student(null, "To Delete", "delete@test.com", "999"));

        studentService.deleteStudent(saved.getId());

        Optional<Student> found = studentRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}
