package com.medikabazaar.bridgePg.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse implements Serializable {

    private String cscTxn;
    private String merchantId;
    private String cscId;
    private String merchantTxn;
    private String txnStatus;
    private String productId;
    private double txnAmount;
    private String amountParameter;
    private String txnMode;
    private String txnType;
    private String merchantReceiptNo;
    private double cscShareAmount;
    private String payToEmail;
    private String currency;
    private String discount;
    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String txnStatusMessage;
    private String statusMessage;
    private String merchantTxnDateTime;
    private String errCode;
    private String errMsg;
}
