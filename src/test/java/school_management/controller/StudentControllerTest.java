package school_management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import school_management.model.Student;
import school_management.service.StudentService;

public class StudentControllerTest {

    // Simple mock service for testing
    class MockStudentService extends StudentService {
        private List<Student> students = new ArrayList<>();
        private Long nextId = 1L;

        public MockStudentService() {
            super(null);
        }

        @Override
        public Student createStudent(Student student) {
            student.setId(nextId++);
            students.add(student);
            return student;
        }

        @Override
        public List<Student> getAllStudents() {
            return new ArrayList<>(students);
        }

        @Override
        public Optional<Student> getStudentById(Long id) {
            return students.stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst();
        }

        @Override
        public Student updateStudent(Long id, Student studentDetails) {
            Student student = getStudentById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setPhone(studentDetails.getPhone());
            return student;
        }

        @Override
        public void deleteStudent(Long id) {
            students.removeIf(s -> s.getId().equals(id));
        }
    }

    @Test
    void testCreateStudent() {
        // Setup
        MockStudentService mockService = new MockStudentService();
        StudentController controller = new StudentController(mockService);
        Student newStudent = new Student(null, "John Doe", "john@example.com", "1234567890");

        // Execute
        ResponseEntity<Student> response = controller.createStudent(newStudent);

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john@example.com", response.getBody().getEmail());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void testGetAllStudents() {
        // Setup
        MockStudentService mockService = new MockStudentService();
        StudentController controller = new StudentController(mockService);
        mockService.createStudent(new Student(null, "Alice", "alice@example.com", "1111111111"));
        mockService.createStudent(new Student(null, "Bob", "bob@example.com", "2222222222"));

        // Execute
        ResponseEntity<List<Student>> response = controller.getAllStudents();

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetStudentById() {
        // Setup
        MockStudentService mockService = new MockStudentService();
        StudentController controller = new StudentController(mockService);
        Student created = mockService.createStudent(new Student(null, "Charlie", "charlie@example.com", "3333333333"));

        // Execute
        ResponseEntity<Student> response = controller.getStudentById(created.getId());

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Charlie", response.getBody().getName());
        assertEquals(created.getId(), response.getBody().getId());
    }

    @Test
    void testGetStudentById_NotFound() {
        // Setup
        MockStudentService mockService = new MockStudentService();
        StudentController controller = new StudentController(mockService);

        // Execute
        ResponseEntity<Student> response = controller.getStudentById(999L);

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateStudent() {
        // Setup
        MockStudentService mockService = new MockStudentService();
        StudentController controller = new StudentController(mockService);
        Student existing = mockService.createStudent(new Student(null, "Dave", "dave@example.com", "4444444444"));
        Student updates = new Student(null, "David Updated", "david@example.com", "5555555555");

        // Execute
        ResponseEntity<Student> response = controller.updateStudent(existing.getId(), updates);

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("David Updated", response.getBody().getName());
        assertEquals("david@example.com", response.getBody().getEmail());
        assertEquals("5555555555", response.getBody().getPhone());
    }

    @Test
    void testUpdateStudent_NotFound() {
        // Setup
        MockStudentService mockService = new MockStudentService();
        StudentController controller = new StudentController(mockService);
        Student updates = new Student(null, "Nonexistent", "none@example.com", "0000000000");

        // Execute
        ResponseEntity<Student> response = controller.updateStudent(999L, updates);

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteStudent() {
        // Setup
        MockStudentService mockService = new MockStudentService();
        StudentController controller = new StudentController(mockService);
        Student created = mockService.createStudent(new Student(null, "Eve", "eve@example.com", "6666666666"));

        // Execute
        ResponseEntity<Void> response = controller.deleteStudent(created.getId());

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, mockService.getAllStudents().size());
    }
}
