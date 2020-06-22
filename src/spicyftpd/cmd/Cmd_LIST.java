package spicyftpd.cmd;

import java.io.File;
import java.io.OutputStream;
import java.net.Socket;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;
import spicyftpd.filesystem.FileSystem;
import spicyftpd.filesystem.FileSystemFactory;

/**
*   Ftp�R�}���h����������N���X
*
*   �s�`��:
*       ���[�h �����N ���L�� �O���[�v �T�C�Y ���t���� ���O<CRLF>
*       ��  �e�t�B�[���h�͋󔒃X�y�[�X1 �ŋ�؂�
*   �t�B�[���h����:
*       ���[�h:
*           ���[�h��10��
*           1���ڂ̐���:
*               �t�@�C���`��
*               d  �f�B���N�g���[��\���܂��B  
*               b  �u���b�N����t�@�C����\���܂��B  
*               c  ��������t�@�C����\���܂��B  
*               l  �V���{���b�N�E�����N��\���܂��B-N �t���O�����͂��ꂽ���A�V���{���b�N�E�����N�̃����N��t�@�C��������܂���ł����B  
*               p  ������o�� (FIFO) ����t�@�C����\���܂��B  
*               s  ���[�J���E�\�P�b�g��\���܂��B  
*               -  ���ʂ̃t�@�C����\���܂��B  
*           2���ڂ���10���ڂ܂ł�9���̐���:
*               ���L�ҁA�O���[�v�A���̑��̃��[�U�̋����
*               ���ꂼ��3���ł���킷
*               r  read (�ǂݎ��)  
*               w  �������� (�ҏW)  
*               x  ���s (����)  
*               -  �Y�����鋖���̔۔F
*       �����N:
*           �t�@�C���ւ̃����N�̐�
*           �E�l�߂�5�����ɖ����Ȃ�����������ꍇ�̓u�����N�Ŗ��߂�
*       ���L��:
*           �t�@�C���̏��L��
*           �����͍��l�߂�10�����ɖ����Ȃ����ɂ̓u�����N�𖄂߂�
*       �O���[�v:
*           �t�@�C���̏��L�O���[�v
*           �����͍��l�߂�10�����ɖ����Ȃ����ɂ̓u�����N�𖄂߂�
*       �T�C�Y:
*           �t�@�C���̃T�C�Y
*           �E�l�߂�10�����ɖ����Ȃ�����������ꍇ�̓u�����N�𖄂߂�
*       ���t����:
*           �t�@�C���̕ύX����
*           ���l�߂�12�����ɖ����Ȃ����ɂ̓u�����N�𖄂߂�
*           �ύX���ߋ� 180 ���ȓ��ɍs��ꂽ���̂ł���ꍇ 
*               Mmm dd hh:mm
*           �ύX���ߋ� 180 ���ȍ~�ɍs��ꂽ���̂ł���ꍇ�A
*               Mmm dd yyyy
*           �e�t�B�[���h�̈Ӗ�
*               Mmm  �����̗��B  
*               dd  ���ɂ�  �E�l�߂�2�����ɖ����Ȃ��ꍇ�ɂ̓u�����N�Ŗ��߂�
*               hh  ����    00 ���� 23 �܂ł� 2 ������
*                           �E�l�߂�2�����ɖ����Ȃ��ꍇ�ɂ̓[���Ŗ��߂�
*               mm  ��      00 ���� 59 �܂ł� 2 ������
*                           �E�l�߂�2�����ɖ����Ȃ��ꍇ�ɂ̓[���Ŗ��߂�
*               yyyy  �N    4 ������
*       ���O:
*           �I�u�W�F�N�g�̉ϒ��̖��O
*           -CRLF(���A (CR) �Ɖ��s (LF) �̃y�A) ���Ō���ɕt������B
*           ���O�ɂ̓u�����N���܂߂邱�Ƃ��ł��܂��B 
*   
*   ��:
*       drwxrwxrwx   4 QSYS           0    51200 Feb  9 21:28 home
*/
public class Cmd_LIST implements Cmd {

