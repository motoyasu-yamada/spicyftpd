package spicyftpd.cmd;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   指定したファイル名でサーバへ送信するデータでファイルを作成する。
*   同一名のファイルがすでにある場合には、上書きする
*   構文:
*       STOR ファイル名
*/
public class Cmd_STOR implements Cmd {

    /**
    *   コマンドの実行
    *
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();

        //  ファイル名が指定されていない
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        thread.output("150 Binary data connection");
        File file = thread.file(args[0]);
        RandomAccessFile output = new RandomAccessFile(file,"rw");
        Socket socket = thread.transferSocket();
        InputStream input = socket.getInputStream();
        byte buffer[] = new byte[1024];
        int amount;
        while((amount = input.read(buffer)) != -1) {
            output.write(buffer,0,amount);
        }
        input. close();
        output.close();
        socket.close();
        thread.output("226 transfer complete");
    }

}
