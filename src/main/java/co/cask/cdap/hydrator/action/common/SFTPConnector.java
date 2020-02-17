/*
 * Copyright © 2017 Cask Data, Inc.
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

package co.cask.cdap.hydrator.action.common;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

/**
 * Class to connect to SFTP server.
 */
public class SFTPConnector implements AutoCloseable {
  private static final Logger LOG = LoggerFactory.getLogger(SFTPConnector.class);
  private final Session session;
  private final Channel channel;
  private JSch jSch;

//  public Session createSession(String host, int port, String username, String password, Map<String, String> sessionProperties) throws JSchException {
//    if(jSch == null) {
//      jSch = new JSch();
//    }
//    this.session = jSch.getSession(username, host, port);
//  }

//  public SFTPConnector(String host, int port, String username, String password, boolean proxyEnabled, Map<String, String> sessionProperties) {
//
//  }

  public SFTPConnector(String host, int port, String userName, String password, Map<String, String> sessionProperties)
    throws Exception {
    JSch jsch = new JSch();
    this.session = jsch.getSession(userName, host, port);
    session.setPassword(password);
    LOG.info("Properties {}", sessionProperties);
    Properties properties = new Properties();
    // properties.put("StrictHostKeyChecking", "no");
    properties.putAll(sessionProperties);
    session.setConfig(properties);
    LOG.info("Connecting to Host: {}, Port: {}, with User: {}", host, port, userName);
    session.connect(30000);

//    if()
//    int assignedPort = session.setPortForwardingL(0, host, port);

    channel = session.openChannel("sftp");
    channel.connect();
  }

  /**
   * Get the established sftp channel to perform operations.
   */
  public ChannelSftp getSftpChannel() {
    return (ChannelSftp) channel;
  }

  @Override
  public void close() throws Exception {
    LOG.info("Closing SFTP session.");
    if (channel != null) {
      try {
        channel.disconnect();
      } catch (Throwable t) {
        LOG.warn("Error while disconnecting sftp channel.", t);
      }
    }

    if (session != null) {
      try {
        session.disconnect();
      } catch (Throwable t) {
        LOG.warn("Error while disconnecting sftp session.", t);
      }
    }
  }
}
