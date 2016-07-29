sourcefiles = \
PicrossDetective.java \
PicrossPuzzle.java \
PicrossCanvas.java \
PicrossFileHandler.java

classfiles = $(sourcefiles:.java=.class)

default: $(classfiles)
%.class: %.java
	javac -d . -classpath . $<

clean :
	rm -f *.class
	rm -f *.jar