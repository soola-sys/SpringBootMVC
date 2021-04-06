package kz.iitu.dao;

import kz.iitu.model.Student;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentsDao {
    private static Connection connection;

    public StudentsDao() {

    }
    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test1" ,
                    "root","root");
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }

    public List<Student> list() throws SQLException {
        PreparedStatement insert_query = connection.prepareStatement("SELECT  * FROM student");
        ResultSet resultSet = insert_query.executeQuery();
        List<Student> students = new ArrayList<>();
        while(resultSet.next()){
            int a =  resultSet.getInt("id");
            String s = resultSet.getString("name");
            System.out.println(a + s);
            students.add(new Student(a, s));
        }
       return students;
    }

    public Student search(int id) throws SQLException {
        List<Student> student = new ArrayList<>();
        PreparedStatement insert_query = connection.prepareStatement("SELECT * FROM student where id = ?");
         insert_query.setString(1,"name");
       // insert_query.executeQuery();
        ResultSet resultSet = insert_query.executeQuery();
        while(resultSet.next()){
            int a =  resultSet.getInt(id);
            String s = resultSet.getString("name");
            System.out.println(a + s);
            student.add(new Student(a, s));
        }
        return (Student) student;
    }

    public void create(Student newStudent) throws SQLException {
        PreparedStatement insert_query = connection.prepareStatement("INSERT Into student(id , name) values (? , ?)");
        insert_query.setInt(1,newStudent.getId());
        insert_query.setString(2,"name");
        insert_query.executeUpdate();

    }

    public void update(int id, Student updated_student) throws SQLException {
        PreparedStatement insert_query = connection.prepareStatement("UPDATE student SET name = ? where id = ?");
        insert_query.setInt(1,id);
        insert_query.setString(2,updated_student.getName());
        insert_query.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement insert_query = connection.prepareStatement("DELETE from student where id = ?");
        insert_query.setInt(1,id);
        insert_query.executeUpdate();

    }
}
