package com.amundi.gp.kafka.dto;

import java.util.Date;

public class QuotationDto {

    private String isin;
    private Double quote;
    private Date quoteDate;

    public QuotationDto(String isin, Double quote, Date quoteDate) {
        this.isin = isin;
        this.quote = quote;
        this.quoteDate = quoteDate;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
    }

    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }
}
