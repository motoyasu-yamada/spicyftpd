package spicyftpd.cmd;

import java.io.File;
import java.net.InetAddress ;
import java.net.ServerSocket;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   パッシブモードへの移行を指示する。
*   サーバはデータコネクションで使用するIPアドレスとポート番号を表示する 
*
*   構文:   PORT 
*   応答形式:
*       ip1,ip2,ip3,ip4,port1,port2
*/
public class Cmd_PASV implements Cmd {

    /**
    *   コマンドを実行する
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();

        //  引数は不要
        if (args.length != 0) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        ServerSocket socket = new ServerSocket(0);
        thread.setPasv(socket);

        //  自身のIPアドレスおよびその応答表現を求める
        InetAddress localhost = InetAddress.getLocalHost();
        byte[] address  = localhost.getAddress();
        String saddress = (((int)address[0]) & 0xFF)+ "," +
                          (((int)address[1]) & 0xFF) + "," +
                          (((int)address[2]) & 0xFF) + "," +
                          (((int)address[3]) & 0xFF);

        //  自身のポート番号およびその応答表現を求める
        int port = socket.getLocalPort();
        assert(1 <= port && port <= 65535);
        String sport = (port / 256) + "," + (port % 256);

        thread.output("227 Entering Passive Mode (" + saddress + ","+ sport + ")");
    }

}
