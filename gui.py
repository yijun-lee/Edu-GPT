import tkinter
import tkinter.font
import fitz
# def show_text():
text=''
def pdf_to_text(pdf_path):
	document = fitz.open(pdf_path)
	global text
	text=''
	i=0
	for page in document:
		text += page.get_text()
		summary_text.configure(text=text)
		i=i+1
		if i==1:
			break
# pdf_to_text("02_C-FileIO(Korean)(1).pdf")
# print(text)
window=tkinter.Tk()

window.title("Edu-GPT")
window.geometry("600x1200")
window.resizable(False, True)


font = tkinter.font.Font(size=30)
font1 = tkinter.font.Font(family="arial", size=20)
head=tkinter.Label(window, text="Edu-GPT",height=3,font=font)
head.pack(side="top",pady=10)

paned_window1=tkinter.PanedWindow(relief="raised")
paned_window1.pack(side="top")

head_input = tkinter.Label(window, text="File : ",font=font1)
name_input = tkinter.Label(window, text="iphone.pdf",font=font1)
button_input = tkinter.Button(window, overrelief="solid",width=15,text="browse file")
paned_window1.add(head_input)
paned_window1.add(name_input)
paned_window1.add(button_input)
paned_window1.pack(side="top",pady=10)


paned_window2=tkinter.PanedWindow(relief="raised")
paned_window2.pack(side="top",pady=10)
button_summary = tkinter.Button(window, overrelief="solid",width=15,text="Generate Summary",pady=3,command=lambda:pdf_to_text("test123.pdf"))
label_summary_progress = tkinter.Label(window, text="processing...",height=0,fg="gray")
paned_window2.add(button_summary)
paned_window2.add(label_summary_progress)

summary_frame = tkinter.Frame(window, relief="solid",width = 550,height=200,bd=2,padx=0)
summary_frame.pack(side="top",pady=10)

button_save = tkinter.Button(summary_frame, text="save",height=0)
button_copy = tkinter.Button(summary_frame, text="copy",height=0)
button_reset = tkinter.Button(summary_frame, text="reset",height=0)

summary_text = tkinter.Label(summary_frame)
summary_head = tkinter.Label(summary_frame, text="summary", font=font1, fg="gray")

summary_head.place(x=0,y=3)
button_save.place(x=410,y=5)
button_copy.place(x=450,y=5)
button_reset.place(x=490,y=5)
summary_text.place(x=0,y=30)

paned_window4=tkinter.PanedWindow(relief="raised")
paned_window4.pack(side="top",pady=10)

button_exercise = tkinter.Button(window, overrelief="solid",width=15,text="Generate Exercise",pady=3)
label_exercise_progress = tkinter.Label(window, text="processing...",height=0,fg="gray")
paned_window4.add(button_exercise)
paned_window4.add(label_exercise_progress)

exercise_frame = tkinter.Frame(window, relief="solid",width = 550,height=200,bd=2,padx=0)
exercise_frame.pack(side="top",pady=10)

button_save = tkinter.Button(exercise_frame, text="save",height=0)
button_copy = tkinter.Button(exercise_frame, text="copy",height=0)
button_reset = tkinter.Button(exercise_frame, text="reset",height=0)

exercise_text = tkinter.Label(exercise_frame, text="ahiuhiufhaiu")
exercise_head = tkinter.Label(exercise_frame, text="exercise", font=font1, fg="gray")

exercise_head.place(x=0,y=3)
button_save.place(x=410,y=5)
button_copy.place(x=450,y=5)
button_reset.place(x=490,y=5)
exercise_text.place(x=0,y=30)

paned_window3=tkinter.PanedWindow(relief="raised")
paned_window3.pack(side="top",pady=10)

button_answer = tkinter.Button(window, overrelief="solid",width=15,text="Generate Answer",pady=3)
label_answer_progress = tkinter.Label(window, text="processing...",height=0,fg="gray")
paned_window3.add(button_answer)
paned_window3.add(label_answer_progress)

answer_frame = tkinter.Frame(window, relief="solid",width = 550,height=200,bd=2,padx=0)
answer_frame.pack(side="top",pady=10)

button_save = tkinter.Button(answer_frame, text="save",height=0)
button_copy = tkinter.Button(answer_frame, text="copy",height=0)
button_reset = tkinter.Button(answer_frame, text="reset",height=0)

answer_text = tkinter.Label(answer_frame, text="ahiuhiufhaiu")
answer_head = tkinter.Label(answer_frame, text="answer", font=font1, fg="gray")

answer_head.place(x=0,y=3)
button_save.place(x=410,y=5)
button_copy.place(x=450,y=5)
button_reset.place(x=490,y=5)
answer_text.place(x=0,y=30)

label_summary_output = tkinter.Label(window, text="processing...",height=0,fg="gray")
window.mainloop()


