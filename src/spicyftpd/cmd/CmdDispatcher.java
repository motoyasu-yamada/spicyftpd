package spicyftpd.cmd;

import java.util.Hashtable;
import spicyftpd.FtpCmdException;

/**
*   Ftp�R�}���h�ɊY������R�}���h�����N���X�̊Ǘ�
*   �g����:
*       CmdDispatcher disp = new CmdDispatcher();
*       disp.cmd("LIST");
*       cmd.cmd(args,thread);
*
*   ���̃o�[�W�����ł̗\��:
*       CmdDispatcher disp = new CmdDispatcher("/etc/conf");
*       Cmd cmd = disp.cmd("LIST");
*       cmd.cmd(args,thread);
*/
public class CmdDispatcher {
    /// �R�}���h���ƃ��\�b�h�̑Ή��}�b�v
    private Hashtable _cmds = new Hashtable();

    /**
    *   �R���X�g���N�^
    *   �S�Ă�FTP�R�}���h�������\�b�h��o�^����
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
    *   �R�}���h���ɊY������R�}���h�����N���X���擾����
    *   @
    *   @throws FtpCmdException �R�}���h��������Ȃ��ꍇ
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
    *   FTP�R�}���h�������\�b�h��o�^����
    *   @param  cmdName     �R�}���h��
    *   @param  className   �N���X��
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
    *   FTP�R�}���h�������\�b�h��o�^����
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