package com.shim.user.shimapplication.util.logging;

import java.security.InvalidParameterException;

public enum LogEvent {
    ASMR_PAUSE {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("ASMR Music(id: %s) is paused.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    ASMR_PLAY {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("ASMR Music(id: %s) is playing.", args[0]);
            } else if (args.length == 2) {
                return String.format("ASMR Music(id: %s) starts at %sms.", args[0], args[1]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    ASMR_STOP {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("ASMR Music(id: %s) is stopped.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    HOME_MUSIC_PLAY {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Home Music(id: %s) is playing.", args[0]);
            } else if (args.length == 2) {
                return String.format("Music(id: %s) starts at %sms.", args[0], args[1]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    HOME_MUSIC_STOP {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Home Music(id: %s) is stopped.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    MUSIC_PAUSE {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Music(id: %s) is paused.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    MUSIC_PLAY {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Music(id: %s) is playing.", args[0]);
            } else if (args.length == 2) {
                return String.format("Music(id: %s) starts at %sms.", args[0], args[1]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    MUSIC_STOP {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Music(id: %s) is stopped.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    PAGE_MOVE {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Moved to %s Page.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    PLAYLIST_ADD {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Music(id: %s) is added to the playlist.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    },
    PLAYLIST_REMOVE {
        @Override
        String toStringWith(String... args) {
            if (args.length == 1) {
                return String.format("Music(id: %s) is removed from the playlist.", args[0]);
            } else {
                throw new InvalidParameterException();
            }
        }
    };

    abstract String toStringWith(String... args);
}
