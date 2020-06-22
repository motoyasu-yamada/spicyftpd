package spicyftpd.cmd;

import spicyftpd.FtpThread;
import spicyftpd.FtpCmdException;

/**
*   転送データの形式を指定する。
*   構文:
*       TYPE 形式オプション 
*   形式オプション:
*       A：ASCII
*       I：Image(バイナリ)
*   未サポート形式オプション:
*       E：EBCDICなど 
*/
public class Cmd_TYPE implements Cmd {

    /**
    *   コマンドを実行する
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }
        String opt = args[0];
        if (opt.equals("A")) {
            thread.typeAscii();

        } else if (opt.equals("I")) {
            thread.typeBinary();

        } else if (opt.equals("E")) {
            throw new FtpCmdException("504 Command not implemented for that parameter");

        } else {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }
        thread.output("200 type set");
    }

}
