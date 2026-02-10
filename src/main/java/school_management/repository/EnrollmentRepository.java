package school_management.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import school_management.model.Enrollment;

@Repository
public class EnrollmentRepository {
    private final Map<Long, Enrollment> enrollments = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Enrollment save(Enrollment enrollment) {
        if (enrollment.getId() == null) {
            enrollment.setId(idCounter.getAndIncrement());
        }
        enrollments.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    public Optional<Enrollment> findById(Long id) {
        return Optional.ofNullable(enrollments.get(id));
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments.values());
    }

    public List<Enrollment> findByStudentId(Long studentId) {
        return enrollments.values().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Enrollment> findByCourseId(Long courseId) {
        return enrollments.values().stream()
                .filter(e -> e.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        enrollments.remove(id);
    }
}
