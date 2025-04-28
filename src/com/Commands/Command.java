package com.Commands;

import java.io.IOException;

public interface Command {
    void execute() throws IOException;
    boolean undo();
}
