package social_network.domain.validator;

import social_network.exceptions.ValidationException;

/**
 * User validator interface
 *
 * @param <T> - entity type
 */
public interface Validator<T> {
    /**
     * Method that verifies if a user is valid
     *
     * @param entity - the entity that will be validated
     * @throws ValidationException - if the entity is not valid
     */
    void validateID(T entity) throws ValidationException;

    void validateFirstName(T entity) throws ValidationException;

    void validateLastName(T entity) throws ValidationException;

    void validateAge(T entity) throws ValidationException;

    //void validateFriends(T entity1, T entity2) throws ValidationException;

    
}
