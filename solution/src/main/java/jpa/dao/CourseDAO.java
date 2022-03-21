package jpa.dao;

import jpa.entitymodels.Course;

import java.util.List;

public interface CourseDAO {
    /**
     *  This method takes no parameter and returns every Course in the table.
     */
    List<Course> getAllCourses();
    Course getCourseById(int id);
}
