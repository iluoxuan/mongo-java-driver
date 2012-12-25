/*
 * Copyright (c) 2008 - 2012 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.command;

import org.mongodb.MongoException;
import org.mongodb.result.CommandResult;

/**
 * Exception thrown when a command fails.
 */
public class MongoCommandException extends MongoException {
    private static final long serialVersionUID = -50109343643507362L;

    private final CommandResult commandResult;

    public MongoCommandException(final CommandResult commandResult) {
        super("Command failed with error code " + commandResult.getErrorCode() + " and error message '" +
                      commandResult.getErrorMessage() + "' on server " + commandResult.getAddress(),
              commandResult.getAddress());
        this.commandResult = commandResult;
    }

    public CommandResult getCommandResult() {
        return commandResult;
    }
}