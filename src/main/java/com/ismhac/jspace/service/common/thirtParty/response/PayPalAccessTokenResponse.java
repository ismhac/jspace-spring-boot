package com.ismhac.jspace.service.common.thirtParty.response;

import lombok.Data;

@Data
public class PayPalAccessTokenResponse {
    private String scope;
    private String access_token;
    private String token_type;
    private String app_id;
    private long expires_in;
    private String nonce;
}
