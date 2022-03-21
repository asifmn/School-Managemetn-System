package jpa.entitymodels;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Course")
public class Course {

    //Unique Course Identifier
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
   private int cID;

    //Provides the name of the course
    @Column(name = "name")
    private String cName;

    // Provides the name of the instructor
    @Column(name = "instructor")
    private String cInstructorName;

    /**
     * constructor takes no parameters and it initializes every member to an initial value
     */

    public Course() {
        this.cName = "";
        this.cInstructorName = "";
    }

    /**
     * constructor initializes every private member with a parameter provided to the constructor
     */

    public Course(String cName, String cInstructorName) {
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }
}