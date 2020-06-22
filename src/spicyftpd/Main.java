package spicyftpd;

/**
*   FTPデーモン起動クラス
*
*   起動方法:
*       java spicyftpd.Main
*/
public class Main {

    /**
    *   エントリーポイント
    *   @param  args    コマンドラインからの引数
    */
    public static void main(String[] args) {
        FtpDaemon daemon = new FtpDaemon();
        daemon.daemon();
    }
}
