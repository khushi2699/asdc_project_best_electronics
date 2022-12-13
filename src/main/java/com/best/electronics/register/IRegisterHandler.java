package com.best.electronics.register;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;

import java.util.Map;

public interface IRegisterHandler {
    State register(Account account, Map<String, Object> typeSpecificParameters);
}
