package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   ログアウトする
*   構文:
*       QUIT
*/
public class Cmd_CDUP implements Cmd {

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

        File directory = thread.currentDirectory();
        directory = directory.getParentFile();
        if (directory != null) {
            thread.setCurrentDirectory(directory);
            thread.output("250 CWD command succesful");
        } else {
            thread.output("450 Requested file action not taken");
        }
    }

}
