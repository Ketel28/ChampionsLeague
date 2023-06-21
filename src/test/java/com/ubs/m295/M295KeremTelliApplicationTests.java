package com.ubs.m295;

import com.ubs.m295.dao.GroupsDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@ExtendWith(MockitoExtension.class)
class M295ProjektApplicationTests {
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private TeamsDao teamsDao;
    private GroupsDao playerdao;

    @BeforeEach
    void setUp() {
        this.teamsDao = new TeamsDao(this.namedParameterJdbcTemplate);
        this.playerdao = new PlayerDao(this.namedParameterJdbcTemplate);
    }

    @Test
    void addTeam() {
        Team team = new Team()
                .team("FCJ")
                .smWinner(13)
                .captain("Freueler");
        teamsDao.addTeam(team);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(
                eq("INSERT INTO teams (Team, SM_Winner, Captain) VALUES (:Team, :SM_Winner, :Captain)"
                ),
                argumentCaptor.capture()
        );

        MapSqlParameterSource mapSqlParameterSource = argumentCaptor.getValue();
        assertThat(mapSqlParameterSource.getValue("team")).isEqualTo("FCJ");
        assertThat(mapSqlParameterSource.getValue("sm_winner")).isEqualTo(13);
        assertThat(mapSqlParameterSource.getValue("Captain")).isEqualTo("Freueler");
    }
    private Team testTeam(String team, int sm_winner, String captain) {
        Team teams = new Team();
        teams.setTeam(team);
        teams.setSmWinner(sm_winner);
        teams.setCaptain(captain);
        return teams;
    }
    @Test
    void getAllTeams() {
        List<Team> allTeams = Arrays.asList(
                testTeam("Team A", 13, "Test")

        );

        when(namedParameterJdbcTemplate.query(eq(READ), any(BeanPropertyRowMapper.class)))
                .thenReturn(allTeams);

        List<Team> actualTeams = teamsDao.getAllTeams();

        assertEquals(allTeams, actualTeams);
        verify(namedParameterJdbcTemplate).query(eq(READ), any(BeanPropertyRowMapper.class));
    }
    @Test
    void deleteTeamByName() {

        String team ="GCZ";
        teamsDao.deleteTeamByName(team);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Map<String, Object>> paramsCaptor = ArgumentCaptor.forClass(Map.class);

        verify(namedParameterJdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());

        assertEquals("DELETE FROM teams WHERE Team = :Team", sqlCaptor.getValue());

        Map<String, Object> params = paramsCaptor.getValue();
        assertEquals(team, params.get("Team"));
    }
    @Test
    void updateTeam() {
        Team team = new Team()
                .idTeams(1)
                .team("GCZ")
                .smWinner(13)
                .captain("Freuler");

        int expectedAffectedRows = 1;

        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenReturn(expectedAffectedRows);

        int acutalAffectedRows = teamsDao.updateTeam(team);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MapSqlParameterSource> paramsCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);

        verify(namedParameterJdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());

        assertEquals("UPDATE teams SET Team = :Team, SM_Winner = :SM_Winner, Captain = :Captain WHERE idTeams = :idTeams", sqlCaptor.getValue());

        MapSqlParameterSource params = paramsCaptor.getValue();
        assertEquals(team.getIdTeams(), params.getValue("idTeams"));
        assertEquals(team.getTeam(), params.getValue("Team"));
        assertEquals(team.getSmWinner(), params.getValue("SM_Winner"));
        assertEquals(team.getCaptain(), params.getValue("Captain"));
        assertEquals(expectedAffectedRows, acutalAffectedRows);


    }
    @Test
    void addPlayer

}

}
