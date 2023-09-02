package com.medikabazaar.bridgePg.controller;

import com.medikabazaar.bridgePg.dto.request.MerchantRequest;
import com.medikabazaar.bridgePg.dto.request.TransactionResponseRequest;
import com.medikabazaar.bridgePg.dto.response.EncryptedMerchantResponse;
import com.medikabazaar.bridgePg.dto.response.PaymentResponse;
import com.medikabazaar.bridgePg.service.BridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/bridge")
@Validated
public class ApiController {

    @Autowired
    private BridgeService bridgeService;

    @PostMapping("/encryptData")
    public ResponseEntity<?> encryptedData(@RequestBody @NotNull Object request) throws IllegalAccessException {
        EncryptedMerchantResponse response = bridgeService.getEncryptedResponse(request);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/decryptData")
    public ResponseEntity<?> decryptResponse(@RequestBody @Valid TransactionResponseRequest request) {
        PaymentResponse response = bridgeService.getDecryptedResponse(request.getResponseData());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
