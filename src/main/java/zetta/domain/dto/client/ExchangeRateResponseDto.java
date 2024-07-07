package zetta.domain.dto.client;

import java.util.Map;

public class ExchangeRateResponse {

    private String result;
    private String documentation;
    private String termsOfUse;
    private long timeLastUpdateUnix;
    private String timeLastUpdateUtc;
    private long timeNextUpdateUnix;
    private String timeNextUpdateUtc;
    private String baseCode;
    private Map<String, Double> conversionRates;

}
