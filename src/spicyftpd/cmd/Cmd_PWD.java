package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   現在のワーキングディレクトリを表示する 
*   構文:
*       PWD
*/
public class Cmd_PWD implements Cmd {

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

        thread.output("257 \"" + thread.currentDirectory() + "\" is current directory");
    }

}
