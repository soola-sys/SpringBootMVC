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
    public static int id = 0;
    private List<Student> students;

    public List<Student> list() {
     students = new ArrayList<>();
     try{
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM student");
       ResultSet  resultSet = statement.executeQuery();
       while(resultSet.next()){
           int i = resultSet.getInt("id");
           String s = resultSet.getString("name");
           System.out.println(i + s);
           students.add(new Student(i ,s));
       }
     } catch (SQLException throwables) {
         throwables.printStackTrace();
     }
     return students;
    }

    public Student search(int id)  {
        Student student = new Student();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM student where id = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                student.setId(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
            }
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return student;
    }

    public void create(Student newStudent) throws SQLException {
        PreparedStatement insert_query = connection.prepareStatement("INSERT INTO student (id , name) VALUES (? , ?)");
        insert_query.setInt(1,id++);
        insert_query.setString(2, newStudent.getName());
        insert_query.executeUpdate();

    }

    public void update(int id, Student updated_student) throws SQLException {
        PreparedStatement insert_query = connection.prepareStatement("UPDATE student SET name = ? where id = ?");
        insert_query.setString(1, updated_student.getName());
        insert_query.setInt(2,id);
        insert_query.executeUpdate();
    }

    public void delete(int id) throws SQLException {
     PreparedStatement statement = connection.prepareStatement("DELETE FROM student where id = ?");
     statement.setInt(1,id);
     statement.executeUpdate();
    }

}
