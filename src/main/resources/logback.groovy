import ch.qos.logback.classic.Level

statusListener(OnConsoleStatusListener)

// Gets the app log level from env var
def _level = System.getenv("APP_LOG_LEVEL")
_level = (_level != null ? _level.toUpperCase() : "INFO")

// Gets the log pattern from env var
def _pattern = System.getenv("APP_LOG_PATTERN")
_pattern = (_pattern != null ? _pattern :
	"%d{ISO8601} %-5level [%t] %c{5}: %msg%n%throwable")

def appenders = ["CONSOLE"]

appender("CONSOLE", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = _pattern
  }
}

root(Level.toLevel(_level), appenders)
