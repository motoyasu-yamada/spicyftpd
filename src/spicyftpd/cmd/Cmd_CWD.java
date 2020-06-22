package spicyftpd.cmd;

import java.io.File;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   �w�肵���f�B���N�g�����J�����g�f�B���N�g���Ƃ���B
*   ��΃p�X�����΃p�X���g�p�ł��� 
*   �\��:
*       CWD <�f�B���N�g����>
*/
public class Cmd_CWD implements Cmd {

    /**
    *   �R�}���h�����s����
    *
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���O�I�����Ă��Ȃ��̂ɂ��̃R�}���h�͎��s�ł��Ȃ�
        thread.checkLogon();
        //  �ړ�����f�B���N�g�����w�肳��Ă��Ȃ�
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
