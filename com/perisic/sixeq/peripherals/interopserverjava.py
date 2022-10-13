# a simple python socket server listening and connecting the clients

import socket
import mysql.connector
from collections import Counter

so = socket.socket()

port = 49191

so.bind(('', port))  # no ip has been kept which helps this server to listen to the requests coming from other computers
so.listen()
print('Socket server started listening')

while True:
    c, addr = so.accept()
    print('Connection request from ', addr, ' accepted.')
    rmsg = c.recv(1024).decode("utf-8")
    z = rmsg.split(',')
    x = z[0]
    y = z[1]
   
    mydb = mysql.connector.connect(
      host="127.0.0.1",
      user="root",
      password="",
      database="game"
    )
    mycursor = mydb.cursor()

    sql = "UPDATE  player SET score = %s WHERE username = %s " 
    val = (x,y)
    mycursor.execute(sql,val)
    
    mydb.commit()

    print(mycursor.rowcount, "record inserted.")
    # modify this program to receive the message from the client
    c.close()
    break