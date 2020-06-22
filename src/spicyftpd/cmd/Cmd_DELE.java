package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   �w�肵���t�@�C�����폜����
*   �\��:
*       DELE �t�@�C����
*/
public class Cmd_DELE implements Cmd {

    /**
    *   �R�}���h�����s����
    *
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���O�I�����Ă��Ȃ��̂ɂ��̃R�}���h�͎��s�ł��Ȃ�
        thread.checkLogon();
        //  �폜����t�@�C�������w�肳��Ă��Ȃ�
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
