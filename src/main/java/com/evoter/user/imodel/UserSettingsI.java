package com.evoter.user.imodel;

import org.springframework.beans.factory.annotation.Value;

public interface UserSettingsI {

    @Value(value = "#{target.public_key}")
    String publicKey();

    @Value(value = "#{target.private_key}")
    String privateKey();
}
