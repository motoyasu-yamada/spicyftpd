package spicyftpd.cmd;

import java.io.File;
import java.net.InetAddress;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   PORT    データ・ポートを指定する
*   クライアントがデータ接続を listen するポートを識別するには、
*   次の形式の PORT を使用します。 
*   構文:   PORT ip1,ip2,ip3,ip4,port1,port2
*   引数:
*       ipn:
*           システムの IP アドレスを表します。0 ～ 255 の 10 進数値を表すストリングです。 
*       pn:
*           TCP ポート番号を表します。0 ～ 255 の 10 進数値を表すストリングです。 
*           p1 と p2 の値を TCP ポート番号に変換するには、次の式を使用します。 
*           port = ( p1 * 256 ) + p2
*       たとえば、次の PORT サブコマンドの場合、 
*       PORT 9,180,128,180,4,8
*       ポート番号は 1032、IP アドレスは 9.180.128.180 です。 
*       注: TCP/IP RFC 1122 に指定されているように、接続のクローズ後
*       約 2 分間経過しなければ、サーバーは同じクライアント IP アドレスと
*       ポート番号に接続できません。クライアント IP アドレスが同じであって
*       も異なるポート番号に接続する場合は、この制限はありません。  
*/
public class Cmd_PORT implements Cmd {

    /**
    *   コマンドを実行する
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();

        //  IPアドレス及びポート番号が指定されていない
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        //  カンマ区切りで渡された、
        //  IPアドレスとして4つ、ポート番号として2つの
        //  整数値を取得する。
        args = args[0].split(",");
        if (args.length != 6) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments.");
        }

        //  指定されているIP及びポート番号の整数値が、
        //  0以上255以下であることを検査
        int[] args2 = new int[6];
        for (int n = 0;n < 6;n++) {
            int a = Integer.parseInt(args[n]);
            if (a < 0 || 255 < a) {
                throw new FtpCmdException("501 Syntax error in parameters or arguments.");
            }
            args2[n] = a;
        }

        //  引数からIPアドレスを求める
        byte[] ip = new byte[] {
            (byte)args2[0],(byte)args2[1],
            (byte)args2[2],(byte)args2[3]
        };
        InetAddress address = InetAddress.getByAddress(ip);
        //  引数からポート番号を求める
        int port    = args2[4] * 256 + args2[5];

        thread.setPort(address,port);

        thread.output("200 PORT command successful");
    }

}
