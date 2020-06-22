package spicyftpd;

import java.net.ServerSocket;
import java.net.Socket;
import spicyftpd.cmd.CmdDispatcher;

/**
*   FTPデーモン
*/
public class FtpDaemon {

    /// 待機するポート番号
    private int _port = 21;
    /// このデーモンで使うFTPコマンドディスパッチャー
    private CmdDispatcher _disp = new CmdDispatcher();

    /**
    *   コンストラクタ
    */
    public FtpDaemon() {
    }

    /**
    *   このデーモンで使うFTPコマンドディスパッチャーを取得する
    *   @return コマンドディスパッチャー
    */
    protected final CmdDispatcher dispatcher() {
        return _disp;
    }

    /**
    *   デーモン
    */
    public void daemon() {
        try {
            start();
            run();
            term();
        } catch(Throwable t) {
            error(t);
        }
        System.exit(0);
    }

    /**
    *   デーモン開始処理
    *
    */
    private final void start() throws Exception {
        System.out.println("spicyftpd started.");
    }

    /**
    *   デーモン処理
    */
    private final void run() throws Exception {
        int threadno = 1;
        ServerSocket socket = new ServerSocket(_port);
        for(;;) {
            Socket incoming = socket.accept();
            new FtpThread(this,incoming,threadno++).start();
        }
    }

    /**
    *   デーモン終了処理
    */
    private final void term() throws Exception {
        System.out.println("spicyftpd terminated.");
    }

    /**
    *   デーモン異常処理
    */
    private final void error(Throwable t) {
        t.printStackTrace();
    }
}
