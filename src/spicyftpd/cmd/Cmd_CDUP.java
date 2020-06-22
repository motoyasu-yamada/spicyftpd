package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   ���O�A�E�g����
*   �\��:
*       QUIT
*/
public class Cmd_CDUP implements Cmd {

    /**
    *   �R�}���h�����s����
    *
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���O�I�����Ă��Ȃ��̂ɂ��̃R�}���h�͎��s�ł��Ȃ�
        thread.checkLogon();
        //  �����͕s�v
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
