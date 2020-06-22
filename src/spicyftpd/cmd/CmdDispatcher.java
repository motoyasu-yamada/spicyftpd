package spicyftpd.cmd;

import java.util.Hashtable;
import spicyftpd.FtpCmdException;

/**
*   Ftpコマンドに該当するコマンド処理クラスの管理
*   使い方:
*       CmdDispatcher disp = new CmdDispatcher();
*       disp.cmd("LIST");
*       cmd.cmd(args,thread);
*
*   次のバージョンでの予定:
*       CmdDispatcher disp = new CmdDispatcher("/etc/conf");
*       Cmd cmd = disp.cmd("LIST");
*       cmd.cmd(args,thread);
*/
public class CmdDispatcher {
    /// コマンド名とメソッドの対応マップ
    private Hashtable _cmds = new Hashtable();

    /**
    *   コンストラクタ
    *   全てのFTPコマンド処理メソッドを登録する
    */
    public CmdDispatcher() {
        try {
            registByClass("LIST",Cmd_LIST.class);
            registByClass("USER",Cmd_USER.class);
            registByClass("PASS",Cmd_PASS.class);
            registByClass("RETR",Cmd_RETR.class);
            registByClass("STOR",Cmd_STOR.class);
            registByClass("QUIT",Cmd_QUIT.class);
            registByClass("TYPE",Cmd_TYPE.class);
            registByClass("DELE",Cmd_DELE.class);
            registByClass("CDUP",Cmd_CDUP.class);
            registByClass("CWD", Cmd_CWD.class);
            registByClass("PWD", Cmd_PWD.class);
            registByClass("PORT",Cmd_PORT.class);
            registByClass("PASV",Cmd_PASV.class);

            //  registByClass("XPWD",Cmd_PWD.class);
            // registByClassName("SYS", "SYSCmd");
            //registByClassName("NLST","NLSTCmd");
        } catch(Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    /**
    *   コマンド名に該当するコマンド処理クラスを取得する
    *   @
    *   @throws FtpCmdException コマンドが見つからない場合
    */
    public final Cmd cmd(String cmdName) throws FtpCmdException {
        assert(_cmds != null);
        if (cmdName == null) {
            throw new NullPointerException();
        }
        if (!_cmds.containsKey(cmdName)) {
            throw new FtpCmdException("502 Command not implemented.");
        }
        Cmd cmd = (Cmd)_cmds.get(cmdName);
        assert(cmd != null);
        return cmd;
    }

    /**
    *   FTPコマンド処理メソッドを登録する
    *   @param  cmdName     コマンド名
    *   @param  className   クラス名
    */
    private final void registByClassName(String cmdName,String className)
        throws  ClassNotFoundException,
                InstantiationException,
                IllegalAccessException {
        assert(_cmds != null);
        assert(className != null);
        assert(cmdName != null);
        assert(!_cmds.containsKey(cmdName));

        registByClass(cmdName,Class.forName(className));
    }

    /**
    *   FTPコマンド処理メソッドを登録する
    *   @param  cmdName
    *   @param  cmdClass
    *   @see    _cmds
    */
    private final void registByClass(String cmdName,Class cmdClass)
        throws  InstantiationException,
                IllegalAccessException{
        assert(_cmds != null);
        assert(cmdClass != null);
        assert(cmdName != null);
        assert(!_cmds.containsKey(cmdName));

        _cmds.put(cmdName,cmdClass.newInstance());
    }
}