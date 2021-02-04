# Sample Makefile for the WACC Compiler lab: edit this to build your own compiler
# Tools

GRADLE	:= ./gradlew

# the make rules

all: rules

# runs the antlr build script then attempts to compile all .java files within src
rules:
	$(GRADLE) build

clean:
	$(GRADLE) clean

check:
	$(GRADLE) test

.PHONY: all rules clean check


