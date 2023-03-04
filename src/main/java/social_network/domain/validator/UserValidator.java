package social_network.domain.validator;

import social_network.domain.User;
import social_network.exceptions.ValidationException;

/**
 * Class representing user validator
 */
public class UserValidator implements Validator<User> {
 

    /**
     * Method that verifies if a user is valid
     *
     * @param entity - the entity that will be validated
     * @throws ValidationException - if the entity is not valid
     */
    @Override
    public void validateID(User entity) throws ValidationException {
        String errors = "";
        if (entity.getId() < 0) {
            errors += "Id must be a positive number!\n";
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors);
        }
    }

    /**
     * Method that validates a user's first name
     *
     * @param entity - the user we validate
     * @throws ValidationException - in case of wrong input of first name
     */
    @Override
    public void validateFirstName(User entity) throws ValidationException {
        String errors = "";

        boolean b1 = Character.isUpperCase(entity.getFirstName().charAt(0));
        if (!b1) {
            errors += "First name must start with capital letter!\n";
        }

        if (entity.getFirstName().length() < 3) {
            errors += "First name must have at least 3 letters!\n";
        }

        boolean allLetters1 = entity.getFirstName().chars().allMatch(Character::isLetter);
        if (!allLetters1) {
            errors += "First name must not contain digits or spaces!\n";
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors);
        }
    }

    /**
     * Method that validates a user's last name
     *
     * @param entity - the user we validate
     * @throws ValidationException - in case of wrong input of last name
     */
    @Override
    public void validateLastName(User entity) throws ValidationException {
        String errors = "";

        boolean b2 = Character.isUpperCase(entity.getLastName().charAt(0));
        if (!b2) {
            errors += "Last name must start with capital letter!\n";
        }
        if (entity.getLastName().length() < 3) {
            errors += "Last name must have at least 3 letters!\n";
        }
        boolean allLetters2 = entity.getLastName().chars().allMatch(Character::isLetter);
        if (!allLetters2) {
            errors += "First name must not contain digits or spaces!\n";
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateAge(User entity) throws ValidationException {
        String errors = "";
        if (entity.getAge() <= 0) {
            errors += "Age must be a number greater than 0!\n";
        }
        boolean onlyDigits = entity.getAge().toString().matches("[0-9]+");
        if (!onlyDigits) {
            errors += "Age must contain only digits!\n";
        }
        if (errors.length() > 0) {
            throw new ValidationException(errors);
        }
    }


}
