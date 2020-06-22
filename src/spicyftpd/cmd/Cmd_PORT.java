package spicyftpd.cmd;

import java.io.File;
import java.net.InetAddress;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   PORT    �f�[�^�E�|�[�g���w�肷��
*   �N���C�A���g���f�[�^�ڑ��� listen ����|�[�g�����ʂ���ɂ́A
*   ���̌`���� PORT ���g�p���܂��B 
*   �\��:   PORT ip1,ip2,ip3,ip4,port1,port2
*   ����:
*       ipn:
*           �V�X�e���� IP �A�h���X��\���܂��B0 �` 255 �� 10 �i���l��\���X�g�����O�ł��B 
*       pn:
*           TCP �|�[�g�ԍ���\���܂��B0 �` 255 �� 10 �i���l��\���X�g�����O�ł��B 
*           p1 �� p2 �̒l�� TCP �|�[�g�ԍ��ɕϊ�����ɂ́A���̎����g�p���܂��B 
*           port = ( p1 * 256 ) + p2
*       ���Ƃ��΁A���� PORT �T�u�R�}���h�̏ꍇ�A 
*       PORT 9,180,128,180,4,8
*       �|�[�g�ԍ��� 1032�AIP �A�h���X�� 9.180.128.180 �ł��B 
*       ��: TCP/IP RFC 1122 �Ɏw�肳��Ă���悤�ɁA�ڑ��̃N���[�Y��
*       �� 2 ���Ԍo�߂��Ȃ���΁A�T�[�o�[�͓����N���C�A���g IP �A�h���X��
*       �|�[�g�ԍ��ɐڑ��ł��܂���B�N���C�A���g IP �A�h���X�������ł�����
*       ���قȂ�|�[�g�ԍ��ɐڑ�����ꍇ�́A���̐����͂���܂���B  
*/
public class Cmd_PORT implements Cmd {

    /**
    *   �R�}���h�����s����
    *
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���O�I�����Ă��Ȃ��̂ɂ��̃R�}���h�͎��s�ł��Ȃ�
        thread.checkLogon();

        //  IP�A�h���X�y�у|�[�g�ԍ����w�肳��Ă��Ȃ�
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        //  �J���}��؂�œn���ꂽ�A
        //  IP�A�h���X�Ƃ���4�A�|�[�g�ԍ��Ƃ���2��
        //  �����l���擾����B
        args = args[0].split(",");
        if (args.length != 6) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments.");
        }

        //  �w�肳��Ă���IP�y�у|�[�g�ԍ��̐����l���A
        //  0�ȏ�255�ȉ��ł��邱�Ƃ�����
        int[] args2 = new int[6];
        for (int n = 0;n < 6;n++) {
            int a = Integer.parseInt(args[n]);
            if (a < 0 || 255 < a) {
                throw new FtpCmdException("501 Syntax error in parameters or arguments.");
            }
            args2[n] = a;
        }

        //  ��������IP�A�h���X�����߂�
        byte[] ip = new byte[] {
            (byte)args2[0],(byte)args2[1],
            (byte)args2[2],(byte)args2[3]
        };
        InetAddress address = InetAddress.getByAddress(ip);
        //  ��������|�[�g�ԍ������߂�
        int port    = args2[4] * 256 + args2[5];

        thread.setPort(address,port);

        thread.output("200 PORT command successful");
    }

}
