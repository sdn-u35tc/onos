/*
 * Copyright 2020-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.synx.app3;

import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.onosproject.cli.AbstractShellCommand;

/**
 * Sample Apache Karaf CLI command.
 */
@Service
@Command(scope = "onos", name = "getGraph",
         description = "Graph information")
public class AppCommand extends AbstractShellCommand {
    private final GraphService graphService = get(GraphService.class);
    @Override
    protected void doExecute() {
        print(graphService.getGraph().toString());
    }

}
