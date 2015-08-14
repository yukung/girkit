/*
 * Copyright 2015 Yusuke Ikeda
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

package org.yukung.girkit

import spock.lang.IgnoreIf
import spock.lang.Specification


/**
 * @author yukung
 */
class DeviceSpec extends Specification {

    @IgnoreIf({ env.CI })
    def "should find IRKit within the same LAN"() {
        when:
        def devices = Device.find()

        then:
        devices.class == ArrayList

        and:
        devices.each { device ->
            device.class == Device
        }
    }

    def "should create device instance from Finder"() {
        given:
        GroovyMock(Device, global: true)
        Device.find() >> [new Device(Inet4Address.getByName('192.168.0.10'), 'irkitd2a4')]

        when:
        def device = Device.find().first()

        then:
        device.class == Device

        and:
        device.format == 'raw'

        and:
        device.freq == 38

        and;
        device.data.class == ArrayList
    }
}