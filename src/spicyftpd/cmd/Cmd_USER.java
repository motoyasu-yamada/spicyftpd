package spicyftpd.cmd;

import spicyftpd.FtpThread;
import spicyftpd.FtpCmdException;

/**
*   Ftpコマンドを実装するクラス
*/
public class Cmd_USER implements Cmd {

    /**
    *   指定したユーザー名でログインする
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ユーザコマンドを既に実行済み
        if (thread._user != null) {
            throw new FtpCmdException("503 Bad sequence of commands.");
        }
        //  既にログオン済み
        if (thread._logon) {
            throw new FtpCmdException("503 Bad sequence of commands.");
        }
        //  ユーザ名が指定されていない
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        thread._user = args[0];
        thread.output("331 Password");
    }

}
