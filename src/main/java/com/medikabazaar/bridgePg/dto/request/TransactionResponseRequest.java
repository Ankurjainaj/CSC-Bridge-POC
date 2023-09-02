package com.medikabazaar.bridgePg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseRequest implements Serializable {

    private String responseData;

}
