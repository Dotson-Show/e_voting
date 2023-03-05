package com.evoter.user.imodel;

import org.springframework.beans.factory.annotation.Value;

public interface UserBasicInfoI {
    @Value(value = "#{target.id}")
    Long id();

    @Value(value = "#{target.email}")
    String email();

    @Value(value = "#{target.phone_number}")
    String phoneNumber();

    @Value(value = "#{target.first_name}")
    String firstName();

    @Value(value = "#{target.last_name}")
    String lastName();
}
