package com.medikabazaar.bridgePg.service;


import bridgeutil.BridgeCryptor;
import bridgeutil.BridgeFactory;
import bridgeutil.BridgePgUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medikabazaar.bridgePg.dto.response.EncryptedMerchantResponse;
import com.medikabazaar.bridgePg.dto.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Service
@Slf4j
public class BridgeServiceImpl implements BridgeService {

    @Value("${bridge.private.key}")
    private String privateKey;
    @Value("${bridge.public.key}")
    private String publicKey;
    @Value("${bridge.url}")
    private String bridgeUrl;

    @Override
    public EncryptedMerchantResponse getEncryptedResponse(Object request) {
        BridgePgUtil bg = new BridgePgUtil();
        bg.createBridgeDefaultParameters();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString;
        Map<String, Object> hashMap;
        try {
            jsonString = objectMapper.writeValueAsString(request);
            hashMap = objectMapper.readValue(jsonString, HashMap.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }

        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue() == null ? "" : String.valueOf(entry.getValue());
            bg.addParameter(fieldName, fieldValue);
        }

        String encrypt = hashMap.get("merchant_id") + "|";
        encrypt += bg.getEncryptedParameters(privateKey, publicKey);

        String urlFraction = bg.getUrlFraction();
        log.info("urlFraction----------- " + urlFraction);
        log.info("encrypt--------- " + encrypt);

        return new EncryptedMerchantResponse(encrypt, bridgeUrl + urlFraction);
    }

    private static HashMap<String, String> strToMap(String resp) {
        HashMap<String, String> hm = new HashMap<>();
        StringTokenizer st = new StringTokenizer(resp, "|");
        while (st.hasMoreTokens()) {
            StringTokenizer st1 = new StringTokenizer(st.nextToken(), "=");
            String key1 = st1.nextToken();
            boolean moreToken = st1.hasMoreTokens();
            String value = null;
            if (moreToken) {
                value = st1.nextToken();
            } else {
                value = "";
            }
            hm.put(key1, value);
        }
        return hm;
    }

    @Override
    public PaymentResponse getDecryptedResponse(String request) {
        PaymentResponse paymentResponse = new PaymentResponse();
        try {
            BridgeCryptor bg = BridgeFactory.getBridgeCryptor();
            bg.setKeys(privateKey, publicKey);
            String decValues = bg.decrypt(request);

            HashMap<String, String> resMap = strToMap(decValues);
            paymentResponse.setCscTxn(resMap.get("csc_txn"));
            paymentResponse.setErrCode(resMap.get("error_code"));
            paymentResponse.setErrMsg(resMap.get("error_message"));
            paymentResponse.setMerchantId(resMap.get("merchant_id"));
            paymentResponse.setCscId(resMap.get("csc_id"));
            paymentResponse.setMerchantTxn(resMap.get("merchant_txn"));
            paymentResponse.setTxnStatus(resMap.get("txn_status"));
            paymentResponse.setMerchantTxnDateTime(resMap.get("merchant_txn_date_time"));
            paymentResponse.setProductId(resMap.get("product_id"));
            String txn_amount = resMap.get("txn_amount");
            if (txn_amount != null && !txn_amount.isEmpty()) {
                paymentResponse.setTxnAmount(Double.parseDouble(txn_amount));
            }
            paymentResponse.setAmountParameter(resMap.get("amount_parameter"));
            paymentResponse.setTxnMode(resMap.get("txn_mode"));
            paymentResponse.setTxnType(resMap.get("txn_type"));
            paymentResponse.setMerchantReceiptNo(resMap.get("merchant_receipt_no"));

            String csc_share_amount = resMap.get("csc_share_amount");
            if (csc_share_amount != null && !csc_share_amount.isEmpty()) {
                paymentResponse.setCscShareAmount(Double.parseDouble(csc_share_amount));
            }
            paymentResponse.setPayToEmail(resMap.get("pay_to_email"));
            paymentResponse.setCurrency(resMap.get("currency"));
            paymentResponse.setDiscount(resMap.get("discount"));
            paymentResponse.setParam1(resMap.get("param_1"));
            paymentResponse.setParam2(resMap.get("param_2"));
            paymentResponse.setParam3(resMap.get("param_3"));
            paymentResponse.setParam4(resMap.get("param_4"));
            paymentResponse.setTxnStatusMessage(resMap.get("txn_status_message"));
            paymentResponse.setStatusMessage(resMap.get("status_message"));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return paymentResponse;
    }
}
