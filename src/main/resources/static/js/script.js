'use strict'

let stompClient;
let stompClient2;
let username;

const connect = (event) => {
    username = document.querySelector('#username').value.trim()

    if (username) {
        const login = document.querySelector('#login')
        login.classList.add('hide')

        const chatPage = document.querySelector('#chat-page')
        chatPage.classList.remove('hide')

        // create new socket connection
        const socket = new SockJS('/chat-example');
        const socket2 = new SockJS("/trading-platform-stream");
        stompClient = Stomp.over(socket)
        stompClient2 = Stomp.over(socket2);
        stompClient.connect({}, onConnected, onError)
        stompClient2.connect({},onConnectStream, onError);
    }
    event.preventDefault()
}





const onConnectStream = () => {
    console.log("Connected to Kiran's Socket");
}


const testEndpoint = () => {
    var id2 = "testId"
    stompClient2.subscribe('/stream/candleData/barHistory', receiveTest, {id : id2});
    const body = {
        ticker: 'AAPL',
        tf: 'd1'
    }
    stompClient2.send("/app/candleData/barHistory", {},JSON.stringify(body));
}

const receiveTest = (payload) => {
    const message = JSON.parse(payload.body);
    console.log(message);
}












const onConnected = () => {
    stompClient.subscribe('/stream/public', onMessageReceived)
    stompClient.send("/app/chat.newUser",
        {},
        JSON.stringify({sender: username, type: 'CONNECT'})
    )
    const status = document.querySelector('#status')
    status.className = 'hide'
}

const onError = (error) => {
    const status = document.querySelector('#status')
    status.innerHTML = 'Could not find the connection you were looking for. Move along. Or, Refresh the page!'
    status.style.color = 'red'
}

const sendMessage = (event) => {
    const messageInput = document.querySelector('#message')
    const messageContent = messageInput.value.trim()

    if (messageContent && stompClient) {
        const chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            time: moment().calendar()
        }
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage))
        messageInput.value = ''
    }
    event.preventDefault();
}



const onMessageReceived = (payload) => {
    const message = JSON.parse(payload.body);

    const chatCard = document.createElement('div')
    chatCard.className = 'card-body'

    const flexBox = document.createElement('div')
    flexBox.className = 'd-flex justify-content-end mb-4'
    chatCard.appendChild(flexBox)

    const messageElement = document.createElement('div')
    messageElement.className = 'msg_container_send'

    flexBox.appendChild(messageElement)

    if (message.type === 'CONNECT') {
        messageElement.classList.add('event-message')
        message.content = message.sender + ' connected!'
    } else if (message.type === 'DISCONNECT') {
        messageElement.classList.add('event-message')
        message.content = message.sender + ' left!'
    } else {
        messageElement.classList.add('chat-message')

        const avatarContainer = document.createElement('div')
        avatarContainer.className = 'img_cont_msg'
        const avatarElement = document.createElement('div')
        avatarElement.className = 'circle user_img_msg'
        const avatarText = document.createTextNode(message.sender[0])
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender)
        avatarContainer.appendChild(avatarElement)

        messageElement.style['background-color'] = getAvatarColor(message.sender)

        flexBox.appendChild(avatarContainer)

        const time = document.createElement('span')
        time.className = 'msg_time_send'
        time.innerHTML = message.time
        messageElement.appendChild(time)

    }

    messageElement.innerHTML = message.content

    const chat = document.querySelector('#chat')
    chat.appendChild(flexBox)
    chat.scrollTop = chat.scrollHeight
}

const hashCode = (str) => {
    let hash = 0
    for (let i = 0; i < str.length; i++) {
       hash = str.charCodeAt(i) + ((hash << 5) - hash)
    }
    return hash
}


const getAvatarColor = (messageSender) => {
    const colours = ['#2196F3', '#32c787', '#1BC6B4', '#A1B4C4']
    const index = Math.abs(hashCode(messageSender) % colours.length)
    return colours[index]
}

const loginForm = document.querySelector('#login-form')
loginForm.addEventListener('submit', connect, true)
const messageControls = document.querySelector('#message-controls')
messageControls.addEventListener('submit', sendMessage, true)

const testEndpointButton = document.querySelector("#testEndpoint");
testEndpointButton.addEventListener("click",testEndpoint,true);