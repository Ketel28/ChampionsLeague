package com.ubs.m295.jdbc.dao;

import ch.ubs.m295.generated.v1.dto.Team;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TeamsDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final static String insert = "INSERT INTO teams (teamName, points, foundingYear, groupName) VALUES (:teamName, :points, :foundingYear, :groupName)";
    private final static String selectAll = "SELECT * FROM teams";
    private final static String selectById = "SELECT * FROM teams WHERE teamId = :teamId";
    private final static String update = "UPDATE teams SET teamName = :teamName, points = :points, foundingYear = :foundingYear, groupName = :groupName WHERE teamId = :teamId";
    private final static String delete = "DELETE FROM teams WHERE teamId = :teamId";


    public TeamsDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        System.out.println(getAllTeams());
    }

    public void insertTeam(Team teams) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("teamId", teams.getTeamId())
                .addValue("teamName", teams.getTeamName())
                .addValue("points", teams.getPoints())
                .addValue("foundingYear", teams.getFoundingYear())
                .addValue("groupName", teams.getGroupName().getValue());
        namedParameterJdbcTemplate.update(insert, params);
    }

//GET ALL

    public List<Team> getAllTeams() {
        return namedParameterJdbcTemplate.query(selectAll, new TeamRowMapper());
    }

//GET BY ID

    public Optional<Team> getTeamById(int teamId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("teamId", teamId);

        List<Team> teams = namedParameterJdbcTemplate.query(selectById, params, new TeamRowMapper());
        if (teams.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(teams.get(0));
    }
//ROWMAPPER

    private static class TeamRowMapper implements RowMapper<Team> {
        @Override
        public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
            Team team = new Team();
            int id = rs.getInt("teamId");
            String name = rs.getString("teamName");
            int points = rs.getInt("points");
            int foundingYear = rs.getInt("foundingYear");
            String groupName = rs.getString("groupName");
            return team.teamId(id).teamName(name).points(points).foundingYear(foundingYear).groupName(Team.GroupNameEnum.valueOf(groupName));
        }
    }

//DELETE

    public boolean deleteTeam(int teamId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("teamId", teamId);
        int inDb = namedParameterJdbcTemplate.update(delete, params);
        if (inDb == 1) {
            return true;
        }
        return false;
    }

//UPDATE

    public boolean updateTeam(Team team) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("teamId", team.getTeamId())
                .addValue("teamName", team.getTeamName())
                .addValue("points", team.getPoints())
                .addValue("foundingYear", team.getFoundingYear())
                .addValue("groupName", team.getGroupName().getValue());
        int inDb = namedParameterJdbcTemplate.update(update, params);
        if(inDb == 1){
            return true;
        }
        return false;
    }

}
