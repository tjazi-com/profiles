package com.tjazi.profiles.service.core;

import com.tjazi.profiles.messages.*;
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

    @Override
    public GetProfileDetailsByUserNameEmailResponseMessage getProfileDetailsByUserNameEmail(
            GetProfileDetailsByUserNameEmailRequestMessage requestMessage) {

        if (requestMessage == null) {
            String errorMessage = "requestMessage is null";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        String userNameEmail = requestMessage.getUserNameEmail();

        if (userNameEmail == null || userNameEmail.isEmpty()) {
            String errorMessage = "requestMessage.UserNameEmail is null or empty";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        GetProfileDetailsByUserNameEmailResponseMessage responseMessage =
                new GetProfileDetailsByUserNameEmailResponseMessage();

        try {
            // find the user profile by name / email

            List<ProfileDataDAOModel> foundProfiles = profileDAO.findByUserNameOrUserEmail(userNameEmail, userNameEmail);

            if (foundProfiles == null || foundProfiles.isEmpty()) {
                responseMessage.setResponseStatus(GetProfileDetailsByUserNameEmailResponseStatus.PROFILE_NOT_FOUND);
                return responseMessage;
            }

            int numberOfRecordsFound = foundProfiles.size();

            // this case shouldn't really happen, but we need to be sure we're controlling the flow
            if (numberOfRecordsFound > 1) {
                String errorMessage = "Found more that 1 profileDAO for user name / email: " + userNameEmail +
                        "; number of elements found: " + numberOfRecordsFound;

                log.error(errorMessage);
                responseMessage.setResponseStatus(GetProfileDetailsByUserNameEmailResponseStatus.GENERAL_ERROR);
                return responseMessage;
            }

            //all went fine so far - fill the result and return it
            ProfileDataDAOModel singleRecordReturned = foundProfiles.get(0);

            responseMessage.setResponseStatus(GetProfileDetailsByUserNameEmailResponseStatus.OK);
            responseMessage.setProfileUuid(singleRecordReturned.getProfileUuid());
            responseMessage.setUserName(singleRecordReturned.getUserName());
            responseMessage.setUserEmail(singleRecordReturned.getUserEmail());
            responseMessage.setName(singleRecordReturned.getName());
            responseMessage.setSurname(singleRecordReturned.getSurname());

            return responseMessage;

        } catch (Exception ex) {
            String errorMessage = "There was an exception when calling profileDAO.findByUserNameOrEmail();" +
                                "user name / email: " + userNameEmail + "; exception: " + ex.toString();

            log.error(errorMessage);

            responseMessage.setResponseStatus(GetProfileDetailsByUserNameEmailResponseStatus.GENERAL_ERROR);
            return responseMessage;
        }
    }
}
