package com.hotel.repository;

import com.hotel.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class HotelOrderDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String GET_REPORT_BY_YEAR =
        "select MONTH(created_date) as month, SUM(amount) as total from hotel_order where YEAR(created_date) = :year GROUP BY MONTH(created_date)";
    public List<Double> getReportByYear(int year) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("year", year);
        List<Map<String, Object>> dbResult = namedParameterJdbcTemplate.queryForList(GET_REPORT_BY_YEAR, params);
        if (dbResult.size() == 0) {
            return null;
        }
        return dbResult.stream().map(row -> (Double)row.get("total")).collect(Collectors.toList());
    }



}
