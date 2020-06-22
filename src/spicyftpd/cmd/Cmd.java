package spicyftpd.cmd;

import spicyftpd.FtpThread;
import spicyftpd.FtpCmdException;

/**
*   Ftpコマンドを実装するクラス
*/
public interface Cmd {

    /**
    *   コマンドの実行
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception;

}
