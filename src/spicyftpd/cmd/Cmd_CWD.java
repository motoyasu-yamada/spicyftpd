package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   指定したディレクトリをカレントディレクトリとする。
*   絶対パスか相対パスが使用できる 
*   構文:
*       CWD <ディレクトリ名>
*/
public class Cmd_CWD implements Cmd {

    /**
    *   コマンドを実行する
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();
        //  移動するディレクトリが指定されていない
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        File directory = thread.file(args[0]);
        if (directory.exists() && directory.isDirectory()) {
        thread.setCurrentDirectory(directory);
        thread.output("250 CWD command succesful");
        } else {
            thread.output("450 Requested file action not taken");
        }
    }

}
