package validation;

import domain.Student;

import java.util.regex.Pattern;

public class StudentValidator implements Validator<Student> {

    /**
     * Valideaza un student
     * @param entity - studentul pe care il valideaza
     * @throws ValidationException - daca studentul nu e valid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if(entity.getID().equals("")){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getID() == null){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getNume().equals("")){
            throw new ValidationException("Nume incorect!");
        }
        if(entity.getGrupa() < 0) {
            throw new ValidationException("Grupa incorecta!");
        }
        if(entity.getEmail() == null){
            throw new ValidationException("Email incorect!");
        }
        if(entity.getNume() == null){
            throw new ValidationException("Nume incorect!");
        }
        final Pattern namePattern = Pattern.compile("^[a-zA-Z\\- ]+$");
        if (!namePattern.matcher(entity.getNume()).find()) {
            throw new ValidationException("Numele contine caractere invalide!");
        }
        final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$");
        if (!emailPattern.matcher(entity.getEmail()).find()) {
            throw new ValidationException("Numele contine caractere invalide!");
        }
        if(entity.getEmail().equals("")){
            throw new ValidationException("Email incorect!");
        }
    }
}
