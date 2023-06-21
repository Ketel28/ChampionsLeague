package com.ubs.m295.dao;

import ch.ubs.m295.generated.v1.dto.ChampionsLeagueGroup;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GroupsDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String insert = "INSERT INTO championsleaguegroup (groupName, totalPoints) VALUES (:groupName, :totalPoints)";
    private final static String selectAll = "SELECT * FROM championsleaguegroup";
    private final static String selectByName = "SELECT * FROM championsleaguegroup WHERE groupName = :groupName";
    private final static String update = "UPDATE championsleaguegroup SET groupName = :groupName, totalPoints = :totalPoints WHERE groupName = :groupName";
    private final static String delete = "DELETE FROM championsleaguegroup WHERE groupName = :groupName";


    public GroupsDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        System.out.println(getAllGroups());
    }

    public void insertGroup(ChampionsLeagueGroup groups) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("groupName", groups.getGroupName().getValue())
                .addValue("totalPoints", groups.getTotalPoints());
        namedParameterJdbcTemplate.update(insert, params);
    }

//GET ALL

    public List<ChampionsLeagueGroup> getAllGroups() {
        return namedParameterJdbcTemplate.query(selectAll, new GroupsRowMapper());
    }

//GET BY ID

    public Optional<ChampionsLeagueGroup> getGroupByName(String groupName) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("groupName", groupName);

        List<ChampionsLeagueGroup> groups = namedParameterJdbcTemplate.query(selectByName, params, new GroupsRowMapper());
        if (groups.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(groups.get(0));
    }
//ROWMAPPER

    private static class GroupsRowMapper implements RowMapper<ChampionsLeagueGroup> {
        @Override
        public ChampionsLeagueGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChampionsLeagueGroup groups = new ChampionsLeagueGroup();
            String name = rs.getString("groupName");
            int points = rs.getInt("totalPoints");
            return groups.groupName(ChampionsLeagueGroup.GroupNameEnum.valueOf(name)).totalPoints(points);
        }
    }

//DELETE

    public boolean deleteGroup(String groupName) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("groupName", groupName);
        int inDb = namedParameterJdbcTemplate.update(delete, params);
        if (inDb == 1) {
            return true;
        }
        return false;
    }

//UPDATE

    public boolean updateGroup(ChampionsLeagueGroup group) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("groupName", group.getGroupName().getValue())
                .addValue("totalPoints", group.getTotalPoints());
        int inDb = namedParameterJdbcTemplate.update(update, params);
        if(inDb == 1){
            return true;
        }
        return false;
    }

}
