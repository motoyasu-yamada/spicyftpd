package spicyftpd.cmd;

import spicyftpd.FtpThread;
import spicyftpd.FtpCmdException;

/**
*   Ftp�R�}���h����������N���X
*/
public interface Cmd {

    /**
    *   �R�}���h�̎��s
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception;

}
