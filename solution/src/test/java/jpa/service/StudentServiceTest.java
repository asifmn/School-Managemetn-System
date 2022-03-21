package jpa.service;


import jpa.entitymodels.Student;
import org.junit.Test;
import org.junit.jupiter.api.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


class StudentServiceTest {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory( "SMS" );
    @BeforeEach
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterEach
    void tearDown() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    @DisplayName("checking student by e-mail")
    void checkStudentByEmail() {
        Student expectedStudent = new Student("sbowden1@yellowbook.com","Sonnnie Bowden", "SJc4aWSU");
        Student actualStudent = new StudentService().getStudentByEmail("sbowden1@yellowbook.com");
        Assertions.assertEquals(expectedStudent,actualStudent);
    }
}