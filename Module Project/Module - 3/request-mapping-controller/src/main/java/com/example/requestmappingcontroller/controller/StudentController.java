package com.example.requestmappingcontroller.controller;

import com.example.requestmappingcontroller.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final Map<Long, Student> studentDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(100);

    public StudentController() {
        // Sample seed data
        long id1 = idGenerator.incrementAndGet();
        studentDatabase.put(id1, new Student(id1, "Mayank Upadhyay", "RA2026CSE001", "CSE Core", "mayankupadhayay2020115@gmail.com"));

        long id2 = idGenerator.incrementAndGet();
        studentDatabase.put(id2, new Student(id2, "Rahul Sharma", "RA2026CSE002", "CSE Core", "rahul.sharma@example.com"));

        long id3 = idGenerator.incrementAndGet();
        studentDatabase.put(id3, new Student(id3, "Priya Nair", "RA2026CSE003", "CSE Core", "priya.nair@example.com"));
    }

    // 1. GET ALL STUDENTS: GET /api/students
    @GetMapping
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentDatabase.values());
    }

    // 2. GET BY ID: GET /api/students/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentDatabase.get(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 3. SEARCH BY NAME OR DEPARTMENT: GET /api/students/search?department=CSE Core
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String name) {

        List<Student> results = new ArrayList<>();
        for (Student s : studentDatabase.values()) {
            boolean match = true;
            if (department != null && !s.getDepartment().equalsIgnoreCase(department.trim())) {
                match = false;
            }
            if (name != null && !s.getName().toLowerCase().contains(name.toLowerCase().trim())) {
                match = false;
            }
            if (match) {
                results.add(s);
            }
        }
        return results;
    }

    // 4. CREATE NEW STUDENT: POST /api/students
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Long newId = idGenerator.incrementAndGet();
        student.setId(newId);
        studentDatabase.put(newId, student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    // 5. UPDATE EXISTING STUDENT: PUT /api/students/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        if (!studentDatabase.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedStudent.setId(id);
        studentDatabase.put(id, updatedStudent);
        return ResponseEntity.ok(updatedStudent);
    }

    // 6. DELETE STUDENT: DELETE /api/students/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        Student removed = studentDatabase.remove(id);
        if (removed != null) {
            return ResponseEntity.ok("Student with ID " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with ID " + id + " not found.");
    }
}
