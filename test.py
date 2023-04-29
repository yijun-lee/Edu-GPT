from tkinter import *

# create the main window
root = Tk()
root.title("My Application")

# create a canvas widget
canvas = Canvas(root)
canvas.pack(side=LEFT, fill=BOTH, expand=TRUE)

# add a scrollbar to the canvas
scrollbar = Scrollbar(root, command=canvas.yview)
scrollbar.pack(side=RIGHT, fill=Y)

# configure the canvas to work with the scrollbar
canvas.configure(yscrollcommand=scrollbar.set)

# create a frame to hold the label
frame = Frame(canvas)

# add the frame to the canvas
canvas.create_window((0,0), window=frame, anchor='nw')

# create the label with long text
long_text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec faucibus lorem vel ex elementum, vel lacinia neque ultrices. Aliquam vitae magna sit amet odio maximus consectetur. Praesent mollis sapien quis ipsum blandit tincidunt. Donec euismod mi ac felis efficitur sagittis. Nam sollicitudin libero sed mauris suscipit, a tincidunt quam facilisis. Cras volutpat ante ut ipsum iaculis, sed pharetra nunc volutpat. Nulla eget justo non lorem feugiat tincidunt. Sed auctor tincidunt mauris, eget accumsan lacus vehicula vel. Maecenas dapibus velit sed lacus maximus, sit amet varius magna volutpat. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Pellentesque eget risus vitae risus dignissim hendrerit vel quis est. Donec eu odio finibus, ultrices libero a, viverra ante. Suspendisse sed suscipit libero, non vulputate ipsum. "
my_label = Label(frame, text=long_text, wraplength=300, justify=LEFT)

# add the label to the frame
my_label.pack()

# configure the canvas to fit the contents
frame.bind("<Configure>", lambda e: canvas.configure(scrollregion=canvas.bbox("all")))

# run the main loop
root.mainloop()
#test