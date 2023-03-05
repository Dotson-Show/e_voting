package com.evoter.general.service;


import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface GeneralService {
    boolean isStringInvalid(String string);

//    String getObjectAsString(Object o);

    Response prepareResponse(ResponseCodeAndMessage codeAndMessage, Object data);

    Response prepareResponse(String responseCode, String responseMessage, Object data);

    Pageable getPageableObject(int size, int page);

    Pageable getPageableObject(int size, int page, Sort sort);

    void createDTOFromModel(Object from, Object to);

    Response getResponse(String responseCode, String responseMessage, Object data);
}
