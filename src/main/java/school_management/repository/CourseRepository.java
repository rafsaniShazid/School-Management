package school_management.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import school_management.model.Course;

@Repository
public class CourseRepository {
    private final Map<Long, Course> courses = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Course save(Course course) {
        if (course.getId() == null) {
            course.setId(idCounter.getAndIncrement());
        }
        courses.put(course.getId(), course);
        return course;
    }

    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(courses.get(id));
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    public void deleteById(Long id) {
        courses.remove(id);
    }
}
