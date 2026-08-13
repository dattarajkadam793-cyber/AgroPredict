// ===============================
// STEP 1: CONFIGURATION
// ===============================

// If page has not set config → use default
if (window.chatbotConfig == undefined) {
  window.chatbotConfig = {};
  window.chatbotConfig.position = "bottom-right";
  window.chatbotConfig.preprompt = "You are a helpful agriculture assistant.";
}


// ===============================
// STEP 2: CREATE CHATBOT UI
// ===============================

function loadChatbot() {

  // Create button
  var btn = document.createElement("button");
  btn.innerHTML = "💬";
  btn.id = "chatBtn";

  btn.onclick = function () {
    toggleChat();
  };

  // Create chatbox
  var chatbox = document.createElement("div");
  chatbox.id = "chatbox";

  chatbox.innerHTML =
    "<div id='chatHeader'>" +
      "Agro Predict Bot 🌱" +
      "<span onclick='toggleChat()'>✖</span>" +
    "</div>" +

    "<div id='chatBody'>" +
      "<p>👋 Hi! Ask me anything.</p>" +
    "</div>" +

    "<div id='chatInput'>" +
      "<input type='text' id='userInput' placeholder='Type a message...' />" +
      "<button onclick='sendMessage()'>➤</button>" +
    "</div>";

  document.body.appendChild(btn);
  document.body.appendChild(chatbox);

  applyPosition();

  // ENTER key support
  var inputBox = document.getElementById("userInput");
  inputBox.addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
      sendMessage();
    }
  });
}


// ===============================
// STEP 3: POSITION CONTROL
// ===============================

function applyPosition() {

  var btn = document.getElementById("chatBtn");
  var box = document.getElementById("chatbox");

  if (window.chatbotConfig.position == "top-right") {

    btn.style.top = "20px";
    btn.style.right = "20px";
    btn.style.bottom = "auto";

    box.style.top = "80px";
    box.style.right = "20px";
    box.style.bottom = "auto";
  }
  else if (window.chatbotConfig.position == "bottom-left") {

    btn.style.bottom = "20px";
    btn.style.left = "20px";

    box.style.bottom = "80px";
    box.style.left = "20px";
  }
  else {
    // default bottom-right
    btn.style.bottom = "20px";
    btn.style.right = "20px";

    box.style.bottom = "80px";
    box.style.right = "20px";
  }
}


// ===============================
// STEP 4: TOGGLE CHAT
// ===============================

function toggleChat() {

  var box = document.getElementById("chatbox");

  if (box.style.display == "block") {
    box.style.display = "none";
  } else {
    box.style.display = "block";
  }
}


// ===============================
// STEP 5: SEND MESSAGE
// ===============================

function sendMessage() {

  var input = document.getElementById("userInput");
  var chatBody = document.getElementById("chatBody");

  var userText = input.value;

  if (userText == "") {
    return;
  }

  // Show user message
  chatBody.innerHTML =
    chatBody.innerHTML +
    "<p><b>You:</b> " + userText + "</p>";

  input.value = "";

  // Scroll down
  chatBody.scrollTop = chatBody.scrollHeight;

  // API CALL
  fetch("https://openrouter.ai/api/v1/chat/completions", {
    method: "POST",
    headers: {
      "Authorization": "Bearer YOUR_OPENROUTER_API_KEY",
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      model: "openrouter/free",
      messages: [
        {
          role: "system",
          content: window.chatbotConfig.preprompt
        },
        {
          role: "user",
          content: userText
        }
      ]
    })
  })
  .then(function (response) {
    return response.json();
  })
  .then(function (data) {

    console.log("API RESPONSE:", data);

    var reply = "No response";

    if (data.choices && data.choices.length > 0) {
      reply = data.choices[0].message.content;
    }

    chatBody.innerHTML =
      chatBody.innerHTML +
      "<p><b>Bot:</b> " + reply + "</p>";

    chatBody.scrollTop = chatBody.scrollHeight;
  })
  .catch(function (error) {

    console.log("ERROR:", error);

    chatBody.innerHTML =
      chatBody.innerHTML +
      "<p style='color:red;'>Error getting response</p>";
  });
}


// ===============================
// STEP 6: LOAD CHATBOT
// ===============================

window.onload = function () {
  loadChatbot();
};