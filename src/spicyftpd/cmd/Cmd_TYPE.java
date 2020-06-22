package spicyftpd.cmd;

import spicyftpd.FtpThread;
import spicyftpd.FtpCmdException;

/**
*   �]���f�[�^�̌`�����w�肷��B
*   �\��:
*       TYPE �`���I�v�V���� 
*   �`���I�v�V����:
*       A�FASCII
*       I�FImage(�o�C�i��)
*   ���T�|�[�g�`���I�v�V����:
*       E�FEBCDIC�Ȃ� 
*/
public class Cmd_TYPE implements Cmd {

    /**
    *   �R�}���h�����s����
    *
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���O�I�����Ă��Ȃ��̂ɂ��̃R�}���h�͎��s�ł��Ȃ�
        thread.checkLogon();
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }
        String opt = args[0];
        if (opt.equals("A")) {
            thread.typeAscii();

        } else if (opt.equals("I")) {
            thread.typeBinary();

        } else if (opt.equals("E")) {
            throw new FtpCmdException("504 Command not implemented for that parameter");

        } else {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }
        thread.output("200 type set");
    }

}
