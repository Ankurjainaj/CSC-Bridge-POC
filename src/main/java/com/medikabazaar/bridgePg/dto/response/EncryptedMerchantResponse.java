package com.medikabazaar.bridgePg.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncryptedMerchantResponse implements Serializable {

    private String requestData;
    private String paymentUrl;
}
