package com.example.dividendproject.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrapedResult {

    private Company company;

    private List<Dividend> dividends;

    public ScrapedResult() {
        this.dividends = new ArrayList<>();
    }
}
