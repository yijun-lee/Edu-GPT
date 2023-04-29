import openai
import fitz
import tkinter
import tkinter.font
import pyperclip
import threading 
import time

openai.api_key =""

def pdf_to_text(pdf_path):
	document = fitz.open(pdf_path)
	text = ''
	for page in document:
		text += page.get_text()
	return text

def choose_file

def generate_summary(text):
	response = openai.Completion.create(
		engine="davinci",
		prompt=text,
		temperature=0.3,
		max_tokens=100,
		top_p=1,
		frequency_penalty=0,
		presence_penalty=0.5,
		stop=["\n", " Student: ", " Teacher: "]
	)
	summary = response['choices'][0]['text']
	return summary

def generate_translate

def generate_questions(text):
	response = openai.Completion.create(
		engine="davinci",
		prompt=text,
		temperature=0.3,
		max_tokens=100,
		top_p=1,
		frequency_penalty=0,
		presence_penalty=0.5,
		stop=["\n", " Student: ", " Teacher: "]
	)
	exercises = response['choices'][0]['text']
	return exercises
        
def generate_answers

def copy_output

def download_output

def regenerate_output



    

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
