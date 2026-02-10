package school_management.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import school_management.model.Student;

@Repository
public class StudentRepository {
    private final Map<Long, Student> students = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idCounter.getAndIncrement());
        }
        students.put(student.getId(), student);
        return student;
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public void deleteById(Long id) {
        students.remove(id);
    }
}
