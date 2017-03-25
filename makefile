# Richard Riedel
# Makefile for Java
#

JC = javac
JFLAGS = -g -Xlint -Xdiags:verbose
CP = -cp commons-cli-1.4/commons-cli-1.4.jar:.:..
SOURCES = DougTheRipper.java
DOUG_PATH = Doug/
DOUG_SOURCES = $(DOUG_PATH)DougDict.java \
			   $(DOUG_PATH)DougHash.java \
			   $(DOUG_PATH)DougLog.java \
			   $(DOUG_PATH)DougSmasher.java


all: $(SOURCES) $(DOUG)
	$(JC) $(JFLAGS) $(CP) $(SOURCES)
	$(JC) $(JFLAGS) $(CP) $(DOUG_SOURCES)

rip: $(SOURCES)
	$(JC) $(JFLAGS) $(CP) $(SOURCES)

doug: $(DOUG_SOURCES)
	$(JC) $(JFLAGS) $(CP) $(DOUG_SOURCES)

clean:
	rm *.class Doug/*.class
