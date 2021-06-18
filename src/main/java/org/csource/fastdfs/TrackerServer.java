/**
 * Copyright (C) 2008 Happy Fish / YuQing
 * <p>
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page https://github.com/happyfish100/fastdfs for more detail.
 */

package org.csource.fastdfs;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.csource.common.MyException;
import org.csource.fastdfs.pool.Connection;
import org.csource.fastdfs.pool.ConnectionFactory;
import org.csource.fastdfs.pool.ConnectionPool;

/**
 * Tracker Server Info
 *
 * @author Happy Fish / YuQing
 * @version Version 1.11
 */
public class TrackerServer {
    protected InetSocketAddress inetSockAddr;


    public TrackerServer(InetSocketAddress inetSockAddr) throws IOException {
        final String key = inetSockAddr.getHostName() + ":" + inetSockAddr.getPort();
        if (ClientGlobal.address_mapping.containsKey(key)) {
            final String v = ClientGlobal.address_mapping.get(key);
            String[] s = v.split(":");
            if (s.length > 1) {
                this.inetSockAddr = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
            } else {
                throw new IOException("config error: " + key + "=" + v);
            }
        } else {
            this.inetSockAddr = inetSockAddr;
        }
    }

    public Connection getConnection() throws MyException, IOException {
        Connection connection;
        if (ClientGlobal.g_connection_pool_enabled) {
            connection = ConnectionPool.getConnection(this.inetSockAddr);
        } else {
            connection = ConnectionFactory.create(this.inetSockAddr);
        }
        return connection;
    }

    /**
     * get the server info
     *
     * @return the server info
     */
    public InetSocketAddress getInetSocketAddress() {
        return this.inetSockAddr;
    }

}
