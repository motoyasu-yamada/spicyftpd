package spicyftpd;

import java.net.ServerSocket;
import java.net.Socket;
import spicyftpd.cmd.CmdDispatcher;

/**
*   FTP�f�[����
*/
public class FtpDaemon {

    /// �ҋ@����|�[�g�ԍ�
    private int _port = 21;
    /// ���̃f�[�����Ŏg��FTP�R�}���h�f�B�X�p�b�`���[
    private CmdDispatcher _disp = new CmdDispatcher();

    /**
    *   �R���X�g���N�^
    */
    public FtpDaemon() {
    }

    /**
    *   ���̃f�[�����Ŏg��FTP�R�}���h�f�B�X�p�b�`���[���擾����
    *   @return �R�}���h�f�B�X�p�b�`���[
    */
    protected final CmdDispatcher dispatcher() {
        return _disp;
    }

    /**
    *   �f�[����
    */
    public void daemon() {
        try {
            start();
            run();
            term();
        } catch(Throwable t) {
            error(t);
        }
        System.exit(0);
    }

    /**
    *   �f�[�����J�n����
    *
    */
    private final void start() throws Exception {
        System.out.println("spicyftpd started.");
    }

    /**
    *   �f�[��������
    */
    private final void run() throws Exception {
        int threadno = 1;
        ServerSocket socket = new ServerSocket(_port);
        for(;;) {
            Socket incoming = socket.accept();
            new FtpThread(this,incoming,threadno++).start();
        }
    }

    /**
    *   �f�[�����I������
    */
    private final void term() throws Exception {
        System.out.println("spicyftpd terminated.");
    }

    /**
    *   �f�[�����ُ폈��
    */
    private final void error(Throwable t) {
        t.printStackTrace();
    }
}
