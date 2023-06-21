package com.ubs.m295;

import ch.ubs.m295.generated.v1.dto.ChampionsLeagueGroup;
import ch.ubs.m295.generated.v1.dto.Team;
import com.ubs.m295.dao.GroupsDao;
import com.ubs.m295.dao.TeamsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class M295KeremTelliApplicationTests {
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private TeamsDao teamsDao;
    private GroupsDao groupsDao;

    @BeforeEach
    void setUp() {
        this.teamsDao = new TeamsDao(this.namedParameterJdbcTemplate);
        this.groupsDao = new GroupsDao(this.namedParameterJdbcTemplate);
    }

    @Test
    void addTeam() {
        Team team = new Team()
                .teamName("Oetwil am See")
                .points(13)
                .foundingYear(1877)
                .groupName(Team.GroupNameEnum.valueOf("C"));
        teamsDao.insertTeam(team);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(
                eq("INSERT INTO teams (teamName, points, foundingYear, groupName) VALUES (:teamName, :points, :foundingYear, :groupName)"
                ),
                argumentCaptor.capture()
        );

        MapSqlParameterSource mapSqlParameterSource = argumentCaptor.getValue();
        assertThat(mapSqlParameterSource.getValue("teamName")).isEqualTo("Oetwil am See");
        assertThat(mapSqlParameterSource.getValue("points")).isEqualTo(13);
        assertThat(mapSqlParameterSource.getValue("foundingYear")).isEqualTo(1877);
        assertThat(mapSqlParameterSource.getValue("groupName")).isEqualTo("C");
    }

    private Team testTeam(String teamName, int points, int foundingYear, String groupName) {
        Team teams = new Team();
        teams.setTeamName(teamName);
        teams.setPoints(points);
        teams.setFoundingYear(foundingYear);
        teams.setGroupName(Team.GroupNameEnum.valueOf(groupName));
        return teams;
    }
    @Test
    void deleteTeamById() {
        int id = 5;
        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MapSqlParameterSource> paramsCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);

        teamsDao.deleteTeam(id);

        verify(namedParameterJdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());

        assertEquals("DELETE FROM teams WHERE teamId = :teamId", sqlCaptor.getValue());

        MapSqlParameterSource value = paramsCaptor.getValue();
        assertEquals(id, value.getValue("teamId"));
    }
    @Test
    void updateTeam() {
        Team team = new Team()
                .teamId(5)
                .teamName("GCZ")
                .points(14)
                .foundingYear(1900)
                .groupName(Team.GroupNameEnum.valueOf("A"));

        teamsDao.updateTeam(team);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MapSqlParameterSource> paramsCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);

        verify(namedParameterJdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());

        assertEquals("UPDATE teams SET teamName = :teamName, points = :points, foundingYear = :foundingYear, groupName = :groupName WHERE teamId = :teamId", sqlCaptor.getValue());

        MapSqlParameterSource params = paramsCaptor.getValue();
        assertEquals(team.getTeamId(), params.getValue("teamId"));
        assertEquals(team.getTeamName(), params.getValue("teamName"));
        assertEquals(team.getPoints(), params.getValue("points"));
        assertEquals(team.getFoundingYear(), params.getValue("foundingYear"));
        assertEquals(team.getGroupName().getValue(), params.getValue("groupName"));

    }
    @Test
    void addGroup() {
        ChampionsLeagueGroup group = new ChampionsLeagueGroup()
                .groupName(ChampionsLeagueGroup.GroupNameEnum.valueOf("D"))
                .totalPoints(20);
        groupsDao.insertGroup(group);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(
                eq("INSERT INTO championsleaguegroup (groupName, totalPoints) VALUES (:groupName, :totalPoints)"
                ),
                argumentCaptor.capture()
        );

        MapSqlParameterSource mapSqlParameterSource = argumentCaptor.getValue();
        assertThat(mapSqlParameterSource.getValue("groupName")).isEqualTo("D");
        assertThat(mapSqlParameterSource.getValue("totalPoints")).isEqualTo(20);

}

    private ChampionsLeagueGroup testGroup(String groupName, int totalPoints) {
        ChampionsLeagueGroup groups = new ChampionsLeagueGroup();
        groups.setGroupName(ChampionsLeagueGroup.GroupNameEnum.valueOf(groupName));
        groups.setTotalPoints(totalPoints);
        return groups;
    }

    @Test
    void deleteGroupById() {
        String letter = "A";
        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MapSqlParameterSource> paramsCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);

        groupsDao.deleteGroup(letter);

        verify(namedParameterJdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());

        assertEquals("DELETE FROM championsleaguegroup WHERE groupName = :groupName", sqlCaptor.getValue());

        MapSqlParameterSource value = paramsCaptor.getValue();
        assertEquals(letter, value.getValue("groupName"));
    }

    @Test
    void updateGroup() {
        ChampionsLeagueGroup group = new ChampionsLeagueGroup()
                .groupName(ChampionsLeagueGroup.GroupNameEnum.valueOf("A"))
                .totalPoints(11);

        groupsDao.updateGroup(group);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MapSqlParameterSource> paramsCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);

        verify(namedParameterJdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());

        assertEquals("UPDATE championsleaguegroup SET groupName = :groupName, totalPoints = :totalPoints WHERE groupName = :groupName", sqlCaptor.getValue());

        MapSqlParameterSource params = paramsCaptor.getValue();
        assertEquals(group.getGroupName().getValue(), params.getValue("groupName"));
        assertEquals(group.getTotalPoints(), params.getValue("totalPoints"));

    }

}
