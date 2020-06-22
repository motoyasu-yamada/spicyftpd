package spicyftpd.cmd;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;

/**
*   �w�肵���t�@�C�����ŃT�[�o�֑��M����f�[�^�Ńt�@�C�����쐬����B
*   ���ꖼ�̃t�@�C�������łɂ���ꍇ�ɂ́A�㏑������
*   �\��:
*       STOR �t�@�C����
*/
public class Cmd_STOR implements Cmd {

    /**
    *   �R�}���h�̎��s
    *
    *   @param  args    �R�}���h�̈���
    *   @param  thread  �R�}���h�����s����Ftp�X���b�h
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ���O�I�����Ă��Ȃ��̂ɂ��̃R�}���h�͎��s�ł��Ȃ�
        thread.checkLogon();

        //  �t�@�C�������w�肳��Ă��Ȃ�
        if (args.length != 1) {
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        thread.output("150 Binary data connection");
        File file = thread.file(args[0]);
        RandomAccessFile output = new RandomAccessFile(file,"rw");
        Socket socket = thread.transferSocket();
        InputStream input = socket.getInputStream();
        byte buffer[] = new byte[1024];
        int amount;
        while((amount = input.read(buffer)) != -1) {
            output.write(buffer,0,amount);
        }
        input. close();
        output.close();
        socket.close();
        thread.output("226 transfer complete");
    }

}
