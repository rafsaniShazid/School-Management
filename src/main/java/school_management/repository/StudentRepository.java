package school_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_management.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // JpaRepository provides all basic CRUD operations:
    // - save(Student student)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // - delete(Student student)
    // - count()
    // - existsById(Long id)
    
    // You can add custom query methods here if needed
    // For example:
    // Optional<Student> findByEmail(String email);
    // List<Student> findByNameContaining(String name);
}
