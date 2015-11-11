package com.tjazi.profiles.service.core;

import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseStatus;
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
    public RegisterNewProfileResponseMessage registerNewProfile(RegisterNewProfileRequestMessage requestMessage) {

        if (requestMessage == null) {
            throw new IllegalArgumentException("requestMessage is null.");
        }

        RegisterNewProfileResponseMessage responseMessage = new RegisterNewProfileResponseMessage();

        try {

            UUID newProfileUuid = UUID.randomUUID();
            ProfileDataDAOModel daoModel = this.generateDaoModelBasedOnRequestMessage(requestMessage, newProfileUuid);

            RegisterNewProfileResponseStatus duplicationStatus = this.isDuplicated(daoModel);

            responseMessage.setRegisterNewProfileResponseStatus(duplicationStatus);

            // save if duplication is not detected
            if (duplicationStatus == RegisterNewProfileResponseStatus.OK) {

                profileDAO.save(daoModel);

                // set new UUID in the response ONLY if there's not duplicated record already
                responseMessage.setNewProfileUuid(newProfileUuid);
            }

        } catch (Exception ex){
            log.error("Got exception in ProfilesRegistrationManagerImpl::registerNewProfile", ex);

            responseMessage.setRegisterNewProfileResponseStatus(RegisterNewProfileResponseStatus.GENERAL_REGISTRATION_ERROR);
        }

        return responseMessage;
    }

    private ProfileDataDAOModel generateDaoModelBasedOnRequestMessage(
            RegisterNewProfileRequestMessage requestMessage, UUID newProfileUuid){

        ProfileDataDAOModel daoModel = RegisterProfileMessage2DaoConverter.convertMessageToModel(requestMessage);

        // set missing fields
        daoModel.setProfileUuid(newProfileUuid);

        return daoModel;
    }

    private RegisterNewProfileResponseStatus isDuplicated(ProfileDataDAOModel profileDataDAOModel) {

        if (profileDataDAOModel == null) {
            throw new IllegalArgumentException("profileDataDAOModel is null");
        }

        String userName = profileDataDAOModel.getUserName();
        String email = profileDataDAOModel.getUserEmail();

        log.debug("Checking for duplicated profile data in database. User name: '{}', email: '{}'.", userName, email);

        List<ProfileDataDAOModel> daoRecordsByUserNameOrEmail = profileDAO.findByUserNameOrUserEmail(userName, email);

        if (daoRecordsByUserNameOrEmail != null && daoRecordsByUserNameOrEmail.size() > 0) {
            if (daoRecordsByUserNameOrEmail.get(0).getUserEmail().equalsIgnoreCase(email)) {

                log.debug("Found duplicated user profile record for user name: {}", userName);
                return RegisterNewProfileResponseStatus.USER_EMAIL_ALREADY_REGISTERED_WITH_DIFFERENT_USER;
            }
            else {
                log.debug("Found duplicated user profile record for email: {}", email);
                return RegisterNewProfileResponseStatus.USER_NAME_ALREADY_REGISTERED;
            }
        }
        else
        {
            return RegisterNewProfileResponseStatus.OK;
        }
    }
}
