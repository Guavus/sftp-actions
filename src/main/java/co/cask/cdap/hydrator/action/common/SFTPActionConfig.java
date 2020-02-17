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

import co.cask.cdap.api.annotation.Description;
import co.cask.cdap.api.annotation.Macro;
import co.cask.cdap.api.dataset.lib.KeyValue;
import co.cask.cdap.api.plugin.PluginConfig;
import co.cask.hydrator.common.KeyValueListParser;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * Common configurations for the FTP Action plugins.
 */
public class SFTPActionConfig extends PluginConfig {
  @Description("Host name of the SFTP server.")
  @Macro
  public String host;

  @Description("Port on which SFTP server is running. Defaults to 22.")
  @Nullable
  @Macro
  public String port;

  @Description("Name of the user used to login to SFTP server.")
  @Macro
  public String userName;

  @Description("Password used to login to SFTP server.")
  @Macro
  public String password;

  @Description("Properties that will be used to configure the SSH connection to the FTP server. " +
    "For example to enable verbose logging add property 'LogLevel' with value 'VERBOSE'. " +
    "To enable host key checking set 'StrictHostKeyChecking' to 'yes'. " +
    "SSH can be configured with the properties described here 'https://linux.die.net/man/5/ssh_config'.")
  @Nullable
  public String sshProperties;

  @Macro
  @Nullable
  @Description("Enables proxy configuration. User will be asked to provide additional" +
          "proxy server details like host, port and credentials. Sftp connection will be made first by connecting to proxy server and then to actual sftp server.")
  public String enableProxy;

  @Macro
  @Nullable
  @Description("Host of proxy server. Connection to sftp server will be made through this host")
  public String proxyHost;

  @Macro
  @Nullable
  @Description("User to connect to proxy server.")
  public String proxyUsername;

  @Macro
  @Nullable
  @Description("Password to connect to proxy server.")
  public String proxyPassword;

  public String getHost() {
    return host;
  }

  public int getPort() {
    return (port != null) ? Integer.parseInt(port) : 22;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public Map<String, String> getSSHProperties(){
    Map<String, String> properties = new HashMap<>();
    // Default set to no
    properties.put("StrictHostKeyChecking", "no");
    if (sshProperties == null || sshProperties.isEmpty()) {
      return properties;
    }

    KeyValueListParser kvParser = new KeyValueListParser("\\s*,\\s*", ":");
    for (KeyValue<String, String> keyVal : kvParser.parse(sshProperties)) {
      String key = keyVal.getKey();
      String val = keyVal.getValue();
      properties.put(key, val);
    }
    return properties;
  }

  @Nullable
  public boolean getEnableProxy() {
    return Boolean.valueOf(enableProxy);
  }

  @Nullable
  public String getProxyHost() {
    return proxyHost;
  }

  @Nullable
  public String getProxyUsername() {
    return proxyUsername;
  }

  @Nullable
  public String getProxyPassword() {
    return proxyPassword;
  }
}
