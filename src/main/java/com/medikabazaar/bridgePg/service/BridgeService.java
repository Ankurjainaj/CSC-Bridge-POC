package com.medikabazaar.bridgePg.service;

import com.medikabazaar.bridgePg.dto.request.MerchantRequest;
import com.medikabazaar.bridgePg.dto.response.EncryptedMerchantResponse;
import com.medikabazaar.bridgePg.dto.response.PaymentResponse;

public interface BridgeService {
    EncryptedMerchantResponse getEncryptedResponse(Object request) throws IllegalAccessException;

    PaymentResponse getDecryptedResponse(String request);
}
