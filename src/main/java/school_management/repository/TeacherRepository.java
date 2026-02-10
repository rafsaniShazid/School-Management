package school_management.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import school_management.model.Teacher;

@Repository
public class TeacherRepository {
    private final Map<Long, Teacher> teachers = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Teacher save(Teacher teacher) {
        if (teacher.getId() == null) {
            teacher.setId(idCounter.getAndIncrement());
        }
        teachers.put(teacher.getId(), teacher);
        return teacher;
    }

    public Optional<Teacher> findById(Long id) {
        return Optional.ofNullable(teachers.get(id));
    }

    public List<Teacher> findAll() {
        return new ArrayList<>(teachers.values());
    }

    public void deleteById(Long id) {
        teachers.remove(id);
    }
}
