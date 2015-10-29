package com.tjazi.profiles.service.core;

import com.tjazi.profiles.messages.GetProfileDetailsRequestMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseStatus;
import com.tjazi.profiles.service.dao.ProfileDAO;
import com.tjazi.profiles.service.dao.model.ProfileDataDAOModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 28/10/2015.
 */
@Service
public class ProfilesInformationProviderImpl implements ProfilesInformationProvider {

    private Logger log = LoggerFactory.getLogger(ProfilesInformationProviderImpl.class);

    @Autowired
    private ProfileDAO profileDAO;

    @Override
    public GetProfileDetailsResponseMessage getProfileDetails(final GetProfileDetailsRequestMessage requestMessage) {

        log.debug("in getProfileDetails");

        if (requestMessage == null) {
            String error = "requestMessage is null";
            log.error(error);
            throw new IllegalArgumentException(error);
        }

        UUID profileUuid = requestMessage.getProfileUuid();

        GetProfileDetailsResponseMessage responseMessage = new GetProfileDetailsResponseMessage();

        try
        {
            List<ProfileDataDAOModel> foundProfiles = profileDAO.findByProfileUuid(profileUuid);

            if (foundProfiles == null || foundProfiles.size() == 0) {
                responseMessage.setResponseStatus(GetProfileDetailsResponseStatus.PROFILE_UUID_NOT_REGISTERED);

                log.debug("No profile found with given UUID: " + profileUuid);

                return responseMessage;
            }

            if (foundProfiles.size() > 1) {
                responseMessage.setResponseStatus(GetProfileDetailsResponseStatus.GENERAL_ERROR);

                log.error("Got {} records for user profile UUID: {}", foundProfiles.size(), profileUuid);

                return responseMessage;
            }

            ProfileDataDAOModel profileDataDAOModel = foundProfiles.get(0);

            responseMessage.setResponseStatus(GetProfileDetailsResponseStatus.OK);
            responseMessage.setProfileUuid(profileDataDAOModel.getProfileUuid());
            responseMessage.setUserName(profileDataDAOModel.getUserName());
            responseMessage.setUserEmail(profileDataDAOModel.getUserEmail());
            responseMessage.setName(profileDataDAOModel.getName());
            responseMessage.setSurname(profileDataDAOModel.getSurname());

            return responseMessage;
        }
        catch (Exception ex){

            log.error(ex.toString());

            responseMessage.setResponseStatus(GetProfileDetailsResponseStatus.GENERAL_ERROR);
            return responseMessage;
        }
    }
}