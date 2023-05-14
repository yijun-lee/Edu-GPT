import openai
import fitz
import tkinter as tk
from tkinter import font, filedialog, messagebox
import pyperclip
import threading
import time
import os

openai.api_key = "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"


def pdf_to_text(pdf_path):
	document = fitz.open(pdf_path)
	text = ''
	for page in document:
		text += page.get_text()
	return text


def choose_file():
	filepath = filedialog.askopenfilename()
	if filepath.endswith('.pdf'):
		file_path_var.set(filepath)
		summary_text.delete(1.0, tk.END)
		translate_text.delete(1.0, tk.END)
		exercise_text.delete(1.0, tk.END)
		file_name_var.set(os.path.basename(filepath))

	else:
		messagebox.showerror(
		    title='Error', message='Please select valid file extension.')


def generate_summary(text):
	words = text.split()
	# max_words = 1000
	summary_output = ''
	token = len(words)
	split_cnt = token // 1500
	for i in range(1, split_cnt + 1):
		prompt = " ".join(words[(i-1) * 1500:1500 * i])
		response = openai.Completion.create(
		model="text-davinci-003",
			prompt=f"Summarize this: {prompt}",
			temperature=0.9,
			max_tokens=300,
			top_p=1.0,
			frequency_penalty=0.0,
			presence_penalty=0.0
		)
		summary_output += response.choices[0].text
	return summary_output

def generate_translate(text):
	words = text.split()
	max_words = 2000
	prompt = " ".join(words[:max_words])
	response = openai.Completion.create(
        model="text-davinci-003",
        prompt=f"Translate this in Korean: {prompt}", #Korean -> Other Languages
        temperature=0.9,
        max_tokens=2000,
        top_p=1.0,
        frequency_penalty=0.0,
        presence_penalty=0.0
    )
	translate_output = response.choices[0].text
	return translate_output

def generate_question(text):
	words = text.split()
	max_words = 1000
	prompt = " ".join(words[:max_words])
	response = openai.Completion.create(
        model="text-davinci-003",
        prompt=f"Make 5 exercises about the text: {prompt}",
        temperature=0.9,
        max_tokens=1000,
        top_p=1.0,
        frequency_penalty=0.0,
        presence_penalty=0.0
    )
	question_output = response.choices[0].text
	return question_output

def execute_summary():
	file_path = file_path_var.get()
	if file_path:
		text = pdf_to_text(file_path)
		summary = generate_summary(text)
		summary_text.insert(tk.END, summary)
	else:
		messagebox.showerror(title = 'Error', message = 'Please select file.')

def execute_translate():
	file_path = file_path_var.get()
	if file_path:
		text = pdf_to_text(file_path)
		translate = generate_translate(text)
		translate_text.insert(tk.END, translate)
	else:
		messagebox.showerror(title = 'Error', message = 'Please select file.')

def execute_question():
	file_path = file_path_var.get()
	if file_path:
		text = pdf_to_text(file_path)
		question = generate_question(text)
		exercise_text.insert(tk.END, question)
	else:
		messagebox.showerror(title = 'Error', message = 'Please select file.')
        
# def generate_answers:

# def copy_output

# def download_output

# def regenerate_output



    






#--------------gui--------------
window=tk.Tk()

text_summary = tk.StringVar()
text_translate = tk.StringVar()
text_question = tk.StringVar()
file_path_var = tk.StringVar()
file_name_var = tk.StringVar()

window.title("Edu-GPT")
window.geometry("600x1400")
window.resizable(True, True)

font1 = font.Font(family="arial", size=20)
font2 = font.Font(size=30)
font3 = font.Font(family="arial", size=10)

head=tk.Label(window, text="Edu-GPT",height=3,font=font2)
head.pack(side="top",pady=10)

paned_window1=tk.PanedWindow(relief="raised")
paned_window1.pack(side="top")

head_input = tk.Label(window, text="File : ",font=font1)
name_input = tk.Label(window, textvariable = file_name_var, font=font3, width=30)
button_input = tk.Button(window, overrelief="solid",width=7,text="browse file", command=lambda:choose_file())
paned_window1.add(head_input)
paned_window1.add(name_input)
paned_window1.add(button_input)
paned_window1.pack(side="top",padx=10,pady=10)

#--------------------summary-------------------------
paned_window2=tk.PanedWindow(relief="raised")
paned_window2.pack(side="top",pady=10)
button_summary = tk.Button(window, overrelief="solid",width=15,text="Generate Summary",pady=3,command=lambda:execute_summary())
label_summary_progress = tk.Label(window, text="processing...",height=0,fg="gray")
paned_window2.add(button_summary)
paned_window2.add(label_summary_progress)

