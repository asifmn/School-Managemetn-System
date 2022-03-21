package jpa.mainrunner;

import static java.lang.System.out;
import java.util.*;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;


public class SMSRunner {

    private Scanner sin;
    private StringBuilder sb;
    private CourseService courseService;
    private StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        sin = new Scanner(System.in);
        sb = new StringBuilder();
        courseService = new CourseService();
        studentService = new StudentService();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SMSRunner sms = new SMSRunner();
        sms.run();
    }

    private void run() {
        // Login or quit
        switch (menu1()) {
            case 1:
                if (studentLogin()) {
                    registerMenu();
                }
                break;
            case 2:
                out.println("Goodbye!");
                break;
            default:
        }
    }

    private int menu1() {
        sb.append("\n1.Student Login\n2. Quit Application\nPlease Enter Selection: ");
        out.print(sb.toString());
        sb.delete(0, sb.length());
        return sin.nextInt();
    }

    private boolean studentLogin() {
        boolean retValue = false;
        out.print("Enter your email address: ");
        String email = sin.next();
        out.print("Enter your password: ");
        String password = sin.next();
        Student student = studentService.getStudentByEmail(email);
        if (student != null) {
            currentStudent = student;
        }
        if (currentStudent != null & currentStudent.getSPass().equals(password)) {
            List<Course> courses = studentService.getStudentCourses(email);
            out.println("My Classes:");
            out.printf("%-6s%-37s%-25s\n", "#", "COURSE NAME", "INSTRUCTOR NAME");
            for (Course course : courses) {
                out.printf("%-6s%-37s%-25s\n", course.getCID(), course.getCName(), course.getCInstructorName());
            }
            retValue = true;
        } else {
            out.println("User Validation failed. GoodBye!");
        }
        return retValue;
    }

    private void registerMenu() {
        sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
        out.print(sb.toString());
        sb.delete(0, sb.length());

        switch (sin.nextInt()) {
            case 1:
                List<Course> allCourses = courseService.getAllCourses();
                List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getSEmail());
                allCourses.removeAll(studentCourses);
                out.println("All Courses: ");
                out.printf("%-6s%-37s%-25s\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
                for (Course course :  allCourses) {
                    out.printf("%-6s%-37s%-25s\n", course.getCID(), course.getCInstructorName(), course.getCName());
                }
                out.println();
                out.print("Enter Course Number: ");
                int number = sin.nextInt();
                Course newCourse = courseService.getCourseById(number);

                if (newCourse != null) {
                    studentService.registerStudentToCourse(currentStudent.getSEmail(), number);
                    Student temp = studentService.getStudentByEmail(currentStudent.getSEmail());
                    List<Course> sCourses = studentService.getStudentCourses(temp.getSEmail());

                    out.println("My Classes:");
                    out.printf("%-5S%-35S%-15S\n", "ID", "Course", "Instructor");
                    for (Course course : sCourses) {
                        out.printf("%-5s%-35s%-25s\n", course.getCID(), course.getCName(), course.getCInstructorName());
                    }
                }
                break;
            case 2:
            default:
                out.println("Goodbye!");
        }
    }
}