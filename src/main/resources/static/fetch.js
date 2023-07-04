function validationHinzufügen() {
  let inputName = document.getElementById();
}

function getTeams() {
  var myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");
  var isLoggedIn = false;
    while (!isLoggedIn) {
        // Prompt the user to enter username and password
        var username = prompt('username (kerem):');
        var password = prompt('password (1234):');
        if (username === 'kerem' && password === '1234') {
            myHeaders.append("Authorization", "Basic " + btoa(username + ":" + password));
            isLoggedIn = true;
        } else {
            alert('Wrong username or password try again.');
        }
      }
  fetch('http://localhost:8080/teams', {
    headers: myHeaders,
  })
    .then(response => response.json())
    .then(data => {
      const teamBody = document.getElementById('team-body');
      teamBody.innerHTML = '';
      data.forEach(team => {
        const row = document.createElement('tr');
        row.innerHTML = `
        <td id="teamId" value="${team.teamId}">${team.teamId}</td>
        <td>${team.teamName}</td>
        <td>${team.points}</td>
        <td>${team.foundingYear}</td>
        <td>${team.groupName}</td>
        <button class="btn btn-primary" id="verwalten1" value="${team.teamId}" data-bs-toggle="modal" data-bs-target="#teamUpdaten" onclick="getGroups2();setTeamId(this.value)">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
          <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
          <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
        </svg>
      </button>
      <button class="btn btn-primary" id="verwalten2" onclick="deleteTeam(this.value)" value="${team.teamId}">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
          <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
          <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
        </svg>
      </button>`;
        teamBody.appendChild(row);
      });
    })
    .catch(error => {
      console.error(error);
    });
}


function getGroups1() {
  fetch('http://localhost:8080/groups')
    .then(response => response.json())
    .then(group => {
      const groupSelect = document.getElementById('gruppeSelect');
      groupSelect.innerHTML = '';

      group.forEach(groups => {
        const option = document.createElement('option');
        option.value = groups.groupName;
        option.textContent = groups.groupName;
        groupSelect.appendChild(option);
      });
    })
    .catch(error => {
      console.error(error);
    });
}

function getGroups2() {
  fetch('http://localhost:8080/groups')
    .then(response => response.json())
    .then(group => {
      const groupSelect = document.getElementById('gruppeSelect2');
      groupSelect.innerHTML = '';

      group.forEach(groups => {
        const option = document.createElement('option');
        option.value = groups.groupName;
        option.textContent = groups.groupName;
        groupSelect.appendChild(option);
      });
    })
    .catch(error => {
      console.error(error);
    });
}



function addTeams() {
  var myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");
  var isLoggedIn = false;
    while (!isLoggedIn) {
        // Prompt the user to enter username and password
        var username = prompt('username (kerem):');
        var password = prompt('password (1234):');
        if (username === 'kerem' && password === '1234') {
            myHeaders.append("Authorization", "Basic " + btoa(username + ":" + password));
            isLoggedIn = true;
        } else {
            alert('Wrong username or password try again.');
        }
      }

  const teamName = document.querySelector('#teamName');
  const punkte = document.querySelector('#punkte');
  const gründungsjahr = document.querySelector('#gründungsjahr');
  const gruppe = document.querySelector('#gruppeSelect');

  // Validierung für Teamname
  if (teamName.value.trim() === '') {
    alert('Bitte geben Sie einen Teamnamen ein.');
    return;
  }

  // Validierung für Punkte
  const pointsValue = parseInt(punkte.value);
  if (isNaN(pointsValue) || pointsValue < 0 || pointsValue > 20) {
    alert('Bitte geben Sie eine gültige Punktzahl zwischen 0 und 20 ein.');
    return;
  }

  // Validierung für Gründungsjahr
  const foundingYearValue = parseInt(gründungsjahr.value);
  const currentYear = new Date().getFullYear();
  if (isNaN(foundingYearValue) || foundingYearValue < 1700 || foundingYearValue > currentYear) {
    alert(`Bitte geben Sie ein gültiges Gründungsjahr zwischen 1700 und ${currentYear} ein.`);
    return;
  }

  // Validierung für Gruppe
  if (gruppe.value === '') {
    alert('Bitte wählen Sie eine Gruppe aus.');
    return;
  }


  const teamsData = {
    teamName: teamName.value,
    points: punkte.value,
    foundingYear: gründungsjahr.value,
    groupName: gruppe.value,
  };

  fetch('http://localhost:8080/teams', {
    method: 'POST',
    headers: myHeaders,
    body: JSON.stringify(teamsData)
  })
    .then(response => response.json())
    .then(data => {
      teamName.value = "";
      punkte.value = "";
      gründungsjahr.value = "";
      gruppe.value = "";

      alert("Team wurde erfolgreich hinzugefügt")
      getTeams();

      /*const teamsModal = new bootstrap.Modal(document.getElementById('teamHinzufügen'));
      teamsModal.hide();
      getGroups();*/
    })
    .catch(error => {
      console.error(error);
    });
}



// Deleted das Team mithilfe dem geglickten Button auf dem Frontend

function deleteTeam(teamId){
 
  console.log(teamId);

    fetch(`http://localhost:8080/teams/${teamId}`, {
      method: 'DELETE'
    })
      .then(response => {
        if (response.ok) {
          alert("Team " + teamId  + " wurde erfolgreich entfernt")
          getTeams();
        } else {
          console.log('Fehler beim Löschen des Teams.');
          // Aktionen im Fehlerfall
        }
      })
      .catch(error => {
        console.error('Fehler bei der Anfrage:', error);
      });
}


function setTeamId(value) {
  let buttonValue = document.getElementById("verwalten1").value;
  buttonValue = value;
  const button = document.getElementById("updaten");
  button.setAttribute("onclick", `updateTeam(${value})`)
}


function updateTeam(teamId) {
  const teamId2 = teamId;
  const teamName2 = document.querySelector('#teamName2');
  const punkte2 = document.querySelector('#punkte2');
  const gründungsjahr2 = document.querySelector('#gründungsjahr2');
  const gruppe2 = document.querySelector('#gruppeSelect2');

  const teamsData = JSON.stringify( {

    teamId: teamId2,
    teamName: teamName2.value,
    points: punkte2.value,
    foundingYear: gründungsjahr2.value,
    groupName: gruppe2.value,
  });

  console.log(teamsData);

  let headers = new Headers();
  headers.append("Content-Type", "application/json");

  var requestOptions = {
    method: 'PUT',
    redirect: 'follow',
    body: teamsData,
    headers: headers
  };


  fetch(`http://localhost:8080/teams/` + teamId, requestOptions)
    .then(response => response.text())
    .then((result) => getTeams())
    .catch(error => {
      console.error(error);
    });
}

