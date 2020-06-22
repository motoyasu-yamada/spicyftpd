package spicyftpd.cmd;

import spicyftpd.FtpThread;
import spicyftpd.FtpCmdException;

/**
*   ログアウトする
*   構文:
*       QUIT
*/
public class Cmd_QUIT implements Cmd {

    /**
    *   コマンドを実行する
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();

        thread.output("GOOD BYE");
        thread._done = true;
    }

}
