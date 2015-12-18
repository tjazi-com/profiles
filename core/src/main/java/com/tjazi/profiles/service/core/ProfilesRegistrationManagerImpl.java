package com.tjazi.profiles.service.core;

import com.tjazi.profiles.messages.RegisterNewProfileRequestCommand;
import com.tjazi.profiles.service.converters.RegisterProfileMessage2DaoConverter;
import com.tjazi.profiles.service.dao.ProfileDAO;
import com.tjazi.profiles.service.dao.model.ProfileDataDAOModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */

@Service
public class ProfilesRegistrationManagerImpl implements ProfilesRegistrationManager {

    @Autowired
    private ProfileDAO profileDAO;

    private final static Logger log = LoggerFactory.getLogger(ProfilesRegistrationManagerImpl.class);

    @Override
    public boolean registerNewProfile(RegisterNewProfileRequestCommand requestMessage) {

        if (requestMessage == null) {
            throw new IllegalArgumentException("requestMessage is null");
        }

        ProfileDataDAOModel daoModel = this.generateDaoModelBasedOnRequestMessage(requestMessage);

        if (this.isDuplicated(daoModel)) {
            log.error("Profile with user name {} already exists.", requestMessage.getUserName());
        }

        profileDAO.save(daoModel);
        return true;
    }

    private ProfileDataDAOModel generateDaoModelBasedOnRequestMessage(
            RegisterNewProfileRequestCommand requestMessage){

        ProfileDataDAOModel daoModel = RegisterProfileMessage2DaoConverter.convertMessageToModel(requestMessage);

        return daoModel;
    }

    private boolean isDuplicated(ProfileDataDAOModel profileDataDAOModel) {

        if (profileDataDAOModel == null) {
            throw new IllegalArgumentException("profileDataDAOModel is null");
        }

        String userName = profileDataDAOModel.getUserName();
        String email = profileDataDAOModel.getUserEmail();

        log.debug("Checking for duplicated profile data in database. User name: '{}', email: '{}'.", userName, email);

        List<ProfileDataDAOModel> daoRecordsByUserNameOrEmail = profileDAO.findByUserNameOrUserEmail(userName, email);

        return (daoRecordsByUserNameOrEmail != null && daoRecordsByUserNameOrEmail.size() > 0);
    }
}
