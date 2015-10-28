package com.tjazi.profiles.service.converters;

import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.service.dao.model.ProfileDataDAOModel;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */
public class RegisterProfileMessage2DaoConverter {

    public static ProfileDataDAOModel convertMessageToModel(RegisterNewProfileRequestMessage requestMessage){

        ProfileDataDAOModel daoModel = new ProfileDataDAOModel();

        daoModel.setUserEmail(requestMessage.getEmail());
        daoModel.setUserName(requestMessage.getUserName());
        daoModel.setName(requestMessage.getName());
        daoModel.setSurname(requestMessage.getName());

        return daoModel;
    }
}
