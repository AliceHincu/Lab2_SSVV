package ssv.example;

import domain.Student;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.function.Consumer;


public class AppTest extends TestCase {
    public AppTest(String testName) {
        super(testName);
    }

    private Service getService() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        return new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    private void assertThrows(Runnable runnable) {
        try {
            runnable.run();
            assert(false);
        } catch (Exception e) {}
    }

    public void testValidStudent() {
        final Service service = getService();
        final Student student = new Student("1", "Andrei", 933, "a@example.com");
        service.addStudent(student);
    }

    public void testInvalidStudent() {
        final Service service = getService();
        final Student student = new Student("", "Andrei", 933, "a@example.com");
        assertThrows(() -> service.addStudent(student));
    }

    public void testValidId() {
        final Student student = new Student("1", "Andrei", 933, "a@example.com");
        final Service service = getService();
        service.addStudent(student);
    }

    public void testEmptyId() {
        final Student student = new Student("", "Andrei", 933, "a@example.com");
        final Service service = getService();
        assertThrows(() -> service.addStudent(student));
    }

    public void testNullId() {
        final Student student = new Student(null, "Andrei", 933, "a@example.com");
        final Service service = getService();
        assertThrows(() -> service.addStudent(student));
    }

    public void testValidNameContents() {
        final Student student = new Student("A", "Test test-test", 933, "a@example.com");
        final Service service = getService();
        service.addStudent(student);
    }

    public void testInvalidNameCharacters() {
        final Student student = new Student("A", "Test+++---", 933, "a@example.com");
        final Service service = getService();
        assertThrows(() -> service.addStudent(student));
    }

    public void testEmptyName() {
        final Student student = new Student("A", "", 933, "a@example.com");
        final Service service = getService();
        assertThrows(() -> service.addStudent(student));
    }

    public void testValidGroup() {
        final Student student = new Student("A", "Andrei", 933, "a@example.com");
        final Service service = getService();
        service.addStudent(student);
    }

    public void testInvalidGroupLess() {
        final Student student = new Student("A", "Andrei", -1, "a@example.com");
        final Service service = getService();
        assertThrows(() -> service.addStudent(student));
    }

    public void testInvalidGroupGreater() {
        @SuppressWarnings("NumericOverflow")
        final Student student = new Student("A", "Andrei", Integer.MAX_VALUE + 1, "a@example.com");
        final Service service = getService();
        assertThrows(() -> service.addStudent(student));
    }

    public void testValidEmail() {
        final Student student = new Student("A", "Andrei", 933, "a@example.com");
        final Service service = getService();
        service.addStudent(student);
    }

    public void testInvalidEmail() {
        final Student student = new Student("A", "Andrei", 933, "com");
        final Service service = getService();
        assertThrows(() -> service.addStudent(student));
    }
}
