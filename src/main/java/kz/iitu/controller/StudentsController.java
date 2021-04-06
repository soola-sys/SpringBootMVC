package kz.iitu.controller;

import kz.iitu.dao.StudentsDao;
import kz.iitu.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

//localhost:8080/students/2/edit

@Controller
@RequestMapping("/students")
public class StudentsController {

    private StudentsDao studentsDao;

    @Autowired
    public StudentsController(StudentsDao studentsDao) {
        this.studentsDao = studentsDao;
    }

    @GetMapping()
    public String list(Model model) throws SQLException {
        model.addAttribute("students", studentsDao.list());
        return "page1";
    }

    @GetMapping("/{id}")
    public String search(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("student", studentsDao.search(id));
        return "page2";
    }

    @GetMapping("/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("student") Student student) throws SQLException {
        studentsDao.create(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {
        model.addAttribute("student", studentsDao.search(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("student") Student student,
                         @PathVariable("id") int id) throws SQLException {
        studentsDao.update(id, student);
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        studentsDao.delete(id);
        return "redirect:/students";
    }
}
