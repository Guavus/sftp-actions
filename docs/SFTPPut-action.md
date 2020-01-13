SFTP Put
========

SFTP Put is a Guavus Enterprise Accelerator that is used for uploading of file(s) from filesystem (local/HDFS) to an FTP server. File Regex can be used to filter the files of interest.


Usage Notes
-----------
In order to perform SFTP Put, we require a host and port on which the SFTP server is running. SFTP implements a secure file
transfer over SSH. Typically, port number 22 is used for SFTP (which is also the default port for SSH). We also require valid
credentials in the form of user name and password. Please make sure that you are able to connect SSH to the SFTP server using
specified user and password. SSH connection to SFTP server can be customized by providing additional configurations
such as by enabling host key checking by setting configuration property 'StrictHostKeyChecking' to 'yes'. These additional
configurations can be specified using `Properties for SSH` section.

Directory/File on the Filesystem which needs to be copied can be specified using `Source Path` property. The specified path should exist. `Destination directory` is the absolute path of the directory on the FTP Server where the files will be copied. If a destination directory does not exist, then it will be created first.

Plugin Configuration
--------------------

| Configuration | Required | Default | Description |
| :------------ | :------: | :----- | :---------- |
| **Host** | **Y** | N/A | Specify the host name of the SFTP server.|
| **Port** | **N** | 22 | Specify the port on which SFTP server is running.|
| **User** | **Y** | N/A | Specify the name of the user which will be used to connect to the SFTP server.|
| **Password** | **Y** | N/A | Specify the password of the user.|
| **Source Path** | **Y** | N/A | The path of file or directory on the file system which is to be copied.|
| **Destination Directory** | **Y** | N/A | Destination directory on the FTP Server, where files need to be copied. If directory does not exist, it will lbe created.|
| **File Name Regex** | **N** | .* | Regex to choose only the files that are of interest. All files will be copied by default.|
| **Properties for SSH** | **N** | N/A | Specify the properties that are used to configure an SSH connection to the FTP server. For example, to enable verbose logging, add property 'LogLevel' with value 'VERBOSE'. To enable host key checking, set 'StrictHostKeyChecking' to 'yes'. SSH can be configured with the properties described here https://linux.die.net/man/5/ssh_config. |


Example
--------
```json
{
   "name": "SFTPPut",
   "plugin": {
      "name": "SFTPPut",
      "type": "action",
      "label": "SFTPPut",
      "artifact": {
      "name": "sftp-actions",
      "version": "1.3.0",
      "scope": "USER"
      },
      "properties": {
      "host": "192.168.192.123",
      "port": "22",
      "userName": "dev",
      "password": "dev@123",
      "srcPath": "/tmp/dummy.csv",
      "destDirectory": "/tmp"
      }
   }
}
```