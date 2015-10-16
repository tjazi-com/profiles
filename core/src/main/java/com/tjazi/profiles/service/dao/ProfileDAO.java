package com.tjazi.profiles.service.dao;

import com.tjazi.profiles.service.dao.model.ProfileDataDAOModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */
public interface ProfileDAO extends CrudRepository<ProfileDataDAOModel, Long> {

    // all save methods are added via CrudRepository

    List<ProfileDataDAOModel> findByUserNameOrEmail(String userName, String email);
}
