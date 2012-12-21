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
 *
 */

package org.mongodb.command;

import org.mongodb.CommandDocument;
import org.mongodb.MongoCollection;
import org.mongodb.operation.MongoCommand;
import org.mongodb.operation.MongoFind;
import org.mongodb.result.CommandResult;

// TODO: deal with !ok responses where the reason is because the collection does not exist.
public class CountCommand extends AbstractCommand {
    private final MongoCollection collection;
    private final MongoFind find;

    public CountCommand(final MongoCollection collection, final MongoFind find) {
        super(collection.getDatabase());
        this.collection = collection;
        this.find = find;
    }

    @Override
    public CountCommandResult execute() {
        return new CountCommandResult(super.execute());
    }

    @Override
    public MongoCommand asMongoCommand() {
        final CommandDocument document = new CommandDocument("count", collection.getName());

        if (find.getFilter() != null) {
            document.put("query", find.getFilter().toDocument());
        }

        if (find.getLimit() > 0) {
            document.put("limit", find.getLimit());
        }

        if (find.getSkip() > 0) {
            document.put("skip", find.getSkip());
        }

        return document;
    }

    public static class CountCommandResult extends CommandResult {

        public CountCommandResult(final CommandResult baseResult) {
            super(baseResult);
        }

        public long getCount() {
            return ((Double) getResponse().get("n")).longValue();
        }
    }
}
