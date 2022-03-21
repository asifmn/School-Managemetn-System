package jpa.entitymodels;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Student")
public class Student {
   //  student’s current school email, unique student identifier
    @Id
    @Column(name = "email")
    private String sEmail;

    // The full name of the student

    @Column(name = "name")
   private String sName;
    // Student’s password in order to log in
    @Column(name = "password")
    private String sPass;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = {@JoinColumn(name = "student_email", referencedColumnName = "email",unique = false)},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id",unique = false)},
            indexes = {@Index(columnList = "student_email,course_id")}
    )
    private List<Course> sCourses;


    /**
     * constructor takes no parameters and it initializes every member to an initial value
     */

    public Student() {
        this.sEmail = "";
        this.sName = "";
        this.sPass = "";
    }

    /**
     * Constructor initializes every private member with a parameter provided to the constructor
     */
    public Student(String sEmail, String sName, String sPass) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
    }

}

