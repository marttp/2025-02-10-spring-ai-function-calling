package dev.tpcoder.application.repository;

import dev.tpcoder.application.entity.UserPreferences;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesRepository extends ListCrudRepository<UserPreferences, Integer> {
}
