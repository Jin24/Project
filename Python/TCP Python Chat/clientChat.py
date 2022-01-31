import socket
import argparse
from sys import stdin
from socket import *
from signal import signal, SIGPIPE, SIG_DFL
BUFFER_SIZE = 1024
signal(SIGPIPE, SIG_DFL)

def signal_handler(sig, frame):
    print("Interrupt received, shutting down...")
    disconnectMessage = "DISCONNECT"
    clientSocket.send(disconnectMessage.encode())
    clientSocket.close()

def main():
    print("Enter Chat Name:")
    chatName = input()
    print("Enter Port Number:")
    port = int(input())
    host = 'localhost'
    clientSocket = socket(AF_INET, SOCK_STREAM)
    clientSocket.connect((host,port))
    #clientSocket.send(chatName.encode())
    #`clientSocket.send(chatName.encode())
    #parser = argparse.ArgumentParser()
    #parser.add_argument("host", help= "Host name of server")
    #parser.add_argument("port", help= "Port number of server")
    #args = parser.parse_args()
    #host = args.host
    #port = int(args.port)
    print("Registration successful. Ready for messaging!")
    #for line in stdin:
    count = 0
    while (1):
        #if(count != 0):
         #    reply =clientSocket.recv(BUFFER_SIZE)
         #    print(">",reply.decode()) 

        #clientSocket.send(chatName.encode())
        enteredSentence = input();
        #clientSocket.
        clientSocket.send(enteredSentence.encode())
        #clientSocket.send(chatName.encode())
        ++count
        reply = clientSocket.recv(BUFFER_SIZE)
        print(reply.decode())
        #clientSocket.close()
    #clientSocket.close()

if __name__ == '__main__':
    main()

