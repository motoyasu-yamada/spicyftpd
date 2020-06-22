package spicyftpd;

import java.io.*;
import java.net.*;
import java.util.*;
import spicyftpd.cmd.Cmd;

/**
*   ���[�U����̃R���g���[���ڑ�����������X���b�h
*
*/
public class FtpThread extends Thread {
    /// ���[�U
    public String      _user;
    /// �I���t���O
    public boolean     _done;
    /// ���O�C���ς݃t���O
    public boolean     _logon;
    /// ���[�U�̃J�����g�f�B���N�g��
    private String      _cd;
    /// FTP�o�̓X�g���[��
    private OutputStream _out;
    /// ����FTP�X���b�h��������f�[����
    private FtpDaemon _daemon;

    /**
    *   �R���g���[���ڑ��\�P�b�g
    */
    private Socket incoming;
    /**
    *   �X���b�hID
    */
    private int threadid;
    /**
    *   PASV�ڑ��̏ꍇ�̃T�[�o�\�P�b�g
    *   ����l�� null
    */
    private ServerSocket _pasv = null;
    /**
    *   ��PASV�ڑ��̏ꍇ�̃f�[�^�ڑ��̕��@
    *   �N���C�A���g�̃A�h���X
    */
    private InetAddress  _host;
    /**
    *   ��PASV�ڑ��̏ꍇ�̃f�[�^�ڑ��̕��@
    *   �N���C�A���g�̃|�[�g�ԍ�
    */
    private int _port;

    /**
    *   �R���X�g���N�^
    *   @param  income  �ʐM�\�P�b�g
    *   @param  c       �ʐMID
    */
    public FtpThread(FtpDaemon daemon,Socket income, int c) {
        incoming = income;
        threadid = c;
        _daemon = daemon;
    }

