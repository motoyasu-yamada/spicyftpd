package spicyftpd;

/*
*
*   FTP�X���b�h
*   ���X�|���X�R�[�h ���b�Z�[�W�� �Ӗ� 

public class FtpResponse {

    /// REST�R�}���h�̂��߂̃}�[�J�[�ԓ��ł��� 
    public final static int STATUS_RESTART_MARKER_REPLY_        = 110;
    public final static String STATUSS = "Restart marker reply";
    /// �T�[�r�X�͒�~���Ă��邪�Annn����ɏ����ł��� 
    public final static int STATUS_SERVICE_READY_IN_MINUTES     = 120;
    public final static String STATUSS = ""Service ready in nnn minutes"";
    /// �f�[�^�R�l�N�V�����͂��łɊm������Ă���B���̃R�l�N�V�����œ]�����J�n���� 
    public final static int STATUS_DATA_CONNECTION_ALREADY_OPEN = 125;
    public final static String STATUSS = ""Data connection already open; transfer starting"";
    /// �t�@�C���X�e�[�^�X�͐���ł���B�f�[�^�R�l�N�V�������m������ 
    public final static int STATUS_FILE_STATUS_OKAY             = 150;
    public final static String STATUSS = "File status okay; about to open data connection";
    /// �R�}���h�͐���Ɏ󂯓����ꂽ 
    public final static int STATUS_COMMAND_OKAY = 200;
    public final static String STATUSS = "Command okay";
    /// �R�}���h�͎�������Ă��Ȃ��BSITE�R�}���h��OS�R�}���h���K�؂łȂ��ꍇ�Ȃ� 
    public final static int STATUS_COMMAND_NOT_IMPLEMENTED = 202;
    public final static String STATUSS = "Command not implemented, superfluous at this site";
    /// STAT�R�}���h�ɑ΂��郌�X�|���X 
    public final static int STATUS_STAT_SYSTEM_STATUS = 211;
    public final static String STATUSS = "System status, or system help reply";
    /// STAT�R�}���h�ɂ��f�B���N�g���������� 
    public final static int STATUS_STAT_DIRECTORY = 212;
    public final static String STATUSS = "Directory status";
    /// STAT�R�}���h�ɂ��t�@�C���������� 
    public final static int STATUS_STAT_FILE     = 213;
    public final static String STATUSS_FILE_STATUS = "File status";
    /// HELP�R�}���h�ɑ΂��郌�X�|���X 
    public final static int STATUS_HELP_MESSAGE = 214;
    public final static String STATUSS = "Help message";
    /// SYST�R�}���h�ɑ΂��郌�X�|���X 
    public final static int STATUS_NAME_SYSTEM_TYPE = 215;
    public final static String STATUSS = "NAME system type";
    /// �V�K���[�U�[�����ɏ������������B���O�C�����ɕ\�������ꍇ��z�肵�Ă��� 
    public final static int STATUS_SERVICE_READY = 220;
    public final static String STATUSS = "Service ready for new user";
    /// �R���g���[���R�l�N�V������ؒf����BQUIT�R�}���h���̃��X�|���X 
    public final static int STATUS_SERVICE_CLOSING_CONTROL_CONNECTION = 221;
    public final static String STATUSS = "Service closing control connection";
    /// �f�[�^�R�l�N�V�������m�������B�f�[�^�̓]���͍s���Ă��Ȃ� 
    public final static int STATUS_DATA_CONNECTION_OPEN = 225;
    public final static String STATUSS = "Data connection open; no transfer in progress";
    /// �v�����ꂽ���N�G�X�g�͐��������B�f�[�^�R�l�N�V�������N���[�Y���� 
    public final static int STATUSI = 226;
    public final static String STATUSS = "Closing data connection";
    /// PASV�R�}���h�ւ̃��X�|���X�Bh1�`h4��IP�A�h���X�Ap1�`p2�̓|�[�g�ԍ������� 
    public final static int STATUSI = 227;
    public final static String STATUSS = "Entering Passive Mode (h1,h2,h3,h4,p1,p2)";
    /// ���[�U�[���O�C���̐��� 
    public final static int STATUSI = 230;
    public final static String STATUSS = "User logged in, proceed";
    /// �v�����ꂽ�R�}���h�ɂ�鑀��͐���I������ 
    public final static int STATUSI = 250;
    public final static String STATUSS = "Requested file action okay, completed";
    /// �t�@�C����f�B���N�g�����쐬�����Ƃ����̂�RFC�ł̈Ӗ������AMKD�R�}���h�̌��ʈȊO�ɂ��A���ۂɂ�PWD�R�}���h�̌��ʂɂ��p������ 
    public final static int STATUSI = 257;
    public final static String STATUSS = ""PATHNAME" created";
    /// �p�X���[�h�̓��͂����߂� 
    public final static int STATUSI = 331;
    public final static String STATUSS = "User name okay, need password";
    /// ACCT�R�}���h�ŉۋ������w�肷��K�v������ 
    public final static int STATUSI = 332;
    public final static String STATUSS = "Need account for login";
    *   350 Requested file action pending further information ���̉��炩�̏������߂Ă��� 
    /// �T�[�r�X��񋟂ł��Ȃ��B�R���g���[���R�l�N�V�������I������B�T�[�o�̃V���b�g�_�E�����Ȃ� 
    public final static int STATUSI = 421;
    public final static String STATUSS = "Service not available, closing control connection";
    /// �f�[�^�R�l�N�V�������I�[�v���ł��Ȃ� 
    public final static int STATUSI = 425;
    public final static String STATUSS = "Can't open data connection";
    /// ���炩�̌����ɂ��A�R�l�N�V�������N���[�Y���A�f�[�^�]�������~���� 
    public final static int STATUS_CONNECTION_CLOSED_TRANSFER_ABORTED = 426;
    public final static String STATUSS = "Connection closed; transfer aborted";
    /// �v�����ꂽ���N�G�X�g�̓A�N�Z�X������t�@�C���V�X�e���̗��R�Ŏ��s�ł��Ȃ� 
    public final static int STATUSI = 450;
    public final static String STATUSS = "Requested file action not taken";
    /// ���[�J���G���[�̂��ߏ����𒆎~���� 
    public final static int STATUSI = 451;
    public final static String STATUSS = "Requested action aborted. Local error in processing";
    /// �f�B�X�N�e�ʂ̖��Ŏ��s�ł��Ȃ� 
    public final static int STATUSI = 452;
    public final static String STATUSS = "Requested action not taken";
    /// �R�}���h�̕��@�G���[ 
    public final static int STATUSI = 500;
    public final static String STATUSS = "Syntax error, command unrecognized";
    /// ������p�����[�^�̕��@�G���[ 
    public final static int STATUSI = 501;
    public final static String STATUSS = "Syntax error in parameters or arguments";
    /// �R�}���h�͖������ł��� 
    public final static int STATUSI = 502;
    public final static String STATUSS = "Command not implemented";
    /// �R�}���h��p���鏇�Ԃ��Ԉ���Ă��� 
    public final static int STATUS_BAD_SEQUENCE_OF_COMMANDS = 503;
    public final static String STATUSS = "Bad sequence of commands";
    /// ������p�����[�^�������� 
    public final static int STATUS_PARAMETER_OF_COMMAND_NOT_IMPLEMENTED_ = 504;
    public final static String STATUSS = "Command not implemented for that parameter";
    /// ���[�U�[�̓��O�C���ł��Ȃ����� 
    public final static int STATUS_NOT_LOGGED_IN = 530;
    public final static String STATUSS = "Not logged in";
    /// �t�@�C�����M�ɂ́AACCT�R�}���h�ŉۋ������m�F���Ȃ��Ă͂Ȃ�Ȃ� 
    public final static int STATUSI = 532;
    public final static String STATUSS = "Need account for storing files";
    /// �v�����ꂽ���N�G�X�g�̓A�N�Z�X������t�@�C���V�X�e���̗��R�Ŏ��s�ł��Ȃ� 
    public final static int STATUSI = 550;
    public final static String STATUSS = "Requested action not taken";
    /// �y�[�W�\���̃^�C�v�̖��Ŏ��s�ł��Ȃ� 
    public final static int STATUS_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN = 551;
    public final static String STATUSS = "Requested action aborted. Page type unknown";
    /// �f�B�X�N�e�ʂ̖��Ŏ��s�ł��Ȃ� 
    public final static int STATUS_REQUESTED_FILE_ACTION_ABORTED = 552;
    public final static String STATUSS = "Requested file action aborted";
    /// �t�@�C�������Ԉ���Ă��邽�ߎ��s�ł��Ȃ� 
    public final static int STATUS_REQUESTED_ACTION_NOT_TAKEN    = 553;
    public final static String STATUSS = "Requested action not taken";
};
*/
