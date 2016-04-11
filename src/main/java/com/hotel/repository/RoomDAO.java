package com.hotel.repository;

import com.hotel.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RoomDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String GET_STATUS_BY_ID =
        "SELECT status_name FROM hotel_room_status WHERE id = :id";
    public String getStatusByID(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Map<String, Object>> dbResult = namedParameterJdbcTemplate.queryForList(GET_STATUS_BY_ID, params);
        if (dbResult.size() == 0) {
            return null;
        }
        return dbResult.stream().map(row -> (String)row.get("status_name")).collect(Collectors.toList()).get(0);
    }

    public static final String GET_TYPE_BY_ID =
        "SELECT type_name FROM hotel_room_type WHERE id = :id";
    public String getTypeByID(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Map<String, Object>> dbResult = namedParameterJdbcTemplate.queryForList(GET_TYPE_BY_ID, params);
        if (dbResult.size() == 0) {
            return null;
        }
        return dbResult.stream().map(row -> (String)row.get("type_name")).collect(Collectors.toList()).get(0);
    }

    public static final String UPDATE_ROOM =
        "UPDATE hotel_room set price = :price , type= :type where number = :number";
    public int updateRoom(Room room) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("price", room.getPrice());
        params.addValue("type", room.getType());
        params.addValue("number", room.getNumber());
        int dbResult = namedParameterJdbcTemplate.update(UPDATE_ROOM,params);
        return dbResult;
    }

}