    /**
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���O�I�����Ă��Ȃ��̂ɂ��̃R�}���h�͎��s�ł��Ȃ�
        thread.checkLogon();

        File directory;
        switch(args.length) {
        case 0:
            directory = thread.currentDirectory();
            break;
        case 1:
            directory = thread.file(args[0]);
            break;
        default:
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        FileSystem system = FileSystemFactory.getInstance();
        StringBuffer data = new StringBuffer();
        final File[] list = directory.listFiles();
        if (list != null) {
            final int count = list.length;
            for(int n = 0;n < count;n++) {
                File file = list[n];
                //  ���[�h
                data.append(getFileType(file));
                data.append(system.getOwnerPermission(file));
                data.append(system.getGroupPermission(file));
                data.append(system.getAllPermission(file)  );
                data.append(' ');
                //  �����N
                formatRight(data,system.getLink(file),5,' ');
                data.append(' ');

                //  ���L��
                formatLeft(data,system.getOwner(file),10);
                data.append(' ');

                //  �O���[�v
                formatLeft(data,system.getGroup(file),10);
                data.append(' ');

                //  �T�C�Y
                formatRight(data,(int)file.length(),10,' ');
                data.append(' ');

                //  ���t
                data.append(getLastModified(file.lastModified()));
                data.append(' ');

                //  �t�@�C����
                data.append(file.getName());
                //  ���s�R�[�h
                data.append("\r\n");
            }
        }

        thread.output("150 ASCII data"); 

        System.out.println(data.toString());
        Socket transfer = thread.transferSocket();
        OutputStream output = transfer.getOutputStream();
        output.write(data.toString().getBytes());
        output.close();
        transfer.close();

        thread.output("226 transfer complete"); 
    }

    /**
    *   �t�@�C���`�����擾����
    *   @return 
    *       d  �f�B���N�g���[
    *       -  ���ʂ̃t�@�C��
    *   ������Ԃ��Ȃ��l:
    *       b  �u���b�N����t�@�C��
    *       c  ��������t�@�C��  
    *       l   �V���{���b�N�E�����N -N �t���O�����͂��ꂽ���A
    *           �V���{���b�N�E�����N�̃����N��t�@�C�����Ȃ�
    *       p  ������o�� (FIFO) ����t�@�C��  
    *       s  ���[�J���E�\�P�b�g  
    */
    private final char getFileType(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        if(file.isDirectory()) {
            return 'd';
        }
        return '-';
    }

    /**
    *   ���t���t�H�[�}�b�g����
    *   @param  long
    */
    private static final String getLastModified(long time) {
        return "Feb  9 21:28";
    }

    /**
    *   �������'��'�ō��l�ɂ��Ē�����length�ɑ�����B
    *
    *   *   src������̒����� length ���Z���ꍇ�́A
    *       ������̍���'��'���߂č��l�ɂ���
    *       ������̒����� length �ɑ�����B
    *   *   src������̒����� length ��蒷���ꍇ�́A
    *       ������length�ȏ�̕�������폜����
    *
    *   @param  format  ���������ʂ�ǉ����镶����
    *   @param  src �����錳�̕�����
    *   @param  length  ������
    */
    private static final void formatLeft(StringBuffer format,String src,int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length (" + length + ") <= 0 ");
        }
        if (src == null) {
            throw new NullPointerException();
        }

        int pad = length - src.length();
        if (pad == 0) {
        } else if (pad < 0) {
            src.substring(0,length);
        } else {
            assert(0 < pad);
            format.append(src);
            repeat(format,pad,' ');
        }
    }

    /**
    *   ���l�𕶎���ŕ\��
    *   �������'��'�ŉE�l�ɂ��Ē�����length�ɑ�����B
    *
    *   *   ���l������킷������̒����� length ���Z���ꍇ�́A
    *       ������̍���'��'���߂ĉE�l�ɂ���
    *       ������̒����� length �ɑ�����
    *   *    ���l������킷������̒����� length ��蒷���ꍇ�́A
    *       �E����length�ȏ�̕�������폜����
    *
    *   @param  format  ���������ʂ�ǉ����镶����
    *   @param  src     �����錳�̕�����
    *   @param  length  ������
    *   @param  padchar �����邽�߂ɋl�߂邽�߂̕���
    */
    private static final void formatRight(StringBuffer format,int number,int length,char padchar) {
        if (length <= 0) {
            throw new IllegalArgumentException("length (" + length + ") <= 0 ");
        }
        String src = String.valueOf(number);
        int pad = length - src.length();
        if (pad == 0) {
        } else if (pad < 0) {
            src.substring(-pad,src.length());
        } else {
            assert(0 < pad);
            repeat(format,pad,padchar);
            format.append(src);
        }
    }

    /**
    *   �w�肵���������A�w�肵���������J��Ԃ��āA������̌��ɒǉ�����B
    *   @param  format  ���̕�����̌��ɒǉ�����
    *   @param  count   �J��Ԃ���      0���傫���ꍇ
    *                                       �w�肵���������������J��Ԃ�
    *                                   0�̏ꍇ
    *                                       �Ȃɂ����Ȃ�
    *                                   0��菬�����ꍇ
    *                                       IllegalArgumentException
    *   @param  c       �J��Ԃ�������
    */
    private static final void repeat(StringBuffer format,int count,char c) {
        if (format == null) {
            throw new NullPointerException();
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count(" + count + ") < 0");
        }
        while (0 < count--) {
            format.append(c);
        }
    }
}
