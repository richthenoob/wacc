# Use gradle wrapper to help compile run and test our code.
GRADLE	:= ./gradlew

all: build

# Build without testing. Also compiles a jar file to build/libs
build:
	$(GRADLE) build -x test

clean:
	$(GRADLE) clean

test:
	$(GRADLE) test

.PHONY: all build clean test

