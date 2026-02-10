package school_management.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import school_management.model.Department;

@Repository
public class DepartmentRepository {
    private final Map<Long, Department> departments = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Department save(Department department) {
        if (department.getId() == null) {
            department.setId(idCounter.getAndIncrement());
        }
        departments.put(department.getId(), department);
        return department;
    }

    public Optional<Department> findById(Long id) {
        return Optional.ofNullable(departments.get(id));
    }

    public List<Department> findAll() {
        return new ArrayList<>(departments.values());
    }

    public void deleteById(Long id) {
        departments.remove(id);
    }
}
