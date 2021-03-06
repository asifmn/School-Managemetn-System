package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.HibernateException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {

   EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");

    /**
     * This method reads the student table in your database and returns the data as a List<Student>
     */

    @Override
    public List<Student> getAllStudents(){
        List<Student> allStudents = null;
        EntityManager entityManager = emf.createEntityManager();
        Query query = entityManager.createNamedQuery("SELECT  * FROM Course c ");
        allStudents = query.getResultList();
        entityManager.close();
        return allStudents;
    }


    /**
     * This method takes a Student’s email as a String and parses the student list for a Student
     *     with that email and returns a Student Object
     */

    @Override
    public  Student getStudentByEmail(String sEmail){
        Student student = null;
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("from Student s WHERE s.sEmail = :email ");
            query.setParameter("email", sEmail);
            student = (Student) query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            //close em
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return student;
    }

    /**
     *This method takes two parameters: the first one is the user email and
     *     the second one is the password from the user input. Return whether or not a student was found
     */
    @Override
    public boolean validateStudent(String sEmail, String sPassword) {
        Student student = getStudentByEmail(sEmail);
        if (student != null) {
            return student.getSPass().equals(sPassword);
        }
        return false;
    }

/*After a successful student validation, this method takes a Student’s email and a Course ID.
 It checks in the join table (i.e. Student_Course) generated by JPA to find if a Student
   with that Email is currently attending a Course with that ID.
   If the Student is not attending that Course, register the student to that course; otherwise not.*/

    @Override
    public void registerStudentToCourse(String sEmail, int cID){
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // find student object by Email
            Student student = entityManager.find(Student.class, sEmail);

            //get all courses related to student
            List<Course> allCourses = getStudentCourses(sEmail);

            //  all courses
            List<Course> courseList = new ArrayList<Course>();
            courseList.addAll(allCourses);

            //adding new course to the course list
            courseList.add(new CourseService().getCourseById(cID));

            //persist to database
            if (courseList.size() > 0) {
                student.setSCourses(courseList);
                entityManager.persist(student);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    /*This method takes a Student’s Email as a parameter and would find all the courses a student is registered.*/
    @Override
    public List<Course>  getStudentCourses(String sEmail){
        List<Course> allCourses = null;
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("Select c.* from Course c JOIN student_course sc on c.id = sc.course_id WHERE sc.student_email = :email", Course.class);
            query.setParameter("email", sEmail);
            allCourses = query.getResultList();
        } catch (Exception e) {

        } finally {
            entityManager.close();
        }
        return allCourses;
    }

}