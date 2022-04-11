package uj.java.w7.insurance;

import java.math.BigDecimal;

public record InsuranceEntry(
        int policyID,
        String stateCode,
        String county,
        BigDecimal eq_site_limit,
        BigDecimal hu_site_limit,
        BigDecimal fl_site_limit,
        BigDecimal fr_site_limit,
        BigDecimal tiv_2011,
        BigDecimal tiv_2012,
        BigDecimal eq_site_deductible,
        BigDecimal hu_site_deductible,
        BigDecimal fl_site_deductible,
        BigDecimal fr_site_deductible,
        BigDecimal point_latitude,
        BigDecimal point_longitude,
        String line,
        String construction,
        BigDecimal point_granularity) {

    public InsuranceEntry(String[] values) {
        this(Integer.parseInt(values[0]),
                values[1],
                values[2],
                new BigDecimal(values[3]),
                new BigDecimal(values[4]),
                new BigDecimal(values[5]),
                new BigDecimal(values[6]),
                new BigDecimal(values[7]),
                new BigDecimal(values[8]),
                new BigDecimal(values[9]),
                new BigDecimal(values[10]),
                new BigDecimal(values[11]),
                new BigDecimal(values[12]),
                new BigDecimal(values[13]),
                new BigDecimal(values[14]),
                values[15],
                values[16],
                new BigDecimal(values[17]));
    }

    public String country() {
        return county;
    }

    public BigDecimal tiv_2012() {
        return tiv_2012;
    }
}
