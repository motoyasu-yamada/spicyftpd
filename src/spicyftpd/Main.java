package spicyftpd;

/**
*   FTP�f�[�����N���N���X
*
*   �N�����@:
*       java spicyftpd.Main
*/
public class Main {

    /**
    *   �G���g���[�|�C���g
    *   @param  args    �R�}���h���C������̈���
    */
    public static void main(String[] args) {
        FtpDaemon daemon = new FtpDaemon();
        daemon.daemon();
    }
}
