import socket
import os
import datetime
import signal
import sys
import select
from socket import *

BUFFER_SIZE = 1024
listOfSockets = []

def signal_handler(sig, frame):
    print('Interrupt received, shutting down ...')
    sys.exit(0)

def main():
    ##from socket import *
    namesSaved = [""];
    signal.signal(signal.SIGINT, signal_handler)
    serverSocket = socket(AF_INET, SOCK_STREAM)
    serverSocket.bind(('', 0))
    print("Will wait for chat clients at port "+ str(serverSocket.getsockname()[1]))
    print("Waiting for incoming chat clients... ")
    #nameHolder = "";
    #nameReceivedBeforeChat = serverSocket.recv(BUFFER_SIZE)
    #print("Connection to client established, waiting to receive message from user ", nameReceivedBeforeChat.decode())
    serverSocket.listen(1)
    count = 0
    while(1):
        #reading, writing = select.select(listOfSockets, [], 0)
        connectionSocket, addr = serverSocket.accept()
        
        #for i in listOfSockets.size():
         #   if addr == listOfSockets[i]:
         #   else if i == listOfSockets.size():
         #       listofSockets.append(addr)
        #listOfSockets.append(connectionSocket)
        print("Connection to client established, waiting to receive message from user ")
        #messageNotEmpty = eachSocket.recv(BUFFER_SIZE)
        #nameReceived = connectionSocket.recv(BUFFER_SIZE)
        print("COUNT:", count)
        #print("Waiting for incoming chat clients...")
        message = connectionSocket.recv(BUFFER_SIZE)
        #print("Accepted connection from client address: ", addr)
        #nameReceived = connectionSocket.recv(BUFFER_SIZE)
        #nameHolder = nameReceived.decode()
        #namesSaved.append(nameReceived.decode())
        #if (nameReceived.decode() not in  namesSaved):
         #   print("Connection to client established from", addr, "waiting for receive messages from user ", nameReceived.decode());
        #namesSaved.append(nameReceived.decode())
        print("Received Message from user ",addr, ": @",addr,": ", message.decode())
        #print(message.decode())
        #nameHolder = nameReceived.decode()
        #connectionSocket.send(message.decode().encode())
        ++count
        print("TEST");
        connectionSocket.send("HELLO".encode())
        #if (message.decode() == "DISCONNECT" or  nameReceived.decode() == "DISCONNECT"):
          # print("TESTING") 
        #connectionSocket.close()
        connectionSocket.close()



if __name__ == '__main__':
    main()

