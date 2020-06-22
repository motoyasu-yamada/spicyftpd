package spicyftpd.cmd;

import spicyftpd.FtpThread;
import spicyftpd.FtpCmdException;

/**
*   Ftp�R�}���h����������N���X
*/
public class Cmd_USER implements Cmd {

    /**
    *   �w�肵�����[�U�[���Ń��O�C������
    *
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���[�U�R�}���h�����Ɏ��s�ς�
        if (thread._user != null) {
            throw new FtpCmdException("503 Bad sequence of commands.");
        }
        //  ���Ƀ��O�I���ς�
        if (thread._logon) {
            throw new FtpCmdException("503 Bad sequence of commands.");
        }
        //  ���[�U�����w�肳��Ă��Ȃ�
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        thread._user = args[0];
        thread.output("331 Password");
    }

}
