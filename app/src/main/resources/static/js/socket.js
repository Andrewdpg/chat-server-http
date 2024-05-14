window.onload = function () {
  socket = new WebSocket("ws://localhost:8080" + "/websockets/chat");

  socket.onopen = function () {
    // Envía el nombre del usuario y la ID de la sesión al servidor
    socket.send(
      JSON.stringify({
        type: "sessionUpdate",
        cSessionId: getCookie("cSessionId"),
        cUsername: getCookie("cUsername"),
      })
    );
  };

  socket.onclose = function () {
    
  };

  socket.onmessage = function (message) {
    handleServerMessage(message);
  };

  document.getElementById("messageInput").onkeydown = function (event) {
    if (event.key === "Enter") {
      sendMessage();
    }
  };
};

var chatBox = document.getElementById("chatMessages");
function sendMessage() {
  let messageInput = document.getElementById("messageInput");
  let message = messageInput.value;

  if (message.trim() === "") {
    return;
  }

  addMessage(message, "outgoing");

  socket.send(
    JSON.stringify({
      type: "message",
      message: message,
      sender: getCookie("cUsername"),
      receiver: "andres",
      messageType: "TEXT",
      chatType: "PRIVATE",
    })
  );

  messageInput.value = "";
}

function handleServerMessage(serverMessage) {
  let message = JSON.parse(serverMessage.data);

  switch (message.type) {

    case "message":
      addMessage(message.message, "incoming");
      break;

    default:
      console.log("Unknown message type: " + message.type);
  }
};

function addMessage(message, type) {
  chatBox.append(create_message_element(message, type));
  while (chatBox.childNodes.length > 25) {
    chatBox.removeChild(chatBox.firstChild);
  }

  chatBox.scrollTop = chatBox.scrollHeight;
}

function create_message_element(text, type){
  let message = document.createElement("div");
  message.classList.add("message");
  message.classList.add(type);

  let body = document.createElement("div");
  body.classList.add("message-content");

  let time = document.createElement("span");
  time.classList.add("message-time");

  // Just hour and minutes
  time.innerHTML = new Date().toLocaleTimeString("en-US", {hour: "numeric", minute: "numeric"});
  
  let content = document.createElement("p");
  content.classList.add("message-content");
  content.innerHTML = text;

  body.appendChild(content);
  body.appendChild(time);
  message.appendChild(body);

  return message;
}





function getCookie(name) {
  var cookieArr = document.cookie.split(";");

  for (var i = 0; i < cookieArr.length; i++) {
    var cookiePair = cookieArr[i].split("=");

    if (name == cookiePair[0].trim()) {
      return decodeURIComponent(cookiePair[1]);
    }
  }

  return null;
}

function addCookie(name, value, days) {
  var date = new Date();
  date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
  var expires = "; expires=" + date.toUTCString();

  document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

function deleteCookie(name) {
  document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
}

function renewCookie(name, value, days) {
  addCookie(name, value, days);
}

if (getCookie("username") == null) {
  addCookie("username", "anonymous", 1);
} else {
  renewCookie("username", getCookie("username"), 1);
}