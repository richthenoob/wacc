# Sample Makefile for the WACC Compiler lab: edit this to build your own compiler
# Tools

GRADLE	:= ./gradlew

# the make rules

all: rules

rules:
	$(GRADLE) build -x test

clean:
	$(GRADLE) clean

test:
	$(GRADLE) test

.PHONY: all rules clean test

