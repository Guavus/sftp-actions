/*
 * Copyright © 2016 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.cdap.hydrator.action;
import co.cask.cdap.hydrator.action.common.SFTPConnector;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SFTPConnectorTest {
    public void test() throws Exception {
        JSch jsch = new JSch();
        String host = "192.168.192.201";
        int port = 22;
        String username = "root";
        String password = "Gu@vu$12#";
        Session session1;
        Session session2;
        Properties props = new Properties();
        props.put("StrictHostKeyChecking", "false");
        props.put("LogLevel", "VERBOSE");

        Session session = session1 = jsch.getSession(username, host, port);
        session.setConfig(props);
        session.setPassword("Gu@vu$12#");
        session.connect(30000);
        System.out.println("Session1 created");

        int assigendPort = session.setPortForwardingL(0, "platacc004-slv-03.cloud.in.guavus.com", 22);
        session = session2 = jsch.getSession("guavus", "127.0.0.1", assigendPort);
        session.setPassword("guavus@123");
        session.setConfig(props);
        session.connect(30000);
        System.out.println("Session created again.");

        ChannelSftp sftp1 = (ChannelSftp) session.openChannel("sftp");
        sftp1.connect();

        sftp1.ls("/tmp/nlsh",
                new ChannelSftp.LsEntrySelector() {
                    public int select(ChannelSftp.LsEntry le) {
                        System.out.println(le);
                        return ChannelSftp.LsEntrySelector.CONTINUE;
                    }
                });

        session1.disconnect();
        session2.disconnect();
    }

    public static void main(String[] args) throws Exception {
        SFTPConnectorTest test = new SFTPConnectorTest();
        test.test();
    }
}
