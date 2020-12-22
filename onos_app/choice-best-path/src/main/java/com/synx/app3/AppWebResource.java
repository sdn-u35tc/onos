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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.rest.AbstractWebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Get device and host.
 */
@Path("graph")
public class AppWebResource extends AbstractWebResource {
    private final GraphService GraphService = get(GraphService.class);

    /**
     * Get device and host.
     *
     * @return 200 OK
     */
    @GET
    public Response getGreeting() {
        ObjectNode node = mapper().createObjectNode();
        Map<String, List<String>> map = GraphService.getGraph();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            ArrayNode port = node.putArray(entry.getKey());
            for (String neighbor : entry.getValue()) {
                port.add(neighbor);
            }
        }
        return ok(node).build();
    }
}