    /**
    *   �ʐM�����X���b�h
    *
    */
    public void run() {
        System.out.println("thread start (" + threadid + ")");

        try {
            _cd = System.getProperty("user.dir");

            BufferedReader in  = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            _out = incoming.getOutputStream();

            output("220 SpicyFTPD. \nversion 1.0\nSpicysoft Corporation.");

            _done = false;
            while(!_done) {
                try {
                    //  �N���C�A���g����̃��N�G�X�g�s���擾����
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    System.out.println(">" + line);

                    String   name = getCmdName(line);
                    String[] args = getCmdArgs(line);
                    Cmd cmd = _daemon.dispatcher().cmd(name);
                    cmd.cmd(args,this);

                } catch(FtpCmdException e) {
                    e.printStackTrace();
                    output(e.getStatus());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            incoming.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("thread end (" + threadid + ")");
    }

    public void cmdSYS(String line) throws Exception {
        output("500 SYS not understood");
    }

    /**
    *   FTP�R���g���[���ڑ��ւ̉���
    *   �����s�̏ꍇ�͎����I�ɉ��s����
    *   line�Ɋ܂܂����s�� LF '\n' �ł��邱�ƁB
    *
    *   @param  line    �����s
    */
    public final void output(String line) {
        if (line == null) {
            throw new NullPointerException();
        }
        if (line.length() == 0) {
            return;
        }

        String[] lines = line.split("\\n");
        int count = lines.length;
        outputLine(lines[0]);
        for (int n = 1;n < count;n++) {
            outputLine("    " + lines[n]);
        }
    }

    private final void outputLine(String line) {
        System.out.println(line);
        try {
            _out.write((line + "\r\n").getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *   �R�}���h�����擾����
    *   @param      �R�}���h�y�ш����s
    *   @return     �R�}���h��
    *   @exception  �R�}���h�����w�肳��Ă��Ȃ��ꍇ
    */
    private final static String getCmdName(String line) throws FtpCmdException {
        if (line.length() == 0) {
            throw new FtpCmdException("500 Syntax error, command unrecognized.");
        }
        int space  = line.indexOf(" ");
        if (space == 0) {
            throw new FtpCmdException("500 Syntax error, command unrecognized.");
        }
        if (space == -1) {
            space = line.length();
        }
        return line.substring(0,space);
    }

    /**
    *   FTP�R�}���h��������𒊏o����
    *   @param  �R�}���h�y�ш����s
    *   @return �������Ȃ��ꍇ�� "" ��Ԃ��B
    */
    private final static String[] getCmdArgs(String line) {
        //  �s�̉E�[�̖��ʂȉ��s���폜����
        line = line.trim();
        //  �R�}���h���ƈ����𕪊�����
        int space  = line.indexOf(" ");
        String[] args;
        if (space == -1) {
            args = new String[0];
        } else {
            args = line.substring(space + 1).split(" ");
        }
        return args;
    }

    /**
    *   �J�����g�f�B���N�g����ݒ肷��
    */
    public final void setCurrentDirectory(File directory) {
        _cd = directory.toString();
    }

    /**
    *   ���݂̃J�����g�f�B���N�g�����擾����
    *   �p�X�̃Z�p���[�^�͏�� '/' ���g�p���邱�ƁB
    *   @return �J�����g�f�B���N�g��
    */
    public final File currentDirectory() {
        assert(_cd != null);
        return new File(_cd);
    }

    /**
    *   ���[�U���[�g�f�B���N�g���ȏ�ɃA�N�Z�X����
    *
    *   @param  path    ��΃p�X���͑��΃p�X
    */
    public final File file(String path) {
        if (path == null) {
            throw new NullPointerException();
        }
        return new File(currentDirectory(),path);
    }

    /**
    *   ���O�C�����邱�Ƃ��K�v�ȃR�}���h�����s����ꍇ�̃`�F�b�N�B
    *
    *   @throws FtpCmdException ���O�C�����Ă��Ȃ��ꍇ
    */
    public final void checkLogon() throws FtpCmdException {
        if (!_logon) {
            throw new FtpCmdException("503 Bad sequence of commands.");
        }
    }

    /**
    *   �]�����[�h��ASCII���[�h�ɕύX����
    */
    public final void typeAscii() {
    }

    /**
    *   �]�����[�h��BINARY���[�h�ɕύX����
    */
    public final void typeBinary() {
    }

    /**
    *   �]���\�P�b�g�𐶐�����
    *   @return  �V�������������]���\�P�b�g
    */
    public Socket transferSocket() throws FtpCmdException {
        try {
            if (_pasv != null) {
                if (_pasv.isClosed()) {
                    throw new FtpCmdException("425 Can't open data connection");
                }
                if (!_pasv.isBound()) {
                    throw new FtpCmdException("425 Can't open data connection");
                }
                return _pasv.accept();
            } else {
                System.out.println(_host + ":" + _port);
                assert(_host != null);
                assert(_port != 0);
                return new Socket(_host,_port);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new FtpCmdException("425 Can't open data connection");
        }
    }

    /**
    *   �f�[�^�ڑ��̐ڑ���̃N���C�A���g��ݒ肷��
    *   @param  address IP�A�h���X
    *   @param  port    �|�[�g�ԍ�
    */
    public final void setPort(InetAddress address,int port) {
        assert(address != null);
        assert(1 <= port && port <= 65535);

        /// ������PASV������ꍇ�͈�U�ڑ���ؒf����
        if (_pasv != null) {
            disconnectPasv();
        }
        _host = address;
        _port = port;
    }

    /**
    *   PASV�ڑ��Ƀ��[�h��ύX����
    */
    public final void setPasv(ServerSocket pasv) {
        assert(pasv != null);
        /// ������PASV������ꍇ�͈�U�ڑ���ؒf����
        if (_pasv != null) {
            disconnectPasv();
        }
        _pasv = pasv;
    }

    /**
    *   PASV�ڑ���ؒf����
    */
    private final void disconnectPasv() {
        assert(_pasv != null);
        try {
            _pasv.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        _pasv = null;
    }
}
