package org.skypro.be.employees.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<Storage> {

    int getNextId();

    Storage save(Storage entity);

    Storage delete(Storage entity);

    Collection<Storage> findAll();

    Optional<Storage> findById(int id);

    Optional<Storage> findByFirstNameAndLastName(String firstName, String lastName);
}
