package spicyftpd.cmd;

import java.io.File;
import java.net.InetAddress ;
import java.net.ServerSocket;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   �p�b�V�u���[�h�ւ̈ڍs���w������B
*   �T�[�o�̓f�[�^�R�l�N�V�����Ŏg�p����IP�A�h���X�ƃ|�[�g�ԍ���\������ 
*
*   �\��:   PORT 
*   �����`��:
*       ip1,ip2,ip3,ip4,port1,port2
*/
public class Cmd_PASV implements Cmd {

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

        ServerSocket socket = new ServerSocket(0);
        thread.setPasv(socket);

        //  ���g��IP�A�h���X����т��̉����\�������߂�
        InetAddress localhost = InetAddress.getLocalHost();
        byte[] address  = localhost.getAddress();
        String saddress = (((int)address[0]) & 0xFF)+ "," +
                          (((int)address[1]) & 0xFF) + "," +
                          (((int)address[2]) & 0xFF) + "," +
                          (((int)address[3]) & 0xFF);

        //  ���g�̃|�[�g�ԍ�����т��̉����\�������߂�
        int port = socket.getLocalPort();
        assert(1 <= port && port <= 65535);
        String sport = (port / 256) + "," + (port % 256);

        thread.output("227 Entering Passive Mode (" + saddress + ","+ sport + ")");
    }

}
