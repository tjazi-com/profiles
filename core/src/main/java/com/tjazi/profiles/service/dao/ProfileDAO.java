package com.tjazi.profiles.service.dao;

import com.tjazi.profiles.service.dao.model.ProfileDataDAOModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */
public interface ProfileDAO extends CrudRepository<ProfileDataDAOModel, Long> {

    // all save methods are added via CrudRepository

    /**
     * Get list of profiles matching user name OR email
     * @param userName User name
     * @param email User email
     * @return
     */
    List<ProfileDataDAOModel> findByUserNameOrUserEmail(String userName, String email);

    /**
     * Find user profile by its UUID
     * @param userUuid
     * @return
     */
    List<ProfileDataDAOModel> findByProfileUuid(UUID userUuid);
}
