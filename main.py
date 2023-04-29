import fitz
import tkinter
import tkinter.font

def pdf_to_text(pdf_path):
	document = fitz.open(pdf_path)
	text = []
	for page in document:
		text.append(page.get_text())
	return text
        
   



    

pdf_path = "11_Gamma.pdf"
#text = pdf_to_text(pdf_path)
# for i in text:
# 	print(i)
#gui code
window=tkinter.Tk()

window.title("Edu-GPT")
window.geometry("800x1600")
window.resizable(False, True)

font = tkinter.font.Font(family="arial", size=30)
font1 = tkinter.font.Font(family="arial", size=20)
head=tkinter.Label(window, text="Edu-GPT",height=3,font=font)
head.pack(side="top")

paned_window1=tkinter.PanedWindow(relief="raised")
paned_window1.pack(side="top")

head_input = tkinter.Label(window, text="File : ",font=font1)
button_input = tkinter.Button(window, overrelief="solid",width=15)
paned_window1.add(head_input)
paned_window1.add(button_input)
# paned_window1.pack(side="top",fill="x")

window.mainloop()
