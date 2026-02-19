# ===== Java RMI Stable Marriage - Makefile (macOS/Linux) =====

JAVAC   := javac
JAVA    := java

# Add flags if you want (optional)
JAVAC_FLAGS := -Xlint:unchecked -Xlint:deprecation

# Main class
MAIN := Driver

# Find all java sources in current folder
SRC := $(wildcard *.java)
CLS := $(SRC:.java=.class)

.PHONY: all run clean rebuild kill1099 status

all: $(CLS)

%.class: %.java
	$(JAVAC) $(JAVAC_FLAGS) $<

run: all
	$(JAVA) $(MAIN)

clean:
	rm -f *.class

rebuild: clean all

# Kill anything listening on TCP port 1099 (RMI registry default)
kill1099:
	@PIDS=$$(lsof -ti tcp:1099); \
	if [ -z "$$PIDS" ]; then \
		echo "No process is using port 1099."; \
	else \
		echo "Killing process(es) on port 1099: $$PIDS"; \
		kill -9 $$PIDS; \
	fi

# Show what is using port 1099 (if anything)
status:
	@lsof -nP -iTCP:1099 -sTCP:LISTEN || true
