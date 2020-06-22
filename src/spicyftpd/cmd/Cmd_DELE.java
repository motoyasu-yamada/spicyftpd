package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   指定したファイルを削除する
*   構文:
*       DELE ファイル名
*/
public class Cmd_DELE implements Cmd {

    /**
    *   コマンドを実行する
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();
        //  削除するファイル名が指定されていない
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        File file = thread.file(args[0]);
        if (file.delete()) {
            thread.output("250 delete command successful");
        } else {
            thread.output("450 Requested file action not taken");
        }
    }
}
