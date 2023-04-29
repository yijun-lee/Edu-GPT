import fitz
import tkinter

def pdf_to_text(pdf_path):
	document = fitz.open(pdf_path)
	text = []
	for page in document:
		text.append(page.get_text())
	return text
        
   



    

pdf_path = "11_Gamma.pdf"
text = pdf_to_text(pdf_path)
# for i in text:
# 	print(i)
#gui code
window=tkinter.Tk()

window.title("Edu-GPT")
window.geometry("800x1600+100+100")
window.resizable(False, True)

label=tkinter.Label(window, text="Edu-GPT")
label.pack()

window.mainloop()
