const BASE_URL = 'https://localhost:8077/navigator/routes';

const getShortest = () => {
  const shortestForm = document.forms['shortest'];
  const fromId = shortestForm.elements['from'].value;
  const toId = shortestForm.elements['to'].value;

  const shortestContainer = document.getElementById('shortest-container');
  const fromIdSpan = document.getElementById('from-id-span');
  const toIdSpan = document.getElementById('to-id-span');

  fetch(BASE_URL + '/' + fromId + '/' + toId + '/shortest').then(res => {
    fromIdSpan.innerHTML = fromId;
    toIdSpan.innerHTML = toId;
    if (res.ok) {
      return res.json();
    } else return res.text();
  }).then(text => shortestContainer.innerHTML = JSON.stringify(text, null, 2));

  return false;
};

const newBetween = () => {
  const betweenForm = document.forms['between'];
  const fromId = betweenForm.elements['from'].value;
  const toId = betweenForm.elements['to'].value;
  const distance = betweenForm.elements['distance'].value;

  const newContainer = document.getElementById('new-container');
  const newFromIdSpan = document.getElementById('new-from-id');
  const newToIdSpan = document.getElementById('new-to-id');

  fetch(BASE_URL + '/add/' + fromId + '/' + toId + '/' + distance, { method: 'POST' }).then(res => {
    newFromIdSpan.innerHTML = fromId;
    newToIdSpan.innerHTML = toId;
    if (res.ok) {
      return res.json();
    } else return res.text();
  }).then(text => newContainer.innerHTML = JSON.stringify(text, null, 2));

  return false;
};