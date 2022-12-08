package com.best.electronics.controller.email;

import com.best.electronics.model.User;

public interface EmailControllerPinResetStoreDAO {
    int updatePinResetToken(User user, Integer randomNumberToken);
}