summary_frame = tk.Frame(window, relief="solid",width = 550,height=200,bd=2,padx=0)
summary_frame.pack(side="top",pady=10)

button_save = tk.Button(summary_frame, text="save",height=0)
button_copy = tk.Button(summary_frame, text="copy",height=0)
button_refresh = tk.Button(summary_frame, text="refresh",height=0)

# summary_text = tk.Label(summary_frame, textvariable=text_summary)
summary_text = tk.Text(summary_frame,width=76, height=10)
summary_head = tk.Label(summary_frame, text="summary", font=font1, fg="gray")

summary_head.place(x=0,y=3)
button_save.place(x=410,y=5)
button_copy.place(x=450,y=5)
button_refresh.place(x=490,y=5)
summary_text.place(x=0,y=30)

#--------------------translate--------------------
paned_window5=tk.PanedWindow(relief="raised")
paned_window5.pack(side="top",pady=10)

button_translate = tk.Button(window, overrelief="solid",width=15,text="Generate Translate",pady=3,command=lambda:execute_translate())
label_translate_progress = tk.Label(window, text="processing...",height=0,fg="gray")
paned_window5.add(button_translate)
paned_window5.add(label_translate_progress)

translate_frame = tk.Frame(window, relief="solid",width = 550,height=200,bd=2,padx=0)
translate_frame.pack(side="top",pady=10)

button_save = tk.Button(translate_frame, text="save", height=0)
button_copy = tk.Button(translate_frame, text="copy", height=0)
button_refresh = tk.Button(translate_frame, text="refresh", height=0)

translate_text = tk.Text(translate_frame, width=76, height=10)
translate_head = tk.Label(translate_frame, text="translate", font=font1, fg="gray")

translate_head.place(x=0, y=3)
button_save.place(x=410, y=5)
button_copy.place(x=450, y=5)
button_refresh.place(x=490, y=5)
translate_text.place(x=0, y=30)

#--------------------exercise--------------------
paned_window4=tk.PanedWindow(relief="raised")
paned_window4.pack(side="top",pady=10)

button_exercise = tk.Button(window, overrelief="solid",width=15,text="Generate Exercise", pady=3, command=lambda:execute_question())
label_exercise_progress = tk.Label(window, text="processing...",height=0,fg="gray")
paned_window4.add(button_exercise)
paned_window4.add(label_exercise_progress)

exercise_frame = tk.Frame(window, relief="solid",width = 550,height=200,bd=2,padx=0)
exercise_frame.pack(side="top",pady=10)

button_save = tk.Button(exercise_frame, text="save",height=0)
button_copy = tk.Button(exercise_frame, text="copy",height=0)
button_refresh = tk.Button(exercise_frame, text="refresh",height=0)

exercise_text = tk.Text(exercise_frame, width=76, height=10)
exercise_head = tk.Label(exercise_frame, text="exercise", font=font1, fg="gray")

exercise_head.place(x=0,y=3)
button_save.place(x=410,y=5)
button_copy.place(x=450,y=5)
button_refresh.place(x=490,y=5)
exercise_text.place(x=0,y=30)

paned_window3=tk.PanedWindow(relief="raised")
paned_window3.pack(side="top",pady=10)

button_answer = tk.Button(window, overrelief="solid",width=15,text="Generate Answer",pady=3)
label_answer_progress = tk.Label(window, text="processing...",height=0,fg="gray")
paned_window3.add(button_answer)
paned_window3.add(label_answer_progress)

answer_frame = tk.Frame(window, relief="solid",width = 550,height=200,bd=2,padx=0)
answer_frame.pack(side="top",pady=10)

button_save = tk.Button(answer_frame, text="save", height=0)
button_copy = tk.Button(answer_frame, text="copy", height=0)
button_refresh = tk.Button(answer_frame, text="refresh", height=0)

answer_text = tk.Text(answer_frame, width=76, height=10)
answer_head = tk.Label(answer_frame, text="answer", font=font1, fg="gray")

answer_head.place(x=0, y=3)
button_save.place(x=410, y=5)
button_copy.place(x=450, y=5)
button_refresh.place(x=490, y=5)
answer_text.place(x=0, y=30)

window.mainloop()
#---------------------gui----------------------

# test
# choose_file()
# print(file_path_var.get())
# target = pdf_to_text(file_path_var.get())
# result = generate_questions(target)
# print(result)