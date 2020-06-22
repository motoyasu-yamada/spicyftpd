package spicyftpd;

import java.io.*;
import java.net.*;
import java.util.*;
import spicyftpd.cmd.Cmd;

/**
*   ユーザからのコントロール接続を処理するスレッド
*
*/
public class FtpThread extends Thread {
    /// ユーザ
    public String      _user;
    /// 終了フラグ
    public boolean     _done;
    /// ログイン済みフラグ
    public boolean     _logon;
    /// ユーザのカレントディレクトリ
    private String      _cd;
    /// FTP出力ストリーム
    private OutputStream _out;
    /// このFTPスレッドが属するデーモン
    private FtpDaemon _daemon;

    /**
    *   コントロール接続ソケット
    */
    private Socket incoming;
    /**
    *   スレッドID
    */
    private int threadid;
    /**
    *   PASV接続の場合のサーバソケット
    *   既定値は null
    */
    private ServerSocket _pasv = null;
    /**
    *   非PASV接続の場合のデータ接続の方法
    *   クライアントのアドレス
    */
    private InetAddress  _host;
    /**
    *   非PASV接続の場合のデータ接続の方法
    *   クライアントのポート番号
    */
    private int _port;

    /**
    *   コンストラクタ
    *   @param  income  通信ソケット
    *   @param  c       通信ID
    */
    public FtpThread(FtpDaemon daemon,Socket income, int c) {
        incoming = income;
        threadid = c;
        _daemon = daemon;
    }

    /**
    *   通信処理スレッド
    *
    */
    public void run() {
        System.out.println("thread start (" + threadid + ")");

        try {
            _cd = System.getProperty("user.dir");

            BufferedReader in  = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            _out = incoming.getOutputStream();

            output("220 SpicyFTPD. \nversion 1.0\nSpicysoft Corporation.");

            _done = false;
            while(!_done) {
                try {
                    //  クライアントからのリクエスト行を取得する
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    System.out.println(">" + line);

                    String   name = getCmdName(line);
                    String[] args = getCmdArgs(line);
                    Cmd cmd = _daemon.dispatcher().cmd(name);
                    cmd.cmd(args,this);

                } catch(FtpCmdException e) {
                    e.printStackTrace();
                    output(e.getStatus());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            incoming.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("thread end (" + threadid + ")");
    }

    public void cmdSYS(String line) throws Exception {
        output("500 SYS not understood");
    }

    /**
    *   FTPコントロール接続への応答
    *   複数行の場合は自動的に改行する
    *   lineに含まれる改行は LF '\n' であること。
    *
    *   @param  line    応答行
    */
    public final void output(String line) {
        if (line == null) {
            throw new NullPointerException();
        }
        if (line.length() == 0) {
            return;
        }

        String[] lines = line.split("\\n");
        int count = lines.length;
        outputLine(lines[0]);
        for (int n = 1;n < count;n++) {
            outputLine("    " + lines[n]);
        }
    }

    private final void outputLine(String line) {
        System.out.println(line);
        try {
            _out.write((line + "\r\n").getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *   コマンド名を取得する
    *   @param      コマンド及び引数行
    *   @return     コマンド名
    *   @exception  コマンド名が指定されていない場合
    */
    private final static String getCmdName(String line) throws FtpCmdException {
        if (line.length() == 0) {
            throw new FtpCmdException("500 Syntax error, command unrecognized.");
        }
        int space  = line.indexOf(" ");
        if (space == 0) {
            throw new FtpCmdException("500 Syntax error, command unrecognized.");
        }
        if (space == -1) {
            space = line.length();
        }
        return line.substring(0,space);
    }

    /**
    *   FTPコマンドから引数を抽出する
    *   @param  コマンド及び引数行
    *   @return 引数がない場合は "" を返す。
    */
    private final static String[] getCmdArgs(String line) {
        //  行の右端の無駄な改行を削除する
        line = line.trim();
        //  コマンド名と引数を分割する
        int space  = line.indexOf(" ");
        String[] args;
        if (space == -1) {
            args = new String[0];
        } else {
            args = line.substring(space + 1).split(" ");
        }
        return args;
    }

    /**
    *   カレントディレクトリを設定する
    */
    public final void setCurrentDirectory(File directory) {
        _cd = directory.toString();
    }

    /**
    *   現在のカレントディレクトリを取得する
    *   パスのセパレータは常に '/' を使用すること。
    *   @return カレントディレクトリ
    */
    public final File currentDirectory() {
        assert(_cd != null);
        return new File(_cd);
    }

    /**
    *   ユーザルートディレクトリ以上にアクセスした
    *
    *   @param  path    絶対パス又は相対パス
    */
    public final File file(String path) {
        if (path == null) {
            throw new NullPointerException();
        }
        return new File(currentDirectory(),path);
    }

    /**
    *   ログインすることが必要なコマンドを実行する場合のチェック。
    *
    *   @throws FtpCmdException ログインしていない場合
    */
    public final void checkLogon() throws FtpCmdException {
        if (!_logon) {
            throw new FtpCmdException("503 Bad sequence of commands.");
        }
    }

    /**
    *   転送モードをASCIIモードに変更する
    */
    public final void typeAscii() {
    }

    /**
    *   転送モードをBINARYモードに変更する
    */
    public final void typeBinary() {
    }

    /**
    *   転送ソケットを生成する
    *   @return  新しく生成した転送ソケット
    */
    public Socket transferSocket() throws FtpCmdException {
        try {
            if (_pasv != null) {
                if (_pasv.isClosed()) {
                    throw new FtpCmdException("425 Can't open data connection");
                }
                if (!_pasv.isBound()) {
                    throw new FtpCmdException("425 Can't open data connection");
                }
                return _pasv.accept();
            } else {
                System.out.println(_host + ":" + _port);
                assert(_host != null);
                assert(_port != 0);
                return new Socket(_host,_port);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new FtpCmdException("425 Can't open data connection");
        }
    }

    /**
    *   データ接続の接続先のクライアントを設定する
    *   @param  address IPアドレス
    *   @param  port    ポート番号
    */
    public final void setPort(InetAddress address,int port) {
        assert(address != null);
        assert(1 <= port && port <= 65535);

        /// 既存のPASVがある場合は一旦接続を切断する
        if (_pasv != null) {
            disconnectPasv();
        }
        _host = address;
        _port = port;
    }

    /**
    *   PASV接続にモードを変更する
    */
    public final void setPasv(ServerSocket pasv) {
        assert(pasv != null);
        /// 既存のPASVがある場合は一旦接続を切断する
        if (_pasv != null) {
            disconnectPasv();
        }
        _pasv = pasv;
    }

    /**
    *   PASV接続を切断する
    */
    private final void disconnectPasv() {
        assert(_pasv != null);
        try {
            _pasv.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        _pasv = null;
    }
}
