JCC = javac
.SUFFIXES: .java .class
.java.class:
        $(JCC) $*.java

CLASSES = \
        PicrossDetective.java \
        PicrossPuzzle.java \
        PicrossCanvas.java \
        PicrossFileHandler.java

default : classes
    jar cvf picrossDetective.jar $(classes)

clean :
    rm -f *.class