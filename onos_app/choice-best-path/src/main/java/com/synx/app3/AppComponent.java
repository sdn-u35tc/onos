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


import org.onosproject.net.Device;
import org.onosproject.net.Link;
import org.onosproject.net.Port;
import org.onosproject.net.device.DeviceEvent;
import org.onosproject.net.device.DeviceListener;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.host.HostEvent;
import org.onosproject.net.host.HostListener;
import org.onosproject.net.host.HostService;
import org.onosproject.net.link.LinkEvent;
import org.onosproject.net.link.LinkListener;
import org.onosproject.net.link.LinkService;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component(immediate = true,
        service = {GraphService.class}
)
public class AppComponent implements GraphService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected DeviceService deviceService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected LinkService linkService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected HostService hostService;

    private final DeviceListener deviceListener = new InternalDeviceListener();

    private final LinkListener linkListener = new InternalLinkListener();

    private final HostListener hostListener = new InternalHostListener();

    @Activate
    protected void activate() {
        log.info("Started");

        linkService.addListener(linkListener);
        deviceService.addListener(deviceListener);
        hostService.addListener(hostListener);
//        getDeviceAndHost();
    }

    @Deactivate
    protected void deactivate() {

        linkService.removeListener(linkListener);
        deviceService.removeListener(deviceListener);
        hostService.removeListener(hostListener);
        log.info("Stopped");
    }


    @Override
    public Map<String, List<String>> getGraph() {
        Iterable<Link> links = linkService.getLinks();
        Iterable<Device> devices = deviceService.getDevices();
        List<Port> result = new ArrayList<>();
        for(Device device:devices){
             List<Port> temp=deviceService.getPorts(device.id());
             result.addAll(temp);
        }
        Map<String,List<String>> ports= new HashMap<>();
//        deviceService.getDevices().forEach(device -> {
//            Optional<List<Port>> list = Optional.ofNullable(deviceService.getPorts(device.id()));
//            list.ifPresent(result::addAll);
//        });
        for(Port port: result){
            if(port.isEnabled()){
                List <String> neighbors = new ArrayList<>();
                ports.put(port.element().id().toString()+"/"+port.number().toString(),neighbors);
            }
        }
        for(String port1:ports.keySet()){
            for(String port2:ports.keySet()){
                if(!port1.equals(port2) && (port1.substring(0,19).equals(port2.substring(0,19)))){
                    ports.get(port1).add(port2);
                }
            }
        }
        for(Link link:links){
            ports.get(link.src().toString()).add(link.dst().toString());
        }

        return ports;
    }

    private class InternalLinkListener implements LinkListener{
        @Override
        public void event(LinkEvent event){
            LinkEvent.Type type =event.type();
            if(type==LinkEvent.Type.LINK_ADDED ||type==LinkEvent.Type.LINK_REMOVED||type==LinkEvent.Type.LINK_UPDATED){
                log.info(getGraph().toString());
            }
        }
    }

    private class InternalHostListener implements HostListener{
        @Override
        public void event(HostEvent event){
            HostEvent.Type type =event.type();
            if(type==HostEvent.Type.HOST_ADDED ||type==HostEvent.Type.HOST_REMOVED||type==HostEvent.Type.HOST_UPDATED||type==HostEvent.Type.HOST_MOVED){
                log.info(getGraph().toString());
            }
        }
    }

    private class InternalDeviceListener implements DeviceListener {
        @Override
        public void event(DeviceEvent event) {
            DeviceEvent.Type type = event.type();
            if (type == DeviceEvent.Type.DEVICE_ADDED || type == DeviceEvent.Type.DEVICE_REMOVED) {
                log.info(getGraph().toString());
            }
        }
    }

}
