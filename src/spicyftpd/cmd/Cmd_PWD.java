package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   ���݂̃��[�L���O�f�B���N�g����\������ 
*   �\��:
*       PWD
*/
public class Cmd_PWD implements Cmd {

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

        thread.output("257 \"" + thread.currentDirectory() + "\" is current directory");
    }

}
